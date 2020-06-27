package com.zte.shopping.action;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zte.shopping.constant.DictConstant;
import com.zte.shopping.entity.SysProduct;
import com.zte.shopping.service.IProductService;
import com.zte.shopping.service.IProductTypeService;
import com.zte.shopping.util.ParameterUtil;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Eytins
 */

@Controller
@RequestMapping(value = "product")
public class ProductController {
    @Autowired
    private IProductService iProductService;

    @Autowired
    private IProductTypeService iProductTypeService;


    //查询商品信息列表

    @RequestMapping("/findAll")
    public ModelAndView findAll(String pageNo, String pageSize) {
        ModelAndView modelAndView = new ModelAndView();

        if (ParameterUtil.isnull(pageNo)) {
            //若为空则默认为1
            pageNo = DictConstant.PAGE_NO;
        }

        if (ParameterUtil.isnull(pageSize)) {
            //默认每页5条数据
            pageSize = DictConstant.PAGE_SIZE;
        }

        PageHelper.startPage(Integer.parseInt(pageNo), Integer.parseInt(pageSize));

        List<SysProduct> sysProductList = iProductService.findAll();

        PageInfo<SysProduct> sysProductPageInfo = new PageInfo<SysProduct>(sysProductList);

        modelAndView.addObject("sysProductPageInfo", sysProductPageInfo);
        modelAndView.setViewName("backend/productManager");

        return modelAndView;
    }

    /**
     * 添加商品信息
     * 文件上传操作
     */
    @RequestMapping(value = "/addProduct", method = {RequestMethod.GET, RequestMethod.POST})
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
        System.out.println(productNo);
        try {
            iProductService.addProduct(file, productNo, productName, price, typeId, session);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        modelAndView.setViewName("redirect:findAll?pageNo" + pageNo);
        return modelAndView;
    }
}
