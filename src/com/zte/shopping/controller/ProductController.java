package com.zte.shopping.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zte.shopping.constant.DictConstant;
import com.zte.shopping.constant.ResponseCodeConstant;
import com.zte.shopping.entity.Product;
import com.zte.shopping.entity.ProductType;
import com.zte.shopping.service.IProductService;
import com.zte.shopping.service.IProductTypeService;
import com.zte.shopping.util.ParameterUtil;
import com.zte.shopping.util.ResponseResult;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import param.ProductParameter;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Eytins
 */


@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private IProductService productService;

    @Autowired
    private IProductTypeService productTypeService;

    /**
     * 查询商品信息列表
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

        List<Product> productList = productService.findAll();

        PageInfo<Product> pageProductList = new PageInfo<Product>(productList);

        if (pageProductList.getList().isEmpty() && pageProductList.getPageNum() != -1) {
            for (int i = 0; i < pageProductList.getList().size(); i++) {
                System.out.println(pageProductList.getList().get(i).getProductNo() + "---------");
            }
        }

        modelAndView.addObject("pageProductList", pageProductList);
        modelAndView.setViewName("backend/productManager");

        return modelAndView;
    }

    /**
     * 进入  添加商品页面 后 加载的 商品类型列表数据(已经禁用的商品不能显示在下拉列表中)
     */
    @ModelAttribute("typeList")
    public List<ProductType> loadTypeList() {
        // 查询所有有效的商品类型
        List<ProductType> typeList = productTypeService.findEnableProductTypeList();

        return typeList;
    }

    /**
     * 添加商品信息
     * 文件上传操作
     */
    @RequestMapping("/addProduct")
    public ModelAndView addProduct(
            @RequestParam("file") CommonsMultipartFile file,
            String productNo,
            String productName,
            String price,
            String typeId,
            HttpSession session,
            String pageNo
    ) {

        ModelAndView modelAndView = new ModelAndView();

        try {
            productService.addProduct(file, productNo, productName, price, typeId, session);
        } catch (FileUploadException e) {
            modelAndView.addObject("productMsg", e.getMessage());
        }

        modelAndView.setViewName("redirect:findAll?pageNo" + pageNo);

        return modelAndView;
    }

    /**
     * 跳到商品修改页面
     */
    @ResponseBody
    @RequestMapping("/findById")
    public ResponseResult findById(String id) {
        ResponseResult result = new ResponseResult();

        // 根据id查询商品信息
        Product product = productService.findById(id);

        result.setMessage("成功");
        result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_SUCCESS);
        result.setReturnObject(product);

        return result;
    }

    /**
     * 商品修改操作
     */
    @RequestMapping("/modifyProduct")
    public ModelAndView modifyProduct(
            @RequestParam("file") CommonsMultipartFile file,
            String productId,
            String name,
            String price,
            String typeId,
            HttpSession session,
            String pageNo) {
        ModelAndView modelAndView = new ModelAndView();

        try {
            productService.modifyProduct(file, productId, name, price, typeId, session);
        } catch (FileUploadException e) {
            modelAndView.addObject("productMsg", e.getMessage());
        }

        modelAndView.setViewName("redirect:findAll?pageNo=" + pageNo);
        return modelAndView;
    }

    /**
     * @param id
     * @param pageNo
     * @return
     */
    @RequestMapping("/removeById")
    public String removeById(String id, String pageNo) {
        productService.removeById(id);
        return "redirect:findAll?pageNo=" + pageNo;
    }

    /**
     * 前台--产品管理页面
     * 根据条件查询商品信息
     */
    @RequestMapping("/findProductFuzzyParamList")
    public ModelAndView findProductFuzzyParamList(ProductParameter parameter, String pageNo, String pageSize) {
        ModelAndView modelAndView = new ModelAndView();

        if (ParameterUtil.isnull(pageNo)) {
            pageNo = DictConstant.PAGE_NO;
        }

        if (ParameterUtil.isnull(pageSize)) {
            pageSize = DictConstant.INDEX_PAGE_SIZE;
        }

        PageHelper.startPage(Integer.parseInt(pageNo), Integer.parseInt(pageSize));


        List<Product> productList = productService.findProductFuzzyParamList(parameter);

        PageInfo<Product> pageProductList = new PageInfo<Product>(productList);

        modelAndView.addObject("pageProductList", pageProductList);
        modelAndView.addObject("parameter", parameter);

        modelAndView.setViewName("main");

        return modelAndView;
    }
}
