package com.my.deliverysystem.db.entity;

public class DeliveryType {
    private long id;
    private String typeName;
    private long languageId;

    public DeliveryType() {
    }

    public DeliveryType(String typeName, long languageId) {
        this.typeName = typeName;
        this.languageId = languageId;
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

    public long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(long languageId) {
        this.languageId = languageId;
    }

    @Override
    public String toString() {
        return "DeliveryType{" +
                "id=" + id +
                ", typeName='" + typeName + '\'' +
                ", languageId=" + languageId +
                '}';
    }
}
