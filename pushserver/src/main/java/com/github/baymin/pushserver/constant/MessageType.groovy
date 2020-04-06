package com.github.baymin.pushserver.constant

/**
 * 消息类型
 * @author Zongwei* @date 2020/4/2 22:48
 */
enum MessageType {

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

}