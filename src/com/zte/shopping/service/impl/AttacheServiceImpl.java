package com.zte.shopping.service.impl;

import com.zte.shopping.constant.DictConstant;
import com.zte.shopping.entity.Attache;
import com.zte.shopping.entity.User;
import com.zte.shopping.mapper.IAttacheMapper;
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
    private IAttacheMapper attacheDao;

    /**
     * 查询当前用户的头像
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Attache findHeadImageByUserId(HttpSession session) {
        User user = (User) session.getAttribute("user");

        return attacheDao.selectUserHeadImage(DictConstant.ATTACHE_FILE_TYPE_HEAD_IMAGE, user.getUserId());
    }

    /**
     * 查询当前用户的生活照片
     */
    @Override
    public List<Attache> findLifeImageByUserId(HttpSession session) {
        User user = (User) session.getAttribute("user");

        return attacheDao.selectUserLifeImages(DictConstant.ATTACHE_FILE_TYPE_LIFE_IMAGE, user.getUserId());
    }

    /**
     * 修改用户头像
     * 判断该用户是否拥有头像
     * 若该用户尚未上传头像,则为添加
     * 若该用户已经有头像,则为修改
     */
    public void modifyHeadImage(CommonsMultipartFile file, HttpSession session) throws FileUploadException {
        User user = (User) session.getAttribute("user");
        //todo:头像为啥不做覆盖操作？

        Attache selectAttache = attacheDao.selectUserHeadImage(DictConstant.ATTACHE_FILE_TYPE_HEAD_IMAGE, user.getUserId());
        String path = "/upload/" + new SimpleDateFormat("yyyyMMdd").format(new Date());
        String realPath = session.getServletContext().getRealPath(path);
        String originalFilename = file.getOriginalFilename();
        String prefix = originalFilename.substring(0, originalFilename.lastIndexOf("."));
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."), originalFilename.length());
        System.out.println(suffix);
        String uuid         = UUID.randomUUID().toString();
        String fileNameOnly = prefix + uuid + suffix;

        if (selectAttache == null) {
            Attache attache = new Attache();
            attache.setCreateDate(new Date());
            attache.setFileType(DictConstant.ATTACHE_FILE_TYPE_HEAD_IMAGE);
            attache.setUser(user);
            attache.setFilePath(path + "/" + fileNameOnly);

            attacheDao.insertAttache(attache);
        } else {
            selectAttache.setCreateDate(new Date());
            selectAttache.setFilePath(path + "/" + fileNameOnly);

            attacheDao.updateAttache(selectAttache);
        }

        File f = new File(realPath);
        f.mkdirs();

        try {
            file.transferTo(new File(realPath, fileNameOnly));
            session.setAttribute("headImg", path + "/" + fileNameOnly);
        } catch (IllegalStateException | IOException e) {
            throw new FileUploadException("上传头像出错");
        }
    }

    @Override
    public void addAttache(Attache attache) {
        attacheDao.insertAttache(attache);
    }

}
