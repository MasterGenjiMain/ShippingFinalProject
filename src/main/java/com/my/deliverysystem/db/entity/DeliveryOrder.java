package com.my.deliverysystem.db.entity;

import java.util.Date;

public class DeliveryOrder {
    private long id;
    private long locationFromID;
    private long locationToId;
    private String address;
    private long deliveryTypeId;
    private double weight;
    private double volume;
    private Date receivingDate;
    private long tariffId;
    private long receiptId;

    public DeliveryOrder() {
    }

    public DeliveryOrder(long id, long locationFromID, long locationToId,
                         String address, long deliveryTypeId, double weight,
                         double volume, Date receivingDate, long tariffId, long receiptId) {
        this.id = id;
        this.locationFromID = locationFromID;
        this.locationToId = locationToId;
        this.address = address;
        this.deliveryTypeId = deliveryTypeId;
        this.weight = weight;
        this.volume = volume;
        this.receivingDate = receivingDate;
        this.tariffId = tariffId;
        this.receiptId = receiptId;
    }

    public DeliveryOrder(long id, long locationFromID, long locationToId,
                         String address, long deliveryTypeId, double weight,
                         double volume, long tariffId, long receiptId) {
        this.id = id;
        this.locationFromID = locationFromID;
        this.locationToId = locationToId;
        this.address = address;
        this.deliveryTypeId = deliveryTypeId;
        this.weight = weight;
        this.volume = volume;
        this.tariffId = tariffId;
        this.receiptId = receiptId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getlocationfromid() {
        return locationFromID;
    }

    public void setLocationFromID(long locationFromID) {
        this.locationFromID = locationFromID;
    }

    public long getLocationToId() {
        return locationToId;
    }

    public void setLocationToId(long locationToId) {
        this.locationToId = locationToId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getDeliveryTypeId() {
        return deliveryTypeId;
    }

    public void setDeliveryTypeId(long deliveryTypeId) {
        this.deliveryTypeId = deliveryTypeId;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public Date getReceivingDate() {
        return receivingDate;
    }

    public void setReceivingDate(Date receivingDate) {
        this.receivingDate = receivingDate;
    }

    public long getTariffId() {
        return tariffId;
    }

    public void setTariffId(long tariffId) {
        this.tariffId = tariffId;
    }

    public long getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(long receiptId) {
        this.receiptId = receiptId;
    }

    @Override
    public String toString() {
        return "DeliveryOrder{" +
                "id=" + id +
                ", locationFromID=" + locationFromID +
                ", locationToId=" + locationToId +
                ", address='" + address + '\'' +
                ", deliveryTypeId=" + deliveryTypeId +
                ", weight=" + weight +
                ", volume=" + volume +
                ", receivingDate=" + receivingDate +
                ", tariffId=" + tariffId +
                ", receiptId=" + receiptId +
                '}';
    }
}
