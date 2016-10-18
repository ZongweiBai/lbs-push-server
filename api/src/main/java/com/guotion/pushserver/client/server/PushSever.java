package com.guotion.pushserver.client.server;

import com.guotion.pushserver.service.entity.AppData;

public abstract interface PushSever {

    public abstract void sendMessage(AppData paramAppData);
}