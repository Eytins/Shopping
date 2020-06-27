package com.zte.shopping.entity;

/**
 * Created by Eytins
 */

public class SysProduct {

    private long           productId;
    private String         productNo;
    private String         name;
    private double         price;
    private String         image;
    private long           productTypeId;
    private SysProductType sysProductType;


    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }


    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public long getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(long productTypeId) {
        this.productTypeId = productTypeId;
    }

    public SysProductType getSysProductType() {
        return sysProductType;
    }

    public void setSysProductType(SysProductType sysProductType) {
        this.sysProductType = sysProductType;
    }
}
