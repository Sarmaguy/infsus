package com.example.demo.models;

public class Platform extends DatabaseModel{
    private String PlatformName;

    public String getPlatformName() {
        return PlatformName;
    }

    public void setPlatformName(String platformName) {
        this.PlatformName = platformName;
    }

    public Platform(){
        super();
    }
}
