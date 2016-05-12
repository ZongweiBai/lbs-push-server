package com.guotion.pushserver.service.service.impl;

import com.guotion.pushserver.service.dao.OfflineMessageDAO;
import com.guotion.pushserver.service.entity.OfflineMessage;
import com.guotion.pushserver.service.service.OfflineMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfflineMessageServiceImpl implements OfflineMessageService {

    @Autowired
    private OfflineMessageDAO offlineMessageDAO;

    public void addOfflineMessage(OfflineMessage offlineMessage) {
        this.offlineMessageDAO.addOfflineMessage(offlineMessage);
    }

    public List<OfflineMessage> getOfflineMessage(String alias) {
        List list = this.offlineMessageDAO.getOfflineMessage(alias);
        return list;
    }

    public int deleteOfflineMessage(String alias) {
        return this.offlineMessageDAO.deleteOfflineMessage(alias);
    }
}