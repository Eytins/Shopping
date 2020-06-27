package com.zte.shopping.service.impl;

import com.zte.shopping.mapper.IUserMapper;
import com.zte.shopping.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Eytins
 */

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserMapper iUserMapper;

    @Override
    public int dologin(String username, String password, String userRole) {
        return this.iUserMapper.dologin(username, password, userRole);
    }
}
