package com.zte.shopping.service;

import com.zte.shopping.entity.SysProductType;
import com.zte.shopping.exception.ProductTypeExistException;
import com.zte.shopping.exception.RequestParameterException;

import java.util.List;

/**
 * Created by Eytins
 */

public interface IProductTypeService {

    List<SysProductType> findAll();

    //添加商品类型，并抛出异常
    void addType(String name) throws RequestParameterException, ProductTypeExistException;

    //修改商品可用状态
    void modifyStatus(String id, String status);

    //修改的类型名称不能与已有的重复，如果重复直接抛出异常即可
    void modifyName(String id, String name) throws ProductTypeExistException;

    //搜索当前ID下的数据，并可以发出请求错误
    SysProductType findById(String id) throws RequestParameterException;
}
