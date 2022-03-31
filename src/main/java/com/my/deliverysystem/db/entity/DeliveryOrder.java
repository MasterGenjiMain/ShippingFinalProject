package com.my.deliverysystem.db.entity;

import java.util.Date;

public class DeliveryOrder {
    private long id;
    private long locationFromID;
    private long locationToId;
    private String cargoName;
    private String cargoDescription;
    private String address;
    private long deliveryTypeId;
    private double weight;
    private double volume;
    private Date receivingDate;
    private long tariffId;
    private long receiptId;

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

    public long getLocationFromID() {
        return locationFromID;
    }

    public String getCargoName() {
        return cargoName;
    }

    public void setCargoName(String cargoName) {
        this.cargoName = cargoName;
    }

    public String getCargoDescription() {
        return cargoDescription;
    }

    public void setCargoDescription(String cargoDescription) {
        this.cargoDescription = cargoDescription;
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
