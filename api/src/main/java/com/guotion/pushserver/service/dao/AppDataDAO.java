package com.guotion.pushserver.service.dao;

import com.guotion.pushserver.service.entity.AppData;

public interface AppDataDAO {

    AppData getAppData(String paramString);

    void add(AppData paramAppData);

}