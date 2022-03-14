package com.my.deliverysystem.db.entity;

public class Tariff {
    private long id;
    private String tariffName;
    private double tariffPrice;
    private String tariffInfo;

    public Tariff() {
    }

    public Tariff(long id, String tariffName, double tariffPrice, String tariffInfo) {
        this.id = id;
        this.tariffName = tariffName;
        this.tariffPrice = tariffPrice;
        this.tariffInfo = tariffInfo;
    }

    public Tariff(long id, String tariffName, double tariffPrice) {
        this.id = id;
        this.tariffName = tariffName;
        this.tariffPrice = tariffPrice;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTariffName() {
        return tariffName;
    }

    public void setTariffName(String tariffName) {
        this.tariffName = tariffName;
    }

    public double getTariffPrice() {
        return tariffPrice;
    }

    public void setTariffPrice(double tariffPrice) {
        this.tariffPrice = tariffPrice;
    }

    public String getTariffInfo() {
        return tariffInfo;
    }

    public void setTariffInfo(String tariffInfo) {
        this.tariffInfo = tariffInfo;
    }

    @Override
    public String toString() {
        return "Tariff{" +
                "id=" + id +
                ", tariffName='" + tariffName + '\'' +
                ", tariffPrice=" + tariffPrice +
                ", tariffInfo='" + tariffInfo + '\'' +
                '}';
    }
}
