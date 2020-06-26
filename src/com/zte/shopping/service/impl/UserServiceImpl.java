package com.zte.shopping.service.impl;

import com.zte.shopping.constant.StatusConstant;
import com.zte.shopping.dao.IUserDao;
import com.zte.shopping.entity.User;
import com.zte.shopping.exception.*;
import com.zte.shopping.service.IUserService;
import com.zte.shopping.util.DataUtil;
import com.zte.shopping.util.MD5Util;
import com.zte.shopping.util.ParameterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * Created by Eytins
 */


@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserDao userDao;

    /**
     * 注册会员信息
     * 注册时要对密码进行加密
     * 默认新注册的用户均为有效用户
     * 默认注册的时间为当前时间
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public void regist(User user) throws UserExistException {
        // 根据   账号   查询会员信息
        User selectUser = userDao.selectByLoginName(user.getLoginName());

        if (selectUser != null) {
            throw new UserExistException("该账号已被使用");
        }

        // 注册时要对密码进行加密
        user.setPassword(MD5Util.md5(user.getPassword()));

        // 默认新注册的用户均为有效用户
        user.setIsValid(StatusConstant.USER_STATUS_ENABLE);

        // 默认注册的时间为当前时间
        user.setRegistDate(new Date());

        // 添加会员信息
        userDao.insertUser(user);
    }

    /**
     * 会员登录
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public User login(String loginName, String password, String code, HttpSession session) throws CodeErrorException, LoginNameOrPasswordErrorException {
        String image = (String) session.getAttribute("code");

		/*if (!image.equals(code))
		{
			throw new CodeErrorException("验证码不正确");
		}*/

        // 根据  账号    密码  是否有效    查询对应的会员信息
        User user = userDao.selectByLoginNameAndPassword(loginName, MD5Util.md5(password), StatusConstant.USER_STATUS_ENABLE);

        if (user == null) {
            throw new LoginNameOrPasswordErrorException("账号或者密码错误");
        }

        return user;
    }

    /**
     * 会员退出
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public void logout(HttpSession session) {
        session.invalidate();
    }

    /**
     * 前台  密码会员修改
     */
    public void modifyPassword(String userId, String newPassword) {
        userDao.updatePassword(Integer.parseInt(userId), MD5Util.md5(newPassword));
    }

    /**
     * 根据  会员id 和  密码   判断原密码是否正确
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public void isPasswordTrue(String userId, String password) throws PasswordErrorException {
        // 根据  用户ID 查询user信息
        User user = userDao.selectById(Integer.parseInt(userId));

        if (!MD5Util.md5(password).equals(user.getPassword())) {
            throw new PasswordErrorException();
        }
    }

    /**
     * (前台) 个人中心 根据会员id 修改会员 基本资料(会员的电话以及地址 )
     */
    public void modifyUser(String userId, String phone, String address) {
        userDao.updateUser(Integer.parseInt(userId), phone, address);
    }


    /**
     * 注意:
     * 要先到前台页面 http://localhost:8080/shopping_ssm/中先注册一个会员
     * 然后再后台的会员管理中才能看到
     * 会员组合条件查询(动态查询)
     * 根据前台页面所传递的参数选择合适的条件进行查询
     * 支持模糊查询
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<User> findByFuzzyParamList(User userParam) {
        User user = new User();

        user.setUserName(DataUtil.stringSpace(userParam.getUserName()));
        user.setLoginName(DataUtil.stringSpace(userParam.getLoginName()));
        user.setPhone(DataUtil.stringSpace(userParam.getPhone()));
        user.setAddress(DataUtil.stringSpace(userParam.getAddress()));
        user.setIsValid(userParam.getIsValid());

        return userDao.selectByParamList(user);
    }


    /**
     * 根据 id  查询用户详情(为修改会员信息服务)
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public User findById(String userId) throws RequestParameterException {
        if (ParameterUtil.isnull(userId)) {
            throw new RequestParameterException("用户id不能为空");
        }

        User user = userDao.selectById(Integer.parseInt(userId));

        return user;
    }


    /**
     * 根据  会员id  修改会员信息
     * 账号不能重复
     */
    public void modifyById(User user) throws UserExistException {
        // 根据  会员id  与  账号   查询符合条件的会员信息
        User selectUser = userDao.selectByIdAndLoginName(user.getUserId(), user.getLoginName());

        if (selectUser != null) {
            throw new UserExistException("该会员已经存在");
        }

        userDao.updateById(user);
    }


    /**
     * 启用/禁用
     * 根据 会员id  修改对应  会员的状态
     */
    public void modifyStatus(String userId, String status) throws RequestParameterException {
        if (ParameterUtil.isnull(userId)) {
            throw new RequestParameterException("用户id不能为空");
        }

        if (ParameterUtil.isnull(status)) {
            throw new RequestParameterException("用户状态不能为空");
        }


        int userStatus = Integer.parseInt(status);


        if (userStatus == StatusConstant.USER_STATUS_ENABLE)  // 如果  会员账号状态为有效(启用)   则改为失效(禁用)
        {
            userStatus = StatusConstant.USER_STATUS_DISABLE;
        } else                                                // 如果  会员账号状态为失效(禁用)   则改为有效(启用)
        {
            userStatus = StatusConstant.PRODUCT_TYPE_STATUS_ENABLE;
        }

        // 根据  会员id  修改对应会员的状态
        userDao.updateStatus(Integer.parseInt(userId), userStatus);
    }

}
