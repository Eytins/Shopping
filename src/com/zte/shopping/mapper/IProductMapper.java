package com.zte.shopping.mapper;

import com.zte.shopping.entity.SysProduct;

import java.util.List;

/**
 * Created by Eytins
 */

public interface IProductMapper {

    List<SysProduct> selectAll();

    void insertProduct(SysProduct product);
}
