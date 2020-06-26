package com.zte.shopping.service;

import com.zte.shopping.entity.Product;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import param.ProductParameter;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Eytins
 */

public interface IProductService {

    /**
     * 商品列表
     */
    List<Product> findAll();

    /**
     * 添加商品信息
     * 文件上传操作
     */
    public void addProduct(CommonsMultipartFile file, String productNo, String productName, String price, String typeId, HttpSession session) throws FileUploadException;

    /**
     * 根据id 查询商品信息
     */
    public Product findById(String id);

    /**
     * 修改商品信息
     */
    public void modifyProduct(CommonsMultipartFile file, String productId, String name, String price, String typeId, HttpSession session) throws FileUploadException;

    /**
     * 根据 id 删除商品信息
     */
    public void removeById(String id);

    /**
     * 根据条件查询商品信息
     */
    public List<Product> findProductFuzzyParamList(ProductParameter parameter);

}
