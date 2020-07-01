package com.zte.shopping.service;

import com.zte.shopping.exception.RequestParameterException;
import com.zte.shopping.exception.UserNotLoginException;
import com.zte.shopping.vo.CartVo;

/**
 * Created by Eytins
 */

public interface ICartService {

    void addCart(CartVo cartVo, String productId) throws RequestParameterException, UserNotLoginException;

    void modifyNum(String productId, String num, CartVo cartVo) throws RequestParameterException;

    void removeByProductId(String productId, CartVo cartVo) throws RequestParameterException;

}
