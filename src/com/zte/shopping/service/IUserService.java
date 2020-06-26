package com.zte.shopping.service;

import com.zte.shopping.entity.User;
import com.zte.shopping.exception.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Eytins
 */

public interface IUserService {

    /**
     * 会员注册
     */
    public void regist(User user) throws UserExistException;

    /**
     * 会员登录
     */
    public User login(String loginName, String password, String code, HttpSession session) throws CodeErrorException, LoginNameOrPasswordErrorException;

    /**
     * 会员退出
     */
    public void logout(HttpSession session);

    /**
     * 根据用户ID 和  密码   判断原密码是否正确
     */
    public void isPasswordTrue(String userId, String password) throws PasswordErrorException;

    /**
     * 根据id  查询 用户详情
     */
    public User findById(String id) throws RequestParameterException;

    /**
     * 根据  会员id  修改会员信息
     * 账号不能重复
     */
    public void modifyById(User user) throws UserExistException;

    /**
     * 修改电话以及地址信息
     */
    public void modifyUser(String userId, String phone, String address);

    /**
     * 启用/禁用
     */
    public void modifyStatus(String id, String status) throws RequestParameterException;

    /**
     * 条件查询
     * 根据前台页面所传递的参数选择合适的条件进行查询
     * 支持模糊查询
     */
    public List<User> findByFuzzyParamList(User userParam);

    /**
     * 前台  密码会员修改
     */
    public void modifyPassword(String userId, String newPassword);


}
