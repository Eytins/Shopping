package com.zte.shopping.mapper;

import com.zte.shopping.entity.SysStaff;
import org.apache.ibatis.annotations.Param;

public interface IStaffMapper {
    SysStaff staffLogin(@Param(value = "username") String username,
                        @Param(value = "password") String password,
                        @Param(value = "userRole") String userRole);
}
