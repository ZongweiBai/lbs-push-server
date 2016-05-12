package com.guotion.pushserver.service.dao;

import com.guotion.pushserver.service.bean.Location;
import com.guotion.pushserver.service.entity.AliasAccount;
import com.guotion.pushserver.service.enums.DeviceType;

import java.util.List;

public abstract interface AliasAccountDAO {
    public abstract AliasAccount findByAlias(String paramString);

    public abstract void addAliasAccount(AliasAccount paramAliasAccount);

    public abstract void updateLocation(String paramString, Location paramLocation);

    public abstract void updateClientAndDevice(String paramString1, String paramString2, DeviceType paramDeviceType);

    public abstract List<AliasAccount> getAround(String[] paramArrayOfString, Location paramLocation, double paramDouble);

    public abstract List<AliasAccount> getAround(Location paramLocation, double paramDouble);
}