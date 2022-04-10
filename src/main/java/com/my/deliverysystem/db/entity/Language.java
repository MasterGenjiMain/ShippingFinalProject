package com.my.deliverysystem.db.entity;

public class Language {
    private long id;
    private String languageName;

    public Language() {
    }

    public Language(String languageName) {
        this.languageName = languageName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    @Override
    public String toString() {
        return "Language{" +
                "id=" + id +
                ", languageName='" + languageName + '\'' +
                '}';
    }
}
