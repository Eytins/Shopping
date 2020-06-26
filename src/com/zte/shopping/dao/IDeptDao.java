package com.zte.shopping.dao;

import com.zte.shopping.entity.Dept;

import java.util.List;

/**
 * Created by Eytins
 */

public interface IDeptDao {

    /**
     * 查询所有部门信息
     */
    public List<Dept> selectAll();

    /**
     * 根据部门名称查询部门信息
     */
    public Dept selectByName(String name);

    /**
     * 添加父部门
     */
    public void insertFatherDept(Dept dept);

    /**
     * 添加子部门
     */
    public void insertSonDept(Dept d);

    /**
     * 子部门的 启用/禁用
     */
    public void updateStatus(int parseInt, Integer status);

    /**
     * 根据   部门id 与   部门名称   查询部门信息
     */
    public Dept selectByIdAndName(int parseInt, String deptName);

    /**
     * 根据id查询部门信息
     */
    public Dept selectById(int parseInt);

    /**
     * 修改部门信息
     */
    public void updateDept(int parseInt, String deptName, String remark);

    /**
     * 根据状态查询出管理员的信息列表
     */
    public List<Dept> selectEnabledDeptList(int deptStatusEnable);

}
