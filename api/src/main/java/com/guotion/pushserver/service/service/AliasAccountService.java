package com.guotion.pushserver.service.service;

import com.guotion.pushserver.service.bean.Location;
import com.guotion.pushserver.service.entity.AliasAccount;

import java.util.List;

public interface AliasAccountService {

    AliasAccount bindAlias(AliasAccount paramAliasAccount, String paramString) throws Exception;

    AliasAccount getAliasAccount(String paramString);

    void updateLocation(String paramString, Location paramLocation);

    List<AliasAccount> getAround(String[] paramArrayOfString, Location paramLocation, double paramDouble);

    List<AliasAccount> getAround(Location paramLocation, double paramDouble);

}