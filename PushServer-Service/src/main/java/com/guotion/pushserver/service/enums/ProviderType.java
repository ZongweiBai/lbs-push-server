package com.guotion.pushserver.service.enums;

public enum ProviderType {
    GE_TUI("个推", 0);

    private int index;
    private String name;

    private ProviderType(String name, int index) {
        this.index = index;
        this.name = name;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}