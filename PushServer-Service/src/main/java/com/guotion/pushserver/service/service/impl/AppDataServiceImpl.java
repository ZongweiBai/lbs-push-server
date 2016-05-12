package com.guotion.pushserver.service.service.impl;

import com.guotion.pushserver.service.dao.AppDataDAO;
import com.guotion.pushserver.service.entity.AppData;
import com.guotion.pushserver.service.enums.DBOperateResult;
import com.guotion.pushserver.service.service.AppDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AppDataServiceImpl implements AppDataService {

    @Autowired
    private AppDataDAO appDataDAO;

    public DBOperateResult addAppData(AppData appData) {
        if (getAppData(appData.getAppId()) == null) {
            this.appDataDAO.add(appData);
            return DBOperateResult.OBJECT_ADD_SUCCESSFUL;
        }
        return DBOperateResult.OBJECT_ALREADY_EXIST;
    }

    public AppData getAppData(String appId) {
        return this.appDataDAO.getAppData(appId);
    }
}