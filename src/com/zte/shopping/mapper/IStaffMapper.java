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

    List<Staff> selectFuzzyByParams(Staff staff);

    void insertStaff(Staff staff);

    Staff selectByLoginName(String loginName);

    void updateStaff(Staff staff);

    Staff selectById(int parseInt);

    void updateStaffStatus(int parseInt, Integer staffStatus);

}
