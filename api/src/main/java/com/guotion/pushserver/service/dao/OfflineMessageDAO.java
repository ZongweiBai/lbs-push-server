package com.guotion.pushserver.service.dao;

import com.guotion.pushserver.service.entity.OfflineMessage;

import java.util.List;

public interface OfflineMessageDAO {

    void addOfflineMessage(OfflineMessage paramOfflineMessage);

    List<OfflineMessage> getOfflineMessage(String paramString);

    int deleteOfflineMessage(String paramString);

}