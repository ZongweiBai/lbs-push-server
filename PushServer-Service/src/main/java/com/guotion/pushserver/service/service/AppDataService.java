package com.guotion.pushserver.service.service;

import com.guotion.pushserver.service.entity.AppData;
import com.guotion.pushserver.service.enums.DBOperateResult;

public abstract interface AppDataService {
    public abstract DBOperateResult addAppData(AppData paramAppData);

    public abstract AppData getAppData(String paramString);
}