package com.zte.shopping.mapper;

import com.zte.shopping.entity.Dept;

import java.util.List;

/**
 * Created by Eytins
 */

public interface IDeptManagerMapper {
    List<Dept> selectAll();

    Dept selectById(int parseInt);

    Dept selectByName(String name);

    Dept selectByIdAndName(int parseInt, String deptName);

    void updateDept(int parseInt, String deptName, String remark);

    void insertDept(Dept dept);

    void insertSonDept(Dept d);

    void updateStatus(int parseInt, Integer status);

    List<Dept> selectEnabledDeptList(int deptStatusEnable);

}
