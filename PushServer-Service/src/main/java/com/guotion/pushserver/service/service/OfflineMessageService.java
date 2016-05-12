package com.guotion.pushserver.service.service;

import com.guotion.pushserver.service.entity.OfflineMessage;

import java.util.List;

public abstract interface OfflineMessageService {
    public abstract void addOfflineMessage(OfflineMessage paramOfflineMessage);

    public abstract List<OfflineMessage> getOfflineMessage(String paramString);

    public abstract int deleteOfflineMessage(String paramString);
}