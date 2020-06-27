package com.zte.shopping.mapper;

import com.zte.shopping.entity.SysProductType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Eytins
 */

public interface IProductTypeMapper {
    List<SysProductType> findAll();

    SysProductType selectProductTypeByName(@Param("name") String name);

    void insertProductType(@Param("name") String name, @Param("productTypeStatusEnable") int productTypeStatusEnable);

    void updateStatus(@Param("parseInt") int parseInt, @Param("typeStatus") int typeStatus);

    SysProductType selectByName(@Param("name") String name);

    void updateName(@Param("parseInt") int parseInt, @Param("name") String name);

    SysProductType selectById(@Param("parseInt") int parseInt);
}
