package com.guotion.pushserver.service.dao;

import com.guotion.pushserver.service.entity.AppData;

public abstract interface AppDataDAO {
    public abstract AppData getAppData(String paramString);

    public abstract void add(AppData paramAppData);
}