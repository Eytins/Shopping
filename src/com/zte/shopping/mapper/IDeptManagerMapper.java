package com.zte.shopping.mapper;

import com.zte.shopping.entity.SysDept;

import java.util.List;

/**
 * Created by Eytins
 */

public interface IDeptManagerMapper {
    List<SysDept> selectAll();

    SysDept selectById(int parseInt);

    SysDept selectByName(String name);

    SysDept selectByIdAndName(int parseInt, String deptName);

    void updateDept(int parseInt, String deptName, String remark);

    void insertDept(SysDept dept);

    void insertSonDept(SysDept d);

    void updateStatus(int parseInt, Integer status);
}
