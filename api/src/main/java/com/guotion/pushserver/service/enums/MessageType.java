package com.guotion.pushserver.service.enums;

public enum MessageType {

    TRANSMISSION_TEMPLATE("com.gexin.rp.sdk.template.TransmissionTemplate", 0),
    NOTIFICATION_TEMPLATE("com.gexin.rp.sdk.template.NotificationTemplate", 1),
    LINK_TEMPLATE("com.gexin.rp.sdk.template.LinkTemplate", 2),
    NOTYPOPLOAD_TEMPLATE("com.gexin.rp.sdk.template.NotyPopLoadTemplate", 3);

    private String name;
    private int index;

    private MessageType(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}