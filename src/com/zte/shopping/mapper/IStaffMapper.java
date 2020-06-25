package com.zte.shopping.mapper;

import com.zte.shopping.entity.Staff;

import java.util.List;

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
    public Staff selectByLoginNameAndPassword(String loginName, String password, Integer isValid, Integer role);

    /**
     * 动态  模糊  查询   管理员信息
     */
	public List<Staff> selectFuzzyByParams(Staff staff);

	/**
	 * 根据账号查询员工信息
	 */
	public Staff selectByLoginName(String loginName);

	/**
	 * 添加管理员
	 */
	public void insertStaff(Staff staff);

	/**
	 * 做修改管理员操作时  查询出修改页面的管理员信息
	 */
	public Staff selectById(int parseInt);

	/**
	 * 修改管理员
	 */
	public void updateStaff(Staff staff);

	/**
	 * 修改管理员状态   启用/禁用
	 */
	public void updateStaffStatus(int parseInt, Integer staffStatus);
}
