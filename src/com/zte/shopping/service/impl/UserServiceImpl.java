package com.zte.shopping.service.impl;

import com.zte.shopping.constant.StatusConstant;
import com.zte.shopping.entity.User;
import com.zte.shopping.exception.RequestParameterException;
import com.zte.shopping.exception.UserExistException;
import com.zte.shopping.mapper.IUserMapper;
import com.zte.shopping.service.IUserService;
import com.zte.shopping.util.DataUtil;
import com.zte.shopping.util.MD5;
import com.zte.shopping.util.ParameterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Eytins
 */

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserMapper iUserMapper;

    @Override
    public int dologin(String username, String password, String userRole) {
        return this.iUserMapper.dologin(username, password, userRole);
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public void register(User user) throws UserExistException {
        // 根据   账号   查询会员信息
        User selectUser = iUserMapper.selectByLoginName(user.getLoginName());

        if (selectUser != null) {
            throw new UserExistException("该账号已被使用");
        }

        // 注册时要对密码进行加密
        MD5 md5 = new MD5();
        user.setPassword(md5.md5Change(user.getPassword()));

        // 默认新注册的用户均为有效用户
        user.setIsValid(StatusConstant.USER_STATUS_ENABLE);

        // 默认注册的时间为当前时间
        user.setRegistDate(new Date());

        // 添加会员信息
        iUserMapper.insertUser(user);
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<User> findByFuzzyParamList(User userParam) {
        User user = new User();

        user.setUserName(DataUtil.stringSpace(userParam.getUserName()));
        user.setLoginName(DataUtil.stringSpace(userParam.getLoginName()));
        user.setPhone(DataUtil.stringSpace(userParam.getPhone()));
        user.setAddress(DataUtil.stringSpace(userParam.getAddress()));
        user.setIsValid(userParam.getIsValid());

        return iUserMapper.selectByParamList(user);
    }

    public void modifyStatus(String userId, String status) throws RequestParameterException {
        if (ParameterUtil.isnull(userId)) {
            throw new RequestParameterException("用户id不能为空");
        }

        if (ParameterUtil.isnull(status)) {
            throw new RequestParameterException("用户状态不能为空");
        }

        int userStatus = Integer.parseInt(status);

        if (userStatus == StatusConstant.USER_STATUS_ENABLE) {
            // 如果  会员账号状态为有效(启用)   则改为失效(禁用)
            userStatus = StatusConstant.USER_STATUS_DISABLE;
        } else {
            // 如果  会员账号状态为失效(禁用)   则改为有效(启用)
            userStatus = StatusConstant.PRODUCT_TYPE_STATUS_ENABLE;
        }

        // 根据  会员id  修改对应会员的状态
        iUserMapper.updateStatus(Integer.parseInt(userId), userStatus);
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public User findById(String userId) throws RequestParameterException {
        if (ParameterUtil.isnull(userId)) {
            throw new RequestParameterException("用户id不能为空");
        }

        User user = iUserMapper.selectById(Integer.parseInt(userId));

        return user;
    }

    public void modifyById(User user) throws UserExistException {
        // 根据  会员id  与  账号   查询符合条件的会员信息
        User selectUser = iUserMapper.selectByIdAndLoginName(user.getUserId(), user.getLoginName());

        if (selectUser != null) {
            throw new UserExistException("该会员已经存在");
        }

        iUserMapper.updateById(user);
    }

}
