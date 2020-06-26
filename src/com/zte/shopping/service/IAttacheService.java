package com.zte.shopping.service;

import com.zte.shopping.entity.Attache;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Eytins
 */

public interface IAttacheService {

    /**
     * 查询当前用户的头像
     */
    Attache findHeadImageByUserId(HttpSession session);

    /**
     * 查询当前用户的生活照片
     */
    List<Attache> findLifeImageByUserId(HttpSession session);

    /**
     * 修改用户头像
     * 判断该用户是否拥有头像
     * 若该用户尚未上传头像,则为添加
     * 若该用户已经有头像,则为修改
     */
    void modifyHeadImage(CommonsMultipartFile file, HttpSession session) throws FileUploadException;

    /**
     * 上传附件
     */
    void addAttache(Attache attache);

}
