package com.my.deliverysystem.db.entity;

public class Tariff {
    private long id;
    private String tariffName;
    private double tariffPrice;
    private String tariffInfo;
    private long languageId;

    public Tariff() {
    }

    public Tariff(String tariffName, double tariffPrice, String tariffInfo, long languageId) {
        this.tariffName = tariffName;
        this.tariffPrice = tariffPrice;
        this.tariffInfo = tariffInfo;
        this.languageId = languageId;
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

    public long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(long languageId) {
        this.languageId = languageId;
    }

    @Override
    public String toString() {
        return "Tariff{" +
                "id=" + id +
                ", tariffName='" + tariffName + '\'' +
                ", tariffPrice=" + tariffPrice +
                ", tariffInfo='" + tariffInfo + '\'' +
                ", languageId=" + languageId +
                '}';
    }
}
