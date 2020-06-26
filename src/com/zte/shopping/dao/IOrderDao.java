package com.zte.shopping.dao;

import com.zte.shopping.entity.Order;

import java.util.List;

/**
 * Created by Eytins
 */

public interface IOrderDao {

    /**
     * 生成订单信息
     */
    void insertOrder(Order order);

    /**
     * 根据userId查询对应的订单信息
     */
    List<Order> selectByUserId(Integer userId);

    /**
     * 根据订单id查询订单的详情
     */
    Order selectByOrderId(int parseInt);

}
