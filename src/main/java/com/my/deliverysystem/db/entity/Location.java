package com.my.deliverysystem.db.entity;

public class Location {
    private long id;
    private String locationName;
    private long cityId;
    private int isActive;

    public Location() {
    }

    public Location(long id, String locationName, long cityId, int isActive) {
        this.id = id;
        this.locationName = locationName;
        this.cityId = cityId;
        this.isActive = isActive;
    }

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

    public long getCityId() {
        return cityId;
    }

    public void setCityId(long cityId) {
        this.cityId = cityId;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", locationName='" + locationName + '\'' +
                ", cityId=" + cityId +
                ", isActive=" + isActive +
                '}';
    }
}
