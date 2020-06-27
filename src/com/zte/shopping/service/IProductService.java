package com.zte.shopping.service;

import com.zte.shopping.entity.Product;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Eytins
 */

public interface IProductService {

    List<Product> findAll();

    void addProduct(CommonsMultipartFile file, String productNo, String productName, String price, String typeId, HttpSession session) throws FileUploadException;

    /**
     * 根据 id 删除商品信息
     */
    void removeById(String id);

    /**
     * 修改商品信息
     */
    public void modifyProduct(CommonsMultipartFile file, String productId, String name, String price, String typeId, HttpSession session) throws FileUploadException;

    /**
     * 根据id 查询商品信息
     */
    public Product findById(String id);


}
