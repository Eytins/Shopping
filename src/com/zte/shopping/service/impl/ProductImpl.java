package com.zte.shopping.service.impl;

import com.zte.shopping.constant.DictConstant;
import com.zte.shopping.entity.SysProduct;
import com.zte.shopping.entity.SysProductType;
import com.zte.shopping.mapper.IProductMapper;
import com.zte.shopping.service.IProductService;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Eytins
 */

@Service
public class ProductImpl implements IProductService {
    @Autowired
    private IProductMapper iProductMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<SysProduct> findAll() {
        return iProductMapper.selectAll();
    }

    @Override
    public void addProduct(CommonsMultipartFile file, String productNo, String productName, String price, String typeId, HttpSession session) throws FileUploadException {
        SysProduct sysProduct = new SysProduct();
        sysProduct.setProductNo(productNo);
        sysProduct.setName(productName);
        sysProduct.setPrice(Double.parseDouble(price));

        SysProductType type = new SysProductType();
        type.setId(Integer.parseInt(typeId));
        sysProduct.setSysProductType(type);
        //标识符  + 年月日
        // DictConstant.PRODUCT_NO_PREFIX表示 商品编号前缀 :SP
        sysProduct.setProductNo(DictConstant.PRODUCT_NO_PREFIX + new SimpleDateFormat("yyyyMMdd").format(new Date()));

        String path        = "/upload/" + new SimpleDateFormat("yyyyMMdd").format(new Date());
        String cp          = session.getServletContext().getRealPath(path);
        String orifileName = file.getOriginalFilename(); // 原始文件名

        sysProduct.setImage(path + "/" + orifileName);

        // 先做添加商品
        iProductMapper.insertProduct(sysProduct);

        // 上传商品图片到服务器
        File f = new File(cp);
        f.mkdirs();

        try {
            file.transferTo(new File(cp, orifileName));
        } catch (IllegalStateException e) {
            // org.apache.commons.fileupload.FileUploadException 不是自定义异常
            throw new FileUploadException("文件上传出错", e);
        } catch (IOException e) {
            throw new FileUploadException("文件上传出错", e);
        }
    }
}
