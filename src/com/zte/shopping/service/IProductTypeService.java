package com.zte.shopping.service;

import com.zte.shopping.entity.ProductType;
import com.zte.shopping.exception.ProductTypeExistException;
import com.zte.shopping.exception.RequestParameterException;

import java.util.List;

/**
 * Created by Eytins
 */

public interface IProductTypeService {

    List<ProductType> findAll();

    void addType(String name) throws RequestParameterException, ProductTypeExistException;

    void modifyStatus(String id, String status);

    void modifyName(String id, String name) throws ProductTypeExistException;

    ProductType findById(String id) throws RequestParameterException;

    List<ProductType> findEnableProductTypeList();
}
