package com.zte.shopping.service;

import com.zte.shopping.entity.SysProduct;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Eytins
 */

public interface IProductService {

    List<SysProduct> findAll();

    void addProduct(CommonsMultipartFile file, String productNo, String productName, String price, String typeId, HttpSession session) throws FileUploadException;
}
