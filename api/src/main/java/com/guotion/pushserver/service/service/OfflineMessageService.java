package com.guotion.pushserver.service.service;

import com.guotion.pushserver.service.entity.OfflineMessage;

import java.util.List;

public interface OfflineMessageService {

    void addOfflineMessage(OfflineMessage paramOfflineMessage);

    List<OfflineMessage> getOfflineMessage(String paramString);

    int deleteOfflineMessage(String paramString);

}