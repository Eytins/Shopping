package com.zte.shopping.action;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zte.shopping.constant.DictConstant;
import com.zte.shopping.constant.ResponseCodeConstant;
import com.zte.shopping.entity.Dept;
import com.zte.shopping.entity.Staff;
import com.zte.shopping.exception.LoginDisabledException;
import com.zte.shopping.exception.NoPromissionException;
import com.zte.shopping.exception.RequestParameterException;
import com.zte.shopping.exception.StaffExistException;
import com.zte.shopping.service.IDeptManagerService;
import com.zte.shopping.service.IStaffService;
import com.zte.shopping.util.MD5;
import com.zte.shopping.util.ParameterUtil;
import com.zte.shopping.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Eytins
 */

@Controller
@RequestMapping(value = "staff")
public class StaffController {

    @Autowired
    private IStaffService iStaffService;

    @Autowired
    private IDeptManagerService iDeptManagerService;

    @RequestMapping(value = "login", method = {RequestMethod.POST, RequestMethod.GET})
    public String staffLogin(@RequestParam(value = "username") String userCode,
                             @RequestParam(value = "password") String password,
                             @RequestParam(value = "userRole") String userRole,
                             @RequestParam(value = "Code") String Code,
                             HttpSession session) {
        MD5   md5   = new MD5();
        Staff staff = this.iStaffService.staffLogin(userCode, md5.md5Change(password), userRole);
        if (staff != null && Code.equals(session.getAttribute("code"))) {
            session.setAttribute("staff", staff);
            session.setAttribute("staffName",staff.getStaffName());
            return "backend/admin_main";
        } else {
            return "backend/login";
        }
    }

    @RequestMapping(value = "exit", method = {RequestMethod.GET, RequestMethod.POST})
    public String staffExit(HttpSession session) {
        session.invalidate();
        return "backend/login";
    }

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

        List<Staff> staffList = iStaffService.findFuzzyByParamList(staffParameter, deptId);

        PageInfo<Staff> pageStaffList = new PageInfo<Staff>(staffList);

        modelAndView.addObject("pageStaffList", pageStaffList);
        modelAndView.addObject("staffParameter", staffParameter);
        modelAndView.addObject("deptId", deptId);

        //找出所有部门并添加到modelAndView里
        List<Dept> deptList = iDeptManagerService.findAll();
        modelAndView.addObject("deptList", deptList);

        modelAndView.setViewName("backend/staffManager");

        return modelAndView;
    }

    @ResponseBody
    @RequestMapping("/addStaff")
    public ResponseResult addStaff(Staff staff, String deptId, HttpSession session) {
        ResponseResult result = new ResponseResult();

        try {
            iStaffService.addStaff(staff, deptId, session);

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

    @ModelAttribute("enabledDeptList")
    public List<Dept> loadEnabledDeptList() {
        return iDeptManagerService.findEnabledDeptList();
    }

    @ResponseBody
    @RequestMapping("/modifyStaff")
    public ResponseResult modifyStaff(Staff staff, String deptId) {
        ResponseResult result = new ResponseResult();

        try {
            iStaffService.modifyStaff(staff, deptId);

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

    @ResponseBody
    @RequestMapping("/findById")
    public ResponseResult findById(String staffId) {
        ResponseResult result = new ResponseResult();

        try {
            Staff staff = iStaffService.findById(staffId);

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

    @ResponseBody
    @RequestMapping("/modifyStaffStatus")
    public ResponseResult modifyStatus(String staffId, String isValid) {
        ResponseResult result = new ResponseResult();

        try {
            iStaffService.modifyStaffStatus(staffId, isValid);

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
