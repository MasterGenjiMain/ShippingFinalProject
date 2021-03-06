package com.my.deliverysystem.db.entity;

import java.sql.Timestamp;

public class DeliveryOrder {
    private long id;
    private long locationFromId;
    private long locationToId;
    private String cargoName;
    private String cargoDescription;
    private String address;
    private long deliveryTypeId;
    private double weight;
    private double volume;
    private Timestamp receivingDate;
    private long tariffId;

    public DeliveryOrder() {
    }

    public DeliveryOrder(long locationFromId, long locationToId, String cargoName, String cargoDescription,
                         String address, long deliveryTypeId, double weight, double volume, Timestamp receivingDate,
                         long tariffId) {
        this.locationFromId = locationFromId;
        this.locationToId = locationToId;
        this.cargoName = cargoName;
        this.cargoDescription = cargoDescription;
        this.address = address;
        this.deliveryTypeId = deliveryTypeId;
        this.weight = weight;
        this.volume = volume;
        this.receivingDate = receivingDate;
        this.tariffId = tariffId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getlocationfromid() {
        return locationFromId;
    }

    public void setLocationFromId(long locationFromId) {
        this.locationFromId = locationFromId;
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

    public Timestamp getReceivingDate() {
        return receivingDate;
    }

    public void setReceivingDate(Timestamp receivingDate) {
        this.receivingDate = receivingDate;
    }

    public long getTariffId() {
        return tariffId;
    }

    public void setTariffId(long tariffId) {
        this.tariffId = tariffId;
    }

    public long getLocationFromId() {
        return locationFromId;
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
                ", locationFromId=" + locationFromId +
                ", locationToId=" + locationToId +
                ", cargoName='" + cargoName + '\'' +
                ", cargoDescription='" + cargoDescription + '\'' +
                ", address='" + address + '\'' +
                ", deliveryTypeId=" + deliveryTypeId +
                ", weight=" + weight +
                ", volume=" + volume +
                ", receivingDate=" + receivingDate +
                ", tariffId=" + tariffId +
                '}';
    }
}
