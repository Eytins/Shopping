package com.zte.shopping.controller;

import com.zte.shopping.constant.DictConstant;
import com.zte.shopping.entity.Attache;
import com.zte.shopping.entity.User;
import com.zte.shopping.service.IAttacheService;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Eytins
 */


@Controller
@RequestMapping("/attache")
public class AttacheController {
    @Autowired
    private IAttacheService attacheService;

    /**
     * 上传/修改 用户头像
     * 判断改用户是否拥有头像
     * 若该用户尚未上传头像,则为添加
     * 若该用户已经上传头上,则为修改
     */
    @RequestMapping("/modifyHeadImage")
    public ModelAndView modifyHeadImage(@RequestParam("file") CommonsMultipartFile file, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();

        try {
            // 修改用户头像
            attacheService.modifyHeadImage(file, session);
        } catch (FileUploadException e)  // org.apache.commons.fileupload.FileUploadException
        {
            modelAndView.addObject("msg", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        modelAndView.setViewName("redirect:/user/showCenter");

        return modelAndView;
    }

    /**
     * 上传 生活照片(支持多张)
     */
    @RequestMapping("/uploadLifeImage")
    public ModelAndView uploadLifeImage(@RequestParam("file") CommonsMultipartFile[] files, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();

        User user = (User) session.getAttribute("user");


        try {
            for (CommonsMultipartFile file : files) {
                String path = "/upload/" + new SimpleDateFormat("yyyyMMdd").format(new Date());

                String originalFilename = file.getOriginalFilename();

                String realPath = session.getServletContext().getRealPath(path);

				/*// "1.jpg"  ---> 1
			    // substring(int beginIndex, int endIndex)  [beginIndex,endIndex)
			    String prefix = originalFilename.substring(0, originalFilename.lastIndexOf("."));

			    // "1.jpg"  ---> .jpg
			    String suffix = originalFilename.substring(originalFilename.lastIndexOf("."), originalFilename.length());
			    System.out.println(suffix);

			    // 生成UUID, 保证文件名不会重复
			    String uuid = UUID.randomUUID().toString();
			    String fileNameOnly = prefix + uuid + suffix;*/

                Attache attache = new Attache();
                attache.setCreateDate(new Date());
                attache.setFileType(DictConstant.ATTACHE_FILE_TYPE_LIFE_IMAGE);
                attache.setUser(user);
                attache.setFilePath(path + "/" + originalFilename);

                attacheService.addAttache(attache);

                File f = new File(realPath);
                f.mkdirs();


                file.transferTo(new File(realPath, originalFilename));

            }
        } catch (IllegalStateException e) {
            modelAndView.addObject("msg", e.getMessage());
        } catch (IOException e) {
            modelAndView.addObject("msg", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            modelAndView.addObject("msg", "服务器内部异常");
        }


        modelAndView.setViewName("redirect:/user/showCenter");

        return modelAndView;
    }
}
