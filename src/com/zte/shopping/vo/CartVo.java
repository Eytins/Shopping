package com.zte.shopping.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.zte.shopping.entity.Item;

/**
 * Created by Eytins
 */

public class CartVo implements Serializable {

    private static final long serialVersionUID = 1L;

    // 订单明细的集合
    private List<Item> items = new ArrayList<Item>();

    // !!! 注意这里的总价   要加默认值  不写默认值就是null
    private Double price = 0.0;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
