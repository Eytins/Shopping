package com.zte.shopping.mapper;

import com.zte.shopping.entity.Staff;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Eytins
 */

public interface IStaffMapper {
    Staff staffLogin(@Param(value = "username") String username,
                     @Param(value = "password") String password,
                     @Param(value = "userRole") String userRole);

    /**
     * 动态  模糊  查询   管理员信息
     */
    List<Staff> selectFuzzyByParams(Staff staff);

    /**
     * 添加管理员
     */
    void insertStaff(Staff staff);

    /**
     * 根据账号查询员工信息
     */
    Staff selectByLoginName(String loginName);

    /**
     * 修改管理员
     */
    void updateStaff(Staff staff);

    /**
     * 做修改管理员操作时  查询出修改页面的管理员信息
     */
    Staff selectById(int parseInt);

    /**
     * 修改管理员状态   启用/禁用
     */
    void updateStaffStatus(int parseInt, Integer staffStatus);

}
