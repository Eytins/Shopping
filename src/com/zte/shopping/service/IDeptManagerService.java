package com.zte.shopping.service;

import com.zte.shopping.entity.SysDept;
import com.zte.shopping.exception.DeptExistException;
import com.zte.shopping.exception.LoginDisabledException;
import com.zte.shopping.exception.RequestParameterException;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Eytins
 */

public interface IDeptManagerService {

    List<SysDept> findAll();

    public SysDept findById(String id) throws RequestParameterException;

    public void modifyDept(String redeptName, String deptId, String deptName, String remark) throws DeptExistException;

    public void addDept(String deptName, String remark, HttpSession session) throws DeptExistException, LoginDisabledException;

    public void addSonDept(String fatherDeptId, String deptName, String remark, HttpSession session) throws DeptExistException, LoginDisabledException;

    public void modifyStatus(String deptId, String isValid) throws RequestParameterException;
}
