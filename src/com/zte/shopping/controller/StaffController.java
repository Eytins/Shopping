package com.zte.shopping.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zte.shopping.constant.DictConstant;
import com.zte.shopping.constant.ResponseCodeConstant;
import com.zte.shopping.entity.Dept;
import com.zte.shopping.entity.Staff;
import com.zte.shopping.exception.*;
import com.zte.shopping.service.IDeptService;
import com.zte.shopping.service.IStaffService;
import com.zte.shopping.util.ParameterUtil;
import com.zte.shopping.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Eytins
 */


@Controller
@RequestMapping("/staff")
public class StaffController {
    @Autowired
    private IStaffService staffService;

    @Autowired
    private IDeptService deptService;

    /**
     * 登录
     */
    @RequestMapping("/login")
    public ModelAndView login(String loginName, String password, String code, String role, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();

        try {
            Staff staff = staffService.login(loginName, password, code, session, role);

            System.out.println("===================================================:" + staff.getStaffName());

            // 登录成功之后把员工放入Session作用域
            session.setAttribute("staff", staff);

            modelAndView.setViewName("backend/main");

        } catch (CodeErrorException e) {
            modelAndView.addObject("loginMsg", e.getMessage());
            modelAndView.setViewName("backend/login");

        } catch (SatffNotExistException e) {
            modelAndView.addObject("loginMsg", e.getMessage());
            modelAndView.setViewName("backend/login");

        } catch (Exception e) {
            modelAndView.addObject("loginMsg", "服务器内部异常");
            modelAndView.setViewName("backend/login");

        }

        return modelAndView;
    }

    /**
     * 退出
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "backend/login";
    }

    /**
     * 点击 管理员管理菜单操作 动态条件中: 查询出所有的部门信息 字段
     */
    @ModelAttribute("deptList")
    public List<Dept> loadAllDeptList() {
        return deptService.findAll();
    }

    /**
     * 点击 添加管理员   查询有效的部门信息
     */
    @ModelAttribute("enabledDeptList")
    public List<Dept> loadEnabledDeptList() {
        return deptService.findEnabledDeptList();
    }

    /**
     * 动态  模糊  查询   管理员信息
     */
    @RequestMapping("/findFuzzyByParamList")
    public ModelAndView findFuzzyByParamList(Staff staffParameter, String deptId, String pageNo, String pageSize) {
        ModelAndView modelAndView = new ModelAndView();

        if (ParameterUtil.isnull(pageNo)) {
            pageNo = DictConstant.PAGE_NO;
        }

        if (ParameterUtil.isnull(pageSize)) {
            pageSize = DictConstant.PAGE_SIZE;
        }

        PageHelper.startPage(Integer.parseInt(pageNo), Integer.parseInt(pageSize));

        List<Staff> staffList = staffService.findFuzzyByParamList(staffParameter, deptId);

        PageInfo<Staff> pageStaffList = new PageInfo<Staff>(staffList);

        modelAndView.addObject("pageStaffList", pageStaffList);
        modelAndView.addObject("staffParameter", staffParameter);
        modelAndView.addObject("deptId", deptId);

        modelAndView.setViewName("backend/staffManager");

        return modelAndView;
    }

    /**
     * 添加管理员
     * <p>
     * 员工账号不能重复
     * 密码需要使用MD5加密
     * 创建人      为    当前登录员工
     * 创建时间   为   当前时间
     * <p>
     * 默认田间的员工为有效状态
     */
    @ResponseBody
    @RequestMapping("/addStaff")
    public ResponseResult addStaff(Staff staff, String deptId, HttpSession session) {
        ResponseResult result = new ResponseResult();

        try {
            staffService.addStaff(staff, deptId, session);

            result.setMessage("成功");
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_SUCCESS);

        } catch (StaffExistException e) {
            result.setMessage(e.getMessage());
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_FAIL);

        } catch (LoginDisabledException e) {
            result.setMessage(e.getMessage());
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_LOGIN_TIMEOUT);

        } catch (NoPromissionException e) {
            result.setMessage(e.getMessage());
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_NO_PROMISSON);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("服务器内部异常");
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_FAIL);
        }

        return result;
    }

    /**
     * 做修改管理员操作时  查询出修改页面的管理员信息
     * <p>
     * 根据id查询员工信息
     */
    @ResponseBody
    @RequestMapping("/findById")
    public ResponseResult findById(String staffId) {
        ResponseResult result = new ResponseResult();

        try {
            Staff staff = staffService.findById(staffId);

            result.setMessage("成功");
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_SUCCESS);

            result.setReturnObject(staff);

        } catch (RequestParameterException e) {
            result.setMessage(e.getMessage());
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_REQUEST_PARAMETER_ERROR);

        } catch (Exception e) {

            result.setMessage("服务器内部异常");
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_FAIL);
        }

        return result;
    }

    /**
     * 修改员工管理员
     */
    @ResponseBody
    @RequestMapping("/modifyStaff")
    public ResponseResult modifyStaff(Staff staff, String deptId) {
        ResponseResult result = new ResponseResult();

        try {
            staffService.modifyStaff(staff, deptId);

            result.setMessage("成功");
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_SUCCESS);

        } catch (RequestParameterException e) {
            result.setMessage(e.getMessage());
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_REQUEST_PARAMETER_ERROR);

        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("服务器内部异常");
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_FAIL);
        }

        return result;
    }

    /**
     * 管理员 启用/禁用
     */
    @ResponseBody
    @RequestMapping("/modifyStaffStatus")
    public ResponseResult modifyStatus(String staffId, String isValid) {
        ResponseResult result = new ResponseResult();

        try {
            staffService.modifyStaffStatus(staffId, isValid);

            result.setMessage("成功");
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_SUCCESS);

        } catch (RequestParameterException e) {
            result.setMessage(e.getMessage());
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_REQUEST_PARAMETER_ERROR);

        } catch (Exception e) {
            e.printStackTrace();

            result.setMessage("服务器内部异常");
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_FAIL);
        }

        return result;
    }

}
