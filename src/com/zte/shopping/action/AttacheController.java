package com.zte.shopping.action;

import com.zte.shopping.constant.DictConstant;
import com.zte.shopping.entity.Attache;
import com.zte.shopping.entity.User;
import com.zte.shopping.service.IAttacheService;
import com.zte.shopping.util.UploadFileUtil;
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

    @RequestMapping("/modifyHeadImage")
    public ModelAndView modifyHeadImage(@RequestParam("file") CommonsMultipartFile file, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();

        try {
            attacheService.modifyHeadImage(file, session);
        } catch (FileUploadException e) {
            modelAndView.addObject("msg", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        modelAndView.setViewName("redirect:/user/showCenter");

        return modelAndView;
    }

    @RequestMapping("/uploadLifeImage")
    public ModelAndView uploadLifeImage(@RequestParam("file") CommonsMultipartFile[] files, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();

        User user = (User) session.getAttribute("user");

        try {
            for (CommonsMultipartFile file : files) {
                String path = "/upload/" + new SimpleDateFormat("yyyyMMdd").format(new Date());
                String originalFilename = file.getOriginalFilename();

                //todo:这句话干啥的
                String realPath = session.getServletContext().getRealPath(path);

                Attache attache = new Attache();
                attache.setCreateDate(new Date());
                attache.setFileType(DictConstant.ATTACHE_FILE_TYPE_LIFE_IMAGE);
                attache.setUser(user);
                attache.setFilePath(path + "/" + originalFilename);

                String serverFileName = UploadFileUtil.renameUploadFileName(originalFilename);

                attacheService.addAttache(attache);

                File f = new File(realPath);
                f.mkdirs();

                //这里originalFilename要不要换成serverFileName
                file.transferTo(new File(realPath, originalFilename));

            }
        } catch (IllegalStateException | IOException e) {
            modelAndView.addObject("msg", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            modelAndView.addObject("msg", "服务器内部异常");
        }

        modelAndView.setViewName("redirect:/user/showCenter");

        return modelAndView;
    }
}
