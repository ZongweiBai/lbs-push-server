package com.guotion.pushserver.service.service;

import com.guotion.pushserver.service.entity.AppData;
import com.guotion.pushserver.service.enums.DBOperateResult;

public interface AppDataService {

    DBOperateResult addAppData(AppData paramAppData);

    AppData getAppData(String paramString);

}