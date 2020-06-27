package com.zte.shopping.action;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zte.shopping.constant.DictConstant;
import com.zte.shopping.constant.ResponseCodeConstant;
import com.zte.shopping.entity.Dept;
import com.zte.shopping.exception.DeptExistException;
import com.zte.shopping.exception.LoginDisabledException;
import com.zte.shopping.exception.RequestParameterException;
import com.zte.shopping.service.IDeptManagerService;
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
 * Created by Eytins
 */

@Controller
@RequestMapping("dept")
public class DeptManagerController {
    @Autowired
    private IDeptManagerService iDeptManagerService;

    @RequestMapping("findAll")
    public ModelAndView findAll(String pageNo, String pageSize) {
        ModelAndView modelAndView = new ModelAndView();


        if (ParameterUtil.isnull(pageNo)) {
            pageNo = DictConstant.PAGE_NO;
        }

        if (ParameterUtil.isnull(pageSize)) {
            pageSize = DictConstant.PAGE_SIZE;
        }


        PageHelper.startPage(Integer.parseInt(pageNo), Integer.parseInt(pageSize));

        List<Dept> deptList = iDeptManagerService.findAll();

        PageInfo<Dept> sysDeptPageInfo = new PageInfo<Dept>(deptList);

        modelAndView.addObject("sysDeptPageInfo", sysDeptPageInfo);

        modelAndView.setViewName("backend/deptManager");

        return modelAndView;
    }

    @ResponseBody
    @RequestMapping("/findById")
    public ResponseResult findById(String id) {
        ResponseResult result = new ResponseResult();

        try {
            Dept dept = iDeptManagerService.findById(id);

            result.setMessage("成功");
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_SUCCESS);

            result.setReturnObject(dept);

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
    @RequestMapping("modifyDept")
    public ResponseResult modifyDept(String redeptName, String deptId, String deptName, String remark) {
        ResponseResult result = new ResponseResult();

        try {
            iDeptManagerService.modifyDept(redeptName, deptId, deptName, remark);
            result.setMessage("成功");
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_SUCCESS);

        } catch (DeptExistException e) {
            result.setMessage(e.getMessage());
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_FAIL);

        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("服务器内部异常");
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_FAIL);
        }

        return result;
    }

    //部门编号的创建规则:部门前缀BM + 当前年月日  + 序列号
    @ResponseBody
    @RequestMapping("/addDept")
    public ResponseResult addFatherDept(String deptName, String remark, HttpSession session) {
        ResponseResult result = new ResponseResult();

        try {
            iDeptManagerService.addDept(deptName, remark, session);

            result.setMessage("成功");
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_SUCCESS);

        } catch (DeptExistException e) {
            result.setMessage(e.getMessage());
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_FAIL);

        } catch (LoginDisabledException e) {
            result.setMessage(e.getMessage());
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_LOGIN_TIMEOUT);

        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("服务器内部异常");
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_FAIL);

        }

        return result;
    }

    @ResponseBody
    @RequestMapping("/addSonDept")
    public ResponseResult addSonDept(String fatherDeptId, String deptName, String remark, HttpSession session) {
        ResponseResult result = new ResponseResult();

        try {
            iDeptManagerService.addSonDept(fatherDeptId, deptName, remark, session);

            result.setMessage("成功");
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_SUCCESS);

        } catch (DeptExistException e) {
            result.setMessage(e.getMessage());
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_FAIL);

        } catch (LoginDisabledException e) {
            result.setMessage(e.getMessage());
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_LOGIN_TIMEOUT);

        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("服务器内部异常");
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_FAIL);

        }

        return result;
    }

    @ResponseBody
    @RequestMapping("/modifyStatus")
    public ResponseResult modifyStatus(String deptId, String isValid) {
        ResponseResult result = new ResponseResult();
        try {
            iDeptManagerService.modifyStatus(deptId, isValid);

            result.setMessage("成功");
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_SUCCESS);

        } catch (RequestParameterException e) {
            result.setMessage(e.getMessage());
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_REQUEST_PARAMETER_ERROR);

        } catch (Exception e) {
            result.setMessage("服务器内部异常");
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_FAIL);

        }

        return result;
    }
}
