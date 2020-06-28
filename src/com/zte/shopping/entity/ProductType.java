package com.zte.shopping.entity;

/**
 * Created by Eytins
 */

public class ProductType {

    private long   id;
    private String name;
    private long   status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

}
