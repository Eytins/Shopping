package com.zte.shopping.service;

import com.zte.shopping.entity.Dept;
import com.zte.shopping.exception.DeptExistException;
import com.zte.shopping.exception.LoginDisabledException;
import com.zte.shopping.exception.RequestParameterException;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Eytins
 */

public interface IDeptService {

    /**
     * 添加父部门
     * <p>
     * 部门名称不能重复
     * 部门编号的创建规则:部门前缀BM + 当前年月日  + 序列号
     */
    public void addFatherDept(String deptName, String remark, HttpSession session) throws DeptExistException, LoginDisabledException, LoginDisabledException;

    /**
     * 查询所有部门信息
     */
    List<Dept> findAll();

    /**
     * 添加子部门
     * <p>
     * 部门名称不能重复
     * 部门编号创建规则:部门前缀BM + 当前年月日  + 序列号
     */
    public void addSonDept(String fatherDeptId, String deptName, String remark, HttpSession session) throws DeptExistException, LoginDisabledException;

    /**
     * 子部门的 启用/禁用
     */
    public void modifyStatus(String deptId, String isValid) throws RequestParameterException;

    /**
     * 根据部门id查询部门信息
     */
    public Dept findById(String id) throws RequestParameterException;

    /**
     * 修改部门信息
     */
    public void modifyDept(String deptId, String deptName, String remark) throws DeptExistException;

    /**
     * 查询有效的部门信息
     */
    public List<Dept> findEnabledDeptList();

}
