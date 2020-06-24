package com.zte.shopping.mapper;

import org.apache.ibatis.annotations.Param;

public interface IUserMapper {
    int dologin(@Param(value = "username") String username,
                @Param(value = "password") String password,
                @Param(value = "userRole") String userRole);
}
