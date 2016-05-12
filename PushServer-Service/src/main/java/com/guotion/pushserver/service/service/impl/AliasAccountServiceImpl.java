package com.guotion.pushserver.service.service.impl;

import com.guotion.pushserver.service.bean.Location;
import com.guotion.pushserver.service.dao.AliasAccountDAO;
import com.guotion.pushserver.service.entity.AliasAccount;
import com.guotion.pushserver.service.entity.AppData;
import com.guotion.pushserver.service.service.AliasAccountService;
import com.guotion.pushserver.service.service.AppDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AliasAccountServiceImpl implements AliasAccountService {

    @Autowired
    private AliasAccountDAO aliasAccountDAO;

    @Autowired
    private AppDataService appDataService;

    public AliasAccount bindAlias(AliasAccount aliasAccount, String appId)
            throws Exception {
        AliasAccount oldAliasAccount = this.aliasAccountDAO.findByAlias(aliasAccount.getAlias());
        if (oldAliasAccount != null) {
            if ((!aliasAccount.getClientId().equals(oldAliasAccount.getClientId())) || (!aliasAccount.getBindDevice().equals(oldAliasAccount.getBindDevice())))
                this.aliasAccountDAO.updateClientAndDevice(aliasAccount.getClientId(), aliasAccount.getAlias(), aliasAccount.getBindDevice());
        } else {
            oldAliasAccount = new AliasAccount();
            AppData appData = this.appDataService.getAppData(appId);
            if (appData == null) {
                throw new Exception("can't find AppData from db with appId & providerType");
            }

            oldAliasAccount.setAppData(appData);
            oldAliasAccount.setAlias(aliasAccount.getAlias());
            oldAliasAccount.setClientId(aliasAccount.getClientId());
            oldAliasAccount.setBindDevice(aliasAccount.getBindDevice());
            this.aliasAccountDAO.addAliasAccount(oldAliasAccount);
        }

        return oldAliasAccount;
    }

    public AliasAccount getAliasAccount(String alias) {
        return this.aliasAccountDAO.findByAlias(alias);
    }

    public void updateLocation(String alias, Location location) {
        this.aliasAccountDAO.updateLocation(alias, location);
    }

    public List<AliasAccount> getAround(String[] aliasArray, Location location, double radius) {
        return this.aliasAccountDAO.getAround(aliasArray, location, radius);
    }

    public List<AliasAccount> getAround(Location location, double radius) {
        return this.aliasAccountDAO.getAround(location, radius);
    }
}