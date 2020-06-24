package com.zte.shopping.service.impl;

import com.zte.shopping.entity.SysStaff;
import com.zte.shopping.mapper.IStaffMapper;
import com.zte.shopping.service.IStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaffServiceImpl implements IStaffService {

    @Autowired
    private IStaffMapper iStaffMapper;


    @Override
    public SysStaff staffLogin(String username, String password, String userRole) {
        return this.iStaffMapper.staffLogin(username, password, userRole);
    }
}
