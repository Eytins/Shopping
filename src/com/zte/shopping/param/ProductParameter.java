package com.zte.shopping.param;

import java.io.Serializable;

/**
 * Created by Eytins
 */

public class ProductParameter implements Serializable {

    private static final long serialVersionUID = 1L;

    private Double minPrice;

    private Double maxPrice;

    private Integer productTypeId;

    private Integer status;

    public ProductParameter() {

    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Integer getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(Integer productTypeId) {
        this.productTypeId = productTypeId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


}
