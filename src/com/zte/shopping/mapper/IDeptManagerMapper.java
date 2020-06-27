package com.zte.shopping.mapper;

import com.zte.shopping.entity.SysDept;

import java.util.List;

/**
 * Created by Eytins
 */

public interface IDeptManagerMapper {
    public List<SysDept> selectAll();

    public SysDept selectById(int parseInt);

    public SysDept selectByName(String name);

    public SysDept selectByIdAndName(int parseInt, String deptName);

    public void updateDept(int parseInt, String deptName, String remark);

    public void insertDept(SysDept dept);

    public void insertSonDept(SysDept d);

    public void updateStatus(int parseInt, Integer status);
}
