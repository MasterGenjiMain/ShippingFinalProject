package com.my.deliverysystem.db.entity;

public class DeliveryType {
    private long id;
    private String typeName;

    public DeliveryType() {
    }

    public DeliveryType(long id, String typeName) {
        this.id = id;
        this.typeName = typeName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "DeliveryType{" +
                "id=" + id +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
