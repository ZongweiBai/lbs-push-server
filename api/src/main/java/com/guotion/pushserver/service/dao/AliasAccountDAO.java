package com.guotion.pushserver.service.dao;

import com.guotion.pushserver.service.bean.Location;
import com.guotion.pushserver.service.entity.AliasAccount;
import com.guotion.pushserver.service.enums.DeviceType;

import java.util.List;

public interface AliasAccountDAO {

    AliasAccount findByAlias(String paramString);

    void addAliasAccount(AliasAccount paramAliasAccount);

    void updateLocation(String paramString, Location paramLocation);

    void updateClientAndDevice(String paramString1, String paramString2, DeviceType paramDeviceType);

    List<AliasAccount> getAround(String[] paramArrayOfString, Location paramLocation, double paramDouble);

    List<AliasAccount> getAround(Location paramLocation, double paramDouble);
}