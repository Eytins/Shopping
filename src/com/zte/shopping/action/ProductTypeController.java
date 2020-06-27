package com.zte.shopping.action;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zte.shopping.constant.DictConstant;
import com.zte.shopping.constant.ResponseCodeConstant;
import com.zte.shopping.entity.ProductType;
import com.zte.shopping.exception.ProductTypeExistException;
import com.zte.shopping.exception.RequestParameterException;
import com.zte.shopping.service.IProductTypeService;
import com.zte.shopping.util.ParameterUtil;
import com.zte.shopping.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by Eytins
 */

@Controller
@RequestMapping(value = "productType")
public class ProductTypeController {
    @Autowired
    private IProductTypeService iProductTypeService;

    @RequestMapping(value = "findAll")
    public ModelAndView findAll(String pageNo, String pageSize) {
        ModelAndView modelAndView = new ModelAndView();
        if (ParameterUtil.isnull(pageNo)) {
            pageNo = DictConstant.PAGE_NO;
        }
        if (ParameterUtil.isnull(pageSize)) {
            pageSize = DictConstant.PAGE_SIZE;
        }
        //利用PageHelper设置分页，第一个参数为页码，第二个参数为每页显示数据条数
        PageHelper.startPage(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
        //查询所有数据
        List<ProductType> productTypeList = iProductTypeService.findAll();

        PageInfo<ProductType> sysProductTypePageInfo = new PageInfo<>(productTypeList);
        modelAndView.addObject("sysProductTypePageInfo", sysProductTypePageInfo);
        modelAndView.setViewName("backend/productTypeManager");
        return modelAndView;
    }


    @RequestMapping(value = "/addType")
    @ResponseBody
    public ResponseResult addType(String name) {
        ResponseResult result = new ResponseResult();
        //利用自定义警告，写在exception中
        try {
            iProductTypeService.addType(name);
            result.setMessage("成功");
            // 响应状态码为成功,值为:1
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_SUCCESS);
        } catch (ProductTypeExistException e) {
            //响应失败，原因：响应状态码为失败,值为:2，为添加值已存在时报错
            e.printStackTrace();
            result.setMessage(e.getMessage());
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_FAIL);
        } catch (RequestParameterException e) {
            //参数响应失败，原因：响应状态码为请求参数有误,值为:3
            e.printStackTrace();
            result.setMessage(e.getMessage());
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_REQUEST_PARAMETER_ERROR);
        } catch (Exception e) {
            //响应失败，原因：响应状态码为失败,值为:2
            e.printStackTrace();
            result.setMessage("服务器内部异常");
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_FAIL);
        }
        return result;
    }

    /*
     *修改商品的可用状态
     * */
    @RequestMapping("/modifyProductTypeStatus")
    public ModelAndView modifyStatus(String id, String status, String pageNo) {
        ModelAndView modelAndView = new ModelAndView();

        iProductTypeService.modifyStatus(id, status);

        //MVC中利用redirect进行重定向到修改的可用界面
        modelAndView.setViewName("redirect:findAll?pageNo=" + pageNo);

        return modelAndView;
    }

    /*
     * 修改类型名称
     * */
    @RequestMapping("/modifyName")
    @ResponseBody
    public ResponseResult modifyName(String id, String name) {
        ResponseResult result = new ResponseResult();

        try {
            iProductTypeService.modifyName(id, name);
            result.setMessage("成功");
            //ResponseCodeConstant.RESPONSE_CODE_SUCCESS==1
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("服务器内部异常");
            //ResponseCodeConstant.RESPONSE_CODE_FAIL==2
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_FAIL);
        }
        return result;
    }

    /*
     * 搜索当前点击的ID下的类型数据，用于填充弹出模态框中数据
     * */
    @ResponseBody
    @RequestMapping("/findById")
    public ResponseResult findById(String id) {
        ResponseResult result = new ResponseResult();
        try {
            ProductType type = iProductTypeService.findById(id);
            //请求成功 ResponseCodeConstant.RESPONSE_CODE_SUCCESS==1
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_SUCCESS);
            result.setReturnObject(type);
        } catch (RequestParameterException e) {
            result.setMessage(e.getMessage());
            // 参数有误,值为:3
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_REQUEST_PARAMETER_ERROR);

        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("服务器内部异常");
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_FAIL);

        }

        return result;
    }
}
