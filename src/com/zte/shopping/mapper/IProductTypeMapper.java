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

    /**
     * 进入  添加商品页面 后 加载的 商品类型列表数据(已经禁用的商品不能显示在下拉列表中)
     */
    List<ProductType> selectByProductTypeStatus(int productTypeStatusEnable);

}
