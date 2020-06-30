package com.zte.shopping.service;

import com.zte.shopping.entity.Product;
import com.zte.shopping.param.ProductParameter;
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

    void removeById(String id);

    void modifyProduct(CommonsMultipartFile file, String productId, String name, String price, String typeId, HttpSession session) throws FileUploadException;

    Product findById(String id);

    List<Product> findProductFuzzyParamList(ProductParameter parameter);

}
