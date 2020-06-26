package com.zte.shopping.dao;

import com.zte.shopping.entity.Product;
import param.ProductParameter;

import java.util.List;

/**
 * Created by Eytins
 */

public interface IProductDao {

    /**
     * 查询所有商品信息
     */
    List<Product> selectAll();

    /**
     * 添加商品
     */
    void insertProduct(Product product);

    /**
     * 根据id查询商品信息
     */
    Product selectById(int parseInt);

    /**
     * 修改商品信息
     */
    void updateProduct(Product product);

    /**
     * 根据 id 删除商品信息
     */
    void deleteById(int parseInt);

    /**
     * 根据条件组合查询商品列表(支持模糊查询)
     */
    List<Product> selectByParamList(ProductParameter parameter);

}
