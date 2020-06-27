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

    /**
     * 会员组合条件查询(动态查询)
     * 根据前台页面所传递的参数选择合适的条件进行查询
     * 支持模糊查询
     */
    List<User> selectByParamList(User user);

    /**
     * 根据   账号   查询会员信息
     */
    User selectByLoginName(String loginName);

    /**
     * 添加会员信息
     */
    void insertUser(User user);

    /**
     * 根据  会员id  修改对应会员的状态
     */
    void updateStatus(int parseInt, int userStatus);

    /**
     * 根据  会员id 查询会员信息
     */
    User selectById(int parseInt);

    /**
     * 根据  会员id  与  账号   查询符合条件的会员信息
     */
    User selectByIdAndLoginName(Integer userId, String loginName);

    /**
     * 根据  会员id  修改会员信息
     */
    void updateById(User user);


}
