package com.my.deliverysystem.db.entity.bean;

public class ReceiptBean {
    private long id;
    private long userId;
    private String managerName;
    private double price;
    private String receiptStatusName;
    private long deliveryOrderId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getReceiptStatusName() {
        return receiptStatusName;
    }

    public void setReceiptStatusName(String receiptStatusName) {
        this.receiptStatusName = receiptStatusName;
    }

    public long getDeliveryOrderId() {
        return deliveryOrderId;
    }

    public void setDeliveryOrderId(long deliveryOrderId) {
        this.deliveryOrderId = deliveryOrderId;
    }

    @Override
    public String toString() {
        return "ReceiptBean{" +
                "id=" + id +
                ", userId=" + userId +
                ", managerName='" + managerName + '\'' +
                ", price=" + price +
                ", receiptStatusName='" + receiptStatusName + '\'' +
                ", deliveryOrderId=" + deliveryOrderId +
                '}';
    }
}
