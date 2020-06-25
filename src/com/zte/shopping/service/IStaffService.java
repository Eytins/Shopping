package com.zte.shopping.service;

import com.zte.shopping.entity.Staff;
import com.zte.shopping.exception.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 
 * @author liyan
 *
 */
public interface IStaffService 
{
	/**
	 * 管理员登录
	 * 
	 * 判断验证码是否正确
	 * 判断账号、密码与角色是否正确
	 * 
	 * @param loginName  账号
	 * @param password   密码
	 * @param code       验证码
	 * @param session    会话信息
	 * @param role       角色
	 * @return Staff     返回管理员信息
	 */
    Staff login(String loginName, String password, String code, HttpSession session, String role) throws CodeErrorException, SatffNotExistException;

    /**
     * 动态  模糊  查询   管理员信息
     * @param staffParameter  用户输入的跟管理员相关的动态条件
     * @param deptId     部门ID
     * @return  List<Staff>   返回后台管理员信息列表
     */
    List<Staff> findFuzzyByParamList(Staff staffParameter, String deptId);

	/**
	 * 添加管理员
	 * 
	 * 员工账号不能重复
	 * 密码需要使用MD5加密
	 * 创建人      为    当前登录员工
	 * 创建时间   为   当前时间
	 * 
	 * 默认田间的员工为有效状态
	 */
    void addStaff(Staff staff, String deptId, HttpSession session) throws LoginDisabledException, NoPromissionException, StaffExistException;

	/**
	 * 做修改管理员操作时  查询出修改页面的管理员信息
	 */
    Staff findById(String staffId) throws RequestParameterException;

	/**
	 * 修改员工管理员
	 */
    void modifyStaff(Staff staff, String deptId) throws RequestParameterException;

	/**
	 * 管理员 启用/禁用
	 */
    void modifyStaffStatus(String staffId, String isValid) throws RequestParameterException;
}
