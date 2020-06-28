package com.zte.shopping.service;

import com.zte.shopping.entity.User;
import com.zte.shopping.exception.RequestParameterException;
import com.zte.shopping.exception.UserExistException;

import java.util.List;

/**
 * Created by Eytins
 */

public interface IUserService {

    int dologin(String username, String password, String userRole);

    void register(User user) throws UserExistException;

    List<User> findByFuzzyParamList(User userParam);

    void modifyStatus(String id, String status) throws RequestParameterException;

    User findById(String id) throws RequestParameterException;

    void modifyById(User user) throws UserExistException;

}
