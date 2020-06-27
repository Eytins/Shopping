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

public interface IDeptManagerService {

    List<Dept> findAll();

    Dept findById(String id) throws RequestParameterException;

    void modifyDept(String redeptName, String deptId, String deptName, String remark) throws DeptExistException;

    void addDept(String deptName, String remark, HttpSession session) throws DeptExistException, LoginDisabledException;

    void addSonDept(String fatherDeptId, String deptName, String remark, HttpSession session) throws DeptExistException, LoginDisabledException;

    void modifyStatus(String deptId, String isValid) throws RequestParameterException;

    /**
     * 查询有效的部门信息
     */
    List<Dept> findEnabledDeptList();

}
