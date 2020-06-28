package com.zte.shopping.service.impl;

import com.zte.shopping.constant.StatusConstant;
import com.zte.shopping.entity.ProductType;
import com.zte.shopping.exception.ProductTypeExistException;
import com.zte.shopping.exception.RequestParameterException;
import com.zte.shopping.mapper.IProductTypeMapper;
import com.zte.shopping.service.IProductTypeService;
import com.zte.shopping.util.ParameterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Eytins
 */

@Service
public class ProductTypeImpl implements IProductTypeService {
    @Autowired
    private IProductTypeMapper iProductMapper;

    @Override
    //Transactional声明式事务管理 propagation为可选事务的传播行为，readOnly为只读写的选择
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<ProductType> findAll() {
        return iProductMapper.findAll();
    }


    @Override
    public void addType(String name) throws RequestParameterException, ProductTypeExistException {
        if (ParameterUtil.isnull(name)) {
            throw new RequestParameterException("商品类型名称不能为空");
        }

        ProductType selectType = iProductMapper.selectProductTypeByName(name);
        //当已存在时抛出自定义异常
        if (selectType != null) {
            throw new ProductTypeExistException("该商品类型已经存在");
        }
        //进行添加操作，并默认状态为不可用
        iProductMapper.insertProductType(name, StatusConstant.PRODUCT_TYPE_STATUS_ENABLE);
    }

    @Override
    public void modifyStatus(String id, String status) {
        int typeStatus = Integer.parseInt(status);

        if (typeStatus == StatusConstant.PRODUCT_TYPE_STATUS_DISABLE) // 禁用   ---> 启用
        {
            typeStatus = StatusConstant.PRODUCT_TYPE_STATUS_ENABLE;
        } else {
            typeStatus = StatusConstant.PRODUCT_TYPE_STATUS_DISABLE;  // 启用  ---> 禁用
        }
        // 根据 商品类型的id  修改  商品类型状态
        iProductMapper.updateStatus(Integer.parseInt(id), typeStatus);
    }

    @Override
    public void modifyName(String id, String name) throws ProductTypeExistException {
        //新的类型名称不存在,则可以修改
        ProductType productType = iProductMapper.selectByName(name);

        // !pt2.getName().equals(name)表示新的类型名称不是原来的,抛出异常
        // pt1 != null 表示DB中不存在该名称,抛出异常
        if (productType != null) {
            throw new ProductTypeExistException("该类型名称已经存在!");
        }

        // 根据 商品类型的id  修改  商品类型名称
        iProductMapper.updateName(Integer.parseInt(id), name);
    }

    @Override
    //Transactional声明式事务管理 propagation为可选事务的传播行为，readOnly为只读写的选择
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public ProductType findById(String id) throws RequestParameterException {
        ProductType productType;
        try {
            productType = iProductMapper.selectById(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            throw new RequestParameterException("商品类型Id只能是数字");
        }
        return productType;
    }

    @Override
    public List<ProductType> findEnableProductTypeList() {
        return iProductMapper.selectByProductTypeStatus(StatusConstant.PRODUCT_TYPE_STATUS_ENABLE);
    }

}
