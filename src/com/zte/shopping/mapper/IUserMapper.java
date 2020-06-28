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

}
