package com.zte.shopping.mapper;

import com.zte.shopping.entity.Product;

import java.util.List;

/**
 * Created by Eytins
 */

public interface IProductMapper {

    List<Product> selectAll();

    void insertProduct(Product product);

    /**
     * 根据 id 删除商品信息
     */
    void deleteById(int parseInt);

    /**
     * 根据id查询商品信息
     */
    Product selectById(int parseInt);

    /**
     * 修改商品信息
     */
    void updateProduct(Product product);

}
