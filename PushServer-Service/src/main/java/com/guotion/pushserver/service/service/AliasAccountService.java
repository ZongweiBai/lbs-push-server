package com.guotion.pushserver.service.service;

import com.guotion.pushserver.service.bean.Location;
import com.guotion.pushserver.service.entity.AliasAccount;

import java.util.List;

public abstract interface AliasAccountService {
    public abstract AliasAccount bindAlias(AliasAccount paramAliasAccount, String paramString)
            throws Exception;

    public abstract AliasAccount getAliasAccount(String paramString);

    public abstract void updateLocation(String paramString, Location paramLocation);

    public abstract List<AliasAccount> getAround(String[] paramArrayOfString, Location paramLocation, double paramDouble);

    public abstract List<AliasAccount> getAround(Location paramLocation, double paramDouble);
}