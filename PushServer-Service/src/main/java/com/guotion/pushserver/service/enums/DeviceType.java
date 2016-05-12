package com.guotion.pushserver.service.enums;

public enum DeviceType {
    ANDROID("ANDROID"),
    IOS("IOS");

    private String name;

    private DeviceType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}