package com.my.deliverysystem.db.entity.bean;

public class LocationBean {
    private long id;
    private String locationName;
    private String cityName;
    private String activeStatus;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
    }

    @Override
    public String toString() {
        return "LocationBean{" +
                "id=" + id +
                ", locationName='" + locationName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", activeStatus='" + activeStatus + '\'' +
                '}';
    }
}
