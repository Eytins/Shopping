package com.zte.shopping.service;

import com.zte.shopping.entity.User;
import com.zte.shopping.exception.CodeErrorException;
import com.zte.shopping.exception.LoginNameOrPasswordErrorException;
import com.zte.shopping.exception.RequestParameterException;
import com.zte.shopping.exception.UserExistException;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Eytins
 */

public interface IUserService {

    int dologin(String username, String password, String userRole);

    void register(User user) throws UserExistException;

    List<User> findByFuzzyParamList(User userParam);

    void modifyStatus(String id, String status) throws RequestParameterException;

    User findById(String id) throws RequestParameterException;

    void modifyById(User user) throws UserExistException;

    /**
     * 会员退出
     */
    void logout(HttpSession session);

    /**
     * 前台  密码会员修改
     */
    void modifyPassword(String userId, String newPassword);

    /**
     * 会员注册
     */
    void regist(User user) throws UserExistException;

    /**
     * 会员登录
     */
    User login(String loginName, String password, String code, HttpSession session) throws CodeErrorException, LoginNameOrPasswordErrorException;

    /**
     * 修改电话以及地址信息
     */
    void modifyUser(String userId, String phone, String address);


}
