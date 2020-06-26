package com.zte.shopping.service;

import com.zte.shopping.entity.Order;
import com.zte.shopping.entity.User;
import com.zte.shopping.vo.CartVo;

import java.util.List;

/**
 * Created by Eytins
 */

public interface IOrderService {

    /**
     * 生成订单信息以及该订单名下的所有明细信息
     * 添加一个订单
     * 添加多个明细
     */
    String createOrder(User user, CartVo cart);

    /**
     * 查询某个会员 下的所有订单
     */
    List<Order> findByUserId(com.zte.shopping.entity.User user);

    /**
     * 根据订单id  查询该订单的详情
     */
    Order findByOrderId(String orderId);

}
