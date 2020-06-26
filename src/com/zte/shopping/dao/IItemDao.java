package com.zte.shopping.dao;

import com.zte.shopping.entity.Item;

/**
 * Created by Eytins
 */

public interface IItemDao {

    /**
     * 添加订单明细信息
     */
    void insertItem(Item item);

}
