package com.zte.shopping.action;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zte.shopping.constant.DictConstant;
import com.zte.shopping.constant.ResponseCodeConstant;
import com.zte.shopping.entity.Staff;
import com.zte.shopping.exception.*;
import com.zte.shopping.service.IStaffService;
import com.zte.shopping.util.ParameterUtil;
import com.zte.shopping.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 后台 --- 管理员管理Hanlder
 *
 * @author liyan
 */
@Controller
@RequestMapping("/staff")
public class StaffController {
    @Autowired
    private IStaffService staffService;

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


}
