package com.zte.shopping.mapper;

import java.util.List;

import com.zte.shopping.entity.Staff;

public interface IStaffMapper 
{
	/**
	 * 根据账号、密码、是否有效、角色、查询有效的管理员信息
	 * 
	 * @param loginName  账号
	 * @param password   密码
	 * @param isValid    是否有效
	 * @param role       角色
	 * 
	 * @return Staff     管理员信息
	 */
    Staff selectByLoginNameAndPassword(String loginName, String password, Integer isValid, Integer role);

    /**
     * 动态  模糊  查询   管理员信息
     */
    List<Staff> selectFuzzyByParams(Staff staff);

	/**
	 * 根据账号查询员工信息
	 */
    Staff selectByLoginName(String loginName);

	/**
	 * 添加管理员
	 */
    void insertStaff(Staff staff);

	/**
	 * 做修改管理员操作时  查询出修改页面的管理员信息
	 */
    Staff selectById(int parseInt);

	/**
	 * 修改管理员
	 */
    void updateStaff(Staff staff);

	/**
	 * 修改管理员状态   启用/禁用
	 */
    void updateStaffStatus(int parseInt, Integer staffStatus);
}
