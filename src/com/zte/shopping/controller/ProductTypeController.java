package com.zte.shopping.controller;

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
@RequestMapping("/type")
public class ProductTypeController {
    @Autowired
    private IProductTypeService productTypeService;

    /**
     * 查询商品类型列表
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

        // 设置分页
        PageHelper.startPage(Integer.parseInt(pageNo), Integer.parseInt(pageSize));

        // 查询所有数据
        List<ProductType> typeList = productTypeService.findAll();

        // 将查询出的所有数据进行分页设置,封装为PageInfo对象
        PageInfo<ProductType> pageProductTypeList = new PageInfo<ProductType>(typeList);

        if (pageProductTypeList.getList().isEmpty() && pageProductTypeList.getPageNum() != -1) {
            for (int i = 0; i < pageProductTypeList.getList().size(); i++) {
                System.out.println(pageProductTypeList.getList().get(i).getName() + "---------");
            }
        }


        modelAndView.addObject("pageProductTypeList", pageProductTypeList);


        modelAndView.setViewName("backend/productTypeManager");

        return modelAndView;
    }

    /**
     * 添加商品类型(采用ajax来添加)
     * <p>
     * 我们需要让SpringMVC知道,我们需要返回的是数据模型 ---> 采用@ResponseBoby注解标注在当前方法addType上
     *
     * @RequestMapping 注解中的produces属性表示:设置响应体的格式 即在此方法上加上  @RequestMapping(value = "/addType",produces="text/html;charset=utf-8") 为了解决此方法的响应数据乱码问题
     */
    @ResponseBody
    @RequestMapping(value = "/addType")
    public ResponseResult addType(String name) {
        ResponseResult result = new ResponseResult();

        try {
            productTypeService.addType(name);

            result.setMessage("成功");
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_SUCCESS);

        } catch (ProductTypeExistException e) {
            e.printStackTrace();
            result.setMessage(e.getMessage());
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_FAIL);

        } catch (RequestParameterException e) {
            e.printStackTrace();
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
     * 改变商品类型的状态:启用/禁用
     * 当选择的商品类型   "启用"  ---> "禁用"
     * 当选择的商品类型   "禁用"  ---> "启用"
     */
    @RequestMapping("/modifyProductTypeStatus")
    public ModelAndView modifyStatus(String id, String status, String pageNo) {
        ModelAndView modelAndView = new ModelAndView();

        productTypeService.modifyStatus(id, status);

        modelAndView.setViewName("redirect:findAll?pageNo" + pageNo);

        return modelAndView;
    }

    /**
     * 根据 商品类型ID 查询   商品类型信息
     */
    @ResponseBody
    @RequestMapping("/findById")
    public ResponseResult findById(String id) {
        ResponseResult result = new ResponseResult();


        try {
            ProductType type = productTypeService.findById(id);

            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_SUCCESS);
            result.setReturnObject(type);
        } catch (RequestParameterException e) {
            result.setMessage(e.getMessage());

            // 响应状态码为请求参数有误,值为:3
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_REQUEST_PARAMETER_ERROR);

        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("服务器内部异常");
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_FAIL);

        }

        return result;
    }

    /**
     * 根据 商品类型ID 修改   商品类型信息
     */
    @ResponseBody
    @RequestMapping("/modifyName")
    public ResponseResult modifyName(String id, String name) {
        ResponseResult result = new ResponseResult();

        try {
            productTypeService.modifyName(id, name);

            result.setMessage("成功");
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("服务器内部异常");
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_FAIL);
        }

        return result;
    }
}
