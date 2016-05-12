package com.guotion.pushserver.service.dao;

import com.guotion.pushserver.service.entity.OfflineMessage;

import java.util.List;

public abstract interface OfflineMessageDAO {
    public abstract void addOfflineMessage(OfflineMessage paramOfflineMessage);

    public abstract List<OfflineMessage> getOfflineMessage(String paramString);

    public abstract int deleteOfflineMessage(String paramString);
}