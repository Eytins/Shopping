package com.zte.shopping.service.impl;

import com.zte.shopping.constant.DictConstant;
import com.zte.shopping.constant.StatusConstant;
import com.zte.shopping.entity.Product;
import com.zte.shopping.entity.ProductType;
import com.zte.shopping.mapper.IProductMapper;
import com.zte.shopping.param.ProductParameter;
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
    public List<Product> findAll() {
        return iProductMapper.selectAll();
    }

    @Override
    public void addProduct(CommonsMultipartFile file, String productNo, String productName, String price, String typeId, HttpSession session) throws FileUploadException {
        Product product = new Product();
        product.setProductNo(productNo);
        product.setName(productName);
        product.setPrice(Double.parseDouble(price));

        ProductType type = new ProductType();
        type.setId(Integer.parseInt(typeId));
        product.setProductType(type);
        // DictConstant.PRODUCT_NO_PREFIX表示 商品编号前缀 :SP
        product.setProductNo(DictConstant.PRODUCT_NO_PREFIX + new SimpleDateFormat("yyyyMMdd").format(new Date()));

        String path        = "/upload/" + new SimpleDateFormat("yyyyMMdd").format(new Date());
        String cp          = session.getServletContext().getRealPath(path);
        String orifileName = file.getOriginalFilename(); // 原始文件名

        product.setImage(path + "/" + orifileName);

        // 先做添加商品
        iProductMapper.insertProduct(product);

        // 上传商品图片到服务器
        File f = new File(cp);
        f.mkdirs();

        try {
            file.transferTo(new File(cp, orifileName));
        } catch (IllegalStateException | IOException e) {
            throw new FileUploadException("文件上传出错", e);
        }
    }

    public void removeById(String id) {
        iProductMapper.deleteById(Integer.parseInt(id));
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Product findById(String id) {
        return iProductMapper.selectById(Integer.parseInt(id));
    }

    @Override
    public void modifyProduct(CommonsMultipartFile file, String productId,
                              String name, String price, String typeId, HttpSession session) throws FileUploadException {
        Product product = new Product();
        product.setProductId(Integer.parseInt(productId));
        product.setName(name);
        product.setPrice(Double.parseDouble(price));

        ProductType type = new ProductType();
        type.setId(Integer.parseInt(typeId));

        product.setProductType(type);

        String path             = "/upload/" + new SimpleDateFormat("yyyyMMdd").format(new Date());
        String cp               = session.getServletContext().getRealPath(path);
        String originalFilename = file.getOriginalFilename();

        // 解决当会员在修改商品时  没有 修改商品的图片的bug
        String image = "";
        if (originalFilename != null && "".equals(originalFilename)) {
            Product pro = iProductMapper.selectById(Integer.parseInt(productId));
            if (pro != null) {
                String imageStr = pro.getImage();
                image = path + "/" + imageStr.substring(imageStr.lastIndexOf("/") + 1, imageStr.length());
            }
        } else {
            image = path + "/" + originalFilename;
        }
        product.setImage(image);

        iProductMapper.updateProduct(product);

        File file1 = new File(cp);
        file1.mkdirs();

        try {
            file.transferTo(new File(cp, originalFilename));
        } catch (IllegalStateException | IOException e) {
            throw new FileUploadException("文件上传出错", e);
        }


    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Product> findProductFuzzyParamList(ProductParameter parameter)
    {
        // StatusConstant.PRODUCT_TYPE_STATUS_ENABLE 商品类型的状态为启用状态,值为1
        parameter.setStatus(StatusConstant.PRODUCT_TYPE_STATUS_ENABLE);

        return iProductMapper.selectByParamList(parameter);
    }
}
