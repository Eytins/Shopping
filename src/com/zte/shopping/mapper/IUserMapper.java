package com.zte.shopping.mapper;

import com.zte.shopping.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Eytins
 */

public interface IUserMapper {

    int dologin(@Param(value = "username") String username,
                @Param(value = "password") String password,
                @Param(value = "userRole") String userRole);

    List<User> selectByParamList(User user);

    User selectByLoginName(String loginName);

    void insertUser(User user);

    void updateStatus(int parseInt, int userStatus);

    User selectById(int parseInt);

    User selectByIdAndLoginName(Integer userId, String loginName);

    void updateById(User user);

    /**
     * 前台  密码会员修改
     */
    void updatePassword(int parseInt, String md5);

    /**
     * 根据  账号    密码  是否有效    查询对应的会员信息
     */
    User selectByLoginNameAndPassword(String loginName, String md5, int userStatusEnable);

    /**
     * (前台) 个人中心 根据会员id 修改会员 基本资料(会员的电话以及地址 )
     */
    void updateUser(int parseInt, String phone, String address);

}
