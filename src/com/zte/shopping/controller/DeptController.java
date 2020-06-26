package com.zte.shopping.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zte.shopping.constant.DictConstant;
import com.zte.shopping.constant.ResponseCodeConstant;
import com.zte.shopping.entity.Dept;
import com.zte.shopping.exception.DeptExistException;
import com.zte.shopping.exception.LoginDisabledException;
import com.zte.shopping.exception.RequestParameterException;
import com.zte.shopping.service.IDeptService;
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
@RequestMapping("/dept")
public class DeptController {
    @Autowired
    private IDeptService deptService;

    /**
     * 查询所有部门信息
     */
    @RequestMapping("/findAll")
    public ModelAndView findAll(String pageNo, String pageSize) {
        ModelAndView modelAndView = new ModelAndView();

        if (ParameterUtil.isnull(pageNo)) {
            pageNo = DictConstant.PAGE_NO;
        }

        if (ParameterUtil.isnull(pageSize)) {
            pageSize = DictConstant.PAGE_SIZE;
        }

        PageHelper.startPage(Integer.parseInt(pageNo), Integer.parseInt(pageSize));

        List<Dept> deptList = deptService.findAll();

        PageInfo<Dept> pageDeptList = new PageInfo<Dept>(deptList);

        modelAndView.addObject("pageDeptList", pageDeptList);

        modelAndView.setViewName("backend/deptManager");

        return modelAndView;
    }

    /**
     * 添加父部门
     * <p>
     * 部门名称不能重复
     * 部门编号的创建规则:部门前缀BM + 当前年月日  + 序列号
     */
    @ResponseBody
    @RequestMapping("/addFatherDept")
    public ResponseResult addFatherDept(String deptName, String remark, HttpSession session) {
        ResponseResult result = new ResponseResult();

        try {
            deptService.addFatherDept(deptName, remark, session);

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

    /**
     * 添加子部门
     * <p>
     * 部门名称不能重复
     * 部门编号创建规则:部门前缀BM + 当前年月日  + 序列号
     */
    @ResponseBody
    @RequestMapping("/addSonDept")
    public ResponseResult addSonDept(String fatherDeptId, String deptName, String remark, HttpSession session) {
        ResponseResult result = new ResponseResult();

        try {
            deptService.addSonDept(fatherDeptId, deptName, remark, session);

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

    /**
     * 子部门的 启用/禁用
     */
    @ResponseBody
    @RequestMapping("/modifyStatus")
    public ResponseResult modifyStatus(String deptId, String isValid) {
        ResponseResult result = new ResponseResult();
        try {
            deptService.modifyStatus(deptId, isValid);

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

    /**
     * 跳出 修改部门信息  页面时  根据部门id查询要修改的部门信息
     */
    @ResponseBody
    @RequestMapping("/findById")
    public ResponseResult findById(String id) {
        ResponseResult result = new ResponseResult();

        try {
            Dept dept = deptService.findById(id);

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

    /**
     * 修改部门信息(不区分  父部门  还是  子部门)
     */
    @ResponseBody
    @RequestMapping("/modifyDept")
    public ResponseResult modifyDept(String deptId, String deptName, String remark) {
        ResponseResult result = new ResponseResult();

        try {
            deptService.modifyDept(deptId, deptName, remark);
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

}
