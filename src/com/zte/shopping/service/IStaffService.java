package com.zte.shopping.service;

import com.zte.shopping.entity.Staff;
import com.zte.shopping.exception.LoginDisabledException;
import com.zte.shopping.exception.NoPromissionException;
import com.zte.shopping.exception.RequestParameterException;
import com.zte.shopping.exception.StaffExistException;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Eytins
 */

public interface IStaffService {

    Staff staffLogin(String username, String password, String userRole);

    List<Staff> findFuzzyByParamList(Staff staffParameter, String deptId);

    void addStaff(Staff staff, String deptId, HttpSession session) throws LoginDisabledException, NoPromissionException, StaffExistException;

    void modifyStaff(Staff staff, String deptId) throws RequestParameterException;

    Staff findById(String staffId) throws RequestParameterException;

    void modifyStaffStatus(String staffId, String isValid) throws RequestParameterException;

}
