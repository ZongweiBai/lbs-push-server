package com.github.baymin.pushserver.constant

/**
 * 设备类型
 * @author Zongwei* @date 2020/4/2 22:46
 */
enum DeviceType {

    ANDROID("ANDROID"),
    IOS("IOS");

    private String type;

    DeviceType(String type) {
        this.type = type;
    }

    String getType() {
        return type
    }
}