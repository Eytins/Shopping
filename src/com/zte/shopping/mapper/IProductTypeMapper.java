package com.zte.shopping.mapper;

import com.zte.shopping.entity.ProductType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Eytins
 */

public interface IProductTypeMapper {
    List<ProductType> findAll();

    ProductType selectProductTypeByName(@Param("name") String name);

    void insertProductType(@Param("name") String name, @Param("productTypeStatusEnable") int productTypeStatusEnable);

    void updateStatus(@Param("parseInt") int parseInt, @Param("typeStatus") int typeStatus);

    ProductType selectByName(@Param("name") String name);

    void updateName(@Param("parseInt") int parseInt, @Param("name") String name);

    ProductType selectById(@Param("parseInt") int parseInt);

    List<ProductType> selectByProductTypeStatus(int productTypeStatusEnable);

}
