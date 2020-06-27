package com.zte.shopping.service;

import com.zte.shopping.entity.SysStaff;

/**
 * Created by Eytins
 */

public interface IStaffService {
    SysStaff staffLogin(String username, String password, String userRole);
}
