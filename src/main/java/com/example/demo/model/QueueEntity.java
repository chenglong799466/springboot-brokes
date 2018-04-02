package com.example.demo.model;

public class QueueEntity {
    private String userPhone;
    private long productId;

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public QueueEntity(String userPhone, long productId) {
        this.userPhone = userPhone;
        this.productId = productId;
    }

    public QueueEntity() {
    }
}
