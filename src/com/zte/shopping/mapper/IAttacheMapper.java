package com.zte.shopping.mapper;

import com.zte.shopping.entity.Attache;

import java.util.List;

/**
 * Created by Eytins
 */

public interface IAttacheMapper {

    /**
     * 根据附件的类型与所属用户的id查询对应的用户头像
     */
    Attache selectUserHeadImage(String attacheFileTypeHeadImage, Integer userId);

    /**
     * 根据附件的类型与所属用户的id查询对应的用户生活照信息
     */
    List<Attache> selectUserLifeImages(String attacheFileTypeLifeImages, Integer userId);

    /**
     * 添加附件信息
     */
    void insertAttache(Attache attache);

    /**
     * 修改附件信息
     */
    void updateAttache(Attache selectAttache);

}
