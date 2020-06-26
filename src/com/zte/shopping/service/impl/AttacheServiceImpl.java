package com.zte.shopping.service.impl;

import com.zte.shopping.constant.DictConstant;
import com.zte.shopping.dao.IAttacheDao;
import com.zte.shopping.entity.Attache;
import com.zte.shopping.entity.User;
import com.zte.shopping.service.IAttacheService;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Eytins
 */

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class AttacheServiceImpl implements IAttacheService {
    @Autowired
    private IAttacheDao attacheDao;

    /**
     * 查询当前用户的头像
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Attache findHeadImageByUserId(HttpSession session) {
        User user = (User) session.getAttribute("user");

        Attache selectAttache = attacheDao.selectUserHeadImage(DictConstant.ATTACHE_FILE_TYPE_HEAD_IMAGE, user.getUserId());

        return selectAttache;
    }

    /**
     * 查询当前用户的生活照片
     */
    @Override
    public List<Attache> findLifeImageByUserId(HttpSession session) {
        User user = (User) session.getAttribute("user");

        List<Attache> selectAttache = attacheDao.selectUserLifeImages(DictConstant.ATTACHE_FILE_TYPE_LIFE_IMAGE, user.getUserId());

        return selectAttache;
    }

    /**
     * 修改用户头像
     * 判断该用户是否拥有头像
     * 若该用户尚未上传头像,则为添加
     * 若该用户已经有头像,则为修改
     */
    public void modifyHeadImage(CommonsMultipartFile file, HttpSession session) throws FileUploadException {
        User user = (User) session.getAttribute("user");

        Attache selectAttache = attacheDao.selectUserHeadImage(DictConstant.ATTACHE_FILE_TYPE_HEAD_IMAGE, user.getUserId());

        String path = "/upload/" + new SimpleDateFormat("yyyyMMdd").format(new Date());

        String realPath = session.getServletContext().getRealPath(path);

		/*File f = new File(realPath);
		f.mkdirs();*/

        String originalFilename = file.getOriginalFilename();

        // "1.jpg"  ---> 1
        // substring(int beginIndex, int endIndex)  [beginIndex,endIndex)
        String prefix = originalFilename.substring(0, originalFilename.lastIndexOf("."));

        // "1.jpg"  ---> .jpg
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."), originalFilename.length());
        System.out.println(suffix);

        // 生成UUID, 保证文件名不会重复
        String uuid         = UUID.randomUUID().toString();
        String fileNameOnly = prefix + uuid + suffix;

        // 若没有查询到对应的数据  表时该用户尚未上传头像  则做  添加会员头像  操作
        if (selectAttache == null) {
            Attache attache = new Attache();
            attache.setCreateDate(new Date());
            attache.setFileType(DictConstant.ATTACHE_FILE_TYPE_HEAD_IMAGE);
            attache.setUser(user);
            attache.setFilePath(path + "/" + fileNameOnly);

            // 添加会员头像
            attacheDao.insertAttache(attache);
        } else {
            // 若查询到对应数据了  表示该用户已经有头像了  则做  修改会员头像  操作
            selectAttache.setCreateDate(new Date());
            selectAttache.setFilePath(path + "/" + fileNameOnly);

            // 修改会员头像
            attacheDao.updateAttache(selectAttache);
        }

        File f = new File(realPath);
        f.mkdirs();

        try {
            file.transferTo(new File(realPath, fileNameOnly));
            session.setAttribute("headImg", path + "/" + fileNameOnly);
        } catch (IllegalStateException e) {
            throw new FileUploadException("上传头像出错");  // org.apache.commons.fileupload.FileUploadException
        } catch (IOException e) {
            throw new FileUploadException("上传头像出错");
        }
    }

    @Override
    public void addAttache(Attache attache) {
        // 添加附件信息
        attacheDao.insertAttache(attache);
    }

}
