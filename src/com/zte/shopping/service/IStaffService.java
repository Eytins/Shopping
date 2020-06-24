package com.zte.shopping.service;

import com.zte.shopping.entity.SysStaff;

public interface IStaffService {
    SysStaff staffLogin(String username, String password, String userRole);
}
