package com.zte.shopping.service;

import com.zte.shopping.entity.User;
import com.zte.shopping.exception.RequestParameterException;
import com.zte.shopping.exception.UserExistException;

import java.util.List;

/**
 * Created by Eytins
 */

public interface IUserService {

    int dologin(String username, String password, String userRole);

    /**
     * 会员注册
     */
    void register(User user) throws UserExistException;

    /**
     * 条件查询
     * 根据前台页面所传递的参数选择合适的条件进行查询
     * 支持模糊查询
     */
    List<User> findByFuzzyParamList(User userParam);

    /**
     * 启用/禁用
     */
    void modifyStatus(String id, String status) throws RequestParameterException;

    /**
     * 根据id  查询 用户详情
     */
    User findById(String id) throws RequestParameterException;

    /**
     * 根据  会员id  修改会员信息
     * 账号不能重复
     */
    void modifyById(User user) throws UserExistException;

}
