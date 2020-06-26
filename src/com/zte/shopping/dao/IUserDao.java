package com.zte.shopping.dao;

import com.zte.shopping.entity.User;

import java.util.List;

/**
 * Created by Eytins
 */

public interface IUserDao {
    /**
     * 根据   账号   查询会员信息
     */
    public User selectByLoginName(String loginName);

    /**
     * 添加会员信息
     */
    public void insertUser(User user);

    /**
     * 根据  账号    密码  是否有效    查询对应的会员信息
     */
    public User selectByLoginNameAndPassword(String loginName, String md5, int userStatusEnable);

    /**
     * 根据  会员id 查询会员信息
     */
    public User selectById(int parseInt);

    /**
     * (前台) 个人中心 根据会员id 修改会员 基本资料(会员的电话以及地址 )
     */
    public void updateUser(int parseInt, String phone, String address);

    /**
     * 根据  会员id  修改对应会员的状态
     */
    public void updateStatus(int parseInt, int userStatus);

    /**
     * 根据  会员id  与  账号   查询符合条件的会员信息
     */
    public User selectByIdAndLoginName(Integer userId, String loginName);

    /**
     * 根据  会员id  修改会员信息
     */
    public void updateById(User user);

    /**
     * 会员组合条件查询(动态查询)
     * 根据前台页面所传递的参数选择合适的条件进行查询
     * 支持模糊查询
     */
    public List<User> selectByParamList(User user);

    /**
     * 前台  密码会员修改
     */
    public void updatePassword(int parseInt, String md5);

}