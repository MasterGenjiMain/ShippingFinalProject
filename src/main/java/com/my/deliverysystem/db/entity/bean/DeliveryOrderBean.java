package com.my.deliverysystem.db.entity.bean;

import java.sql.Timestamp;

public class DeliveryOrderBean {

    private long id;
    private String locationFrom;
    private String locationTo;
    private String cargoName;
    private String cargoDescription;
    private String address;
    private String deliveryType;
    private double weight;
    private double volume;
    private Timestamp receivingDate;
    private String tariffName;

    public DeliveryOrderBean() {
    }

    public DeliveryOrderBean(long id, String locationFrom, String locationTo,
                             String cargoName, String cargoDescription,
                             String address, String deliveryType,
                             double weight, double volume,
                             Timestamp receivingDate, String tariffName) {
        this.id = id;
        this.locationFrom = locationFrom;
        this.locationTo = locationTo;
        this.cargoName = cargoName;
        this.cargoDescription = cargoDescription;
        this.address = address;
        this.deliveryType = deliveryType;
        this.weight = weight;
        this.volume = volume;
        this.receivingDate = receivingDate;
        this.tariffName = tariffName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLocationFrom() {
        return locationFrom;
    }

    public void setLocationFrom(String locationFrom) {
        this.locationFrom = locationFrom;
    }

    public String getLocationTo() {
        return locationTo;
    }

    public void setLocationTo(String locationTo) {
        this.locationTo = locationTo;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
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

    public String getTariffName() {
        return tariffName;
    }

    public void setTariffName(String tariffName) {
        this.tariffName = tariffName;
    }

    @Override
    public String toString() {
        return "DeliveryOrderBean{" +
                "id=" + id +
                ", locationFrom='" + locationFrom + '\'' +
                ", locationTo='" + locationTo + '\'' +
                ", cargoName='" + cargoName + '\'' +
                ", cargoDescription='" + cargoDescription + '\'' +
                ", address='" + address + '\'' +
                ", deliveryType='" + deliveryType + '\'' +
                ", weight=" + weight +
                ", volume=" + volume +
                ", receivingDate=" + receivingDate +
                ", tariff='" + tariffName + '\'' +
                '}';
    }
}
