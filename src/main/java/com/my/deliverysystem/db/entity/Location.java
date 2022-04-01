package com.my.deliverysystem.db.entity;

public class Location {
    private long id;
    private String locationName;
    private long cityId;
    private int activeStatusId;

    public Location() {
    }

    public Location(String locationName, long cityId, int activeStatusId) {
        this.locationName = locationName;
        this.cityId = cityId;
        this.activeStatusId = activeStatusId;
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

    public int getActiveStatusId() {
        return activeStatusId;
    }

    public void setActiveStatusId(int activeStatusId) {
        this.activeStatusId = activeStatusId;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", locationName='" + locationName + '\'' +
                ", cityId=" + cityId +
                ", isActive=" + activeStatusId +
                '}';
    }
}
