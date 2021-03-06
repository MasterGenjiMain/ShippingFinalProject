package com.my.deliverysystem.db.entity;

public class Receipt {
    private long id;
    private long userId;
    private long managerId;
    private double price;
    private long receiptStatusId;
    private long deliveryOrderId;

    public Receipt() {
    }

    public Receipt(long id, long userId, long managerId, double price, long receiptStatusId, long deliveryOrderId) {
        this.id = id;
        this.userId = userId;
        this.managerId = managerId;
        this.price = price;
        this.receiptStatusId = receiptStatusId;
        this.deliveryOrderId = deliveryOrderId;
    }

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

    public long getManagerId() {
        return managerId;
    }

    public void setManagerId(long managerId) {
        this.managerId = managerId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getReceiptStatusId() {
        return receiptStatusId;
    }

    public void setReceiptStatusId(long receiptStatusId) {
        this.receiptStatusId = receiptStatusId;
    }

    public long getDeliveryOrderId() {
        return deliveryOrderId;
    }

    public void setDeliveryOrderId(long deliveryOrderId) {
        this.deliveryOrderId = deliveryOrderId;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "id=" + id +
                ", userId=" + userId +
                ", managerId=" + managerId +
                ", price=" + price +
                ", receiptStatusId=" + receiptStatusId +
                ", deliveryOrderId=" + deliveryOrderId +
                '}';
    }
}
