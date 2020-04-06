package com.github.baymin.pushserver.service.impl

import com.github.baymin.pushserver.entity.UserAccount
import com.github.baymin.pushserver.model.Location
import com.github.baymin.pushserver.repository.AppDataDAO
import com.github.baymin.pushserver.repository.UserAccountDAO
import com.github.baymin.pushserver.service.UserAccountService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * UserAccount业务处理实现类
 * @author Zongwei* @date 2020/4/2 23:08
 */
@Service
class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    private UserAccountDAO aliasAccountDAO

    @Autowired
    private AppDataDAO appDataDAO

    @Override
    UserAccount bindAlias(UserAccount aliasAccount, String appId) throws Exception {
        def aliasAccountInDB = aliasAccountDAO.findByAlias(aliasAccount.getAlias())
        if (aliasAccountInDB) {
            if (aliasAccount.getClientId() != aliasAccountInDB.getClientId() || aliasAccount.getBindDevice() != aliasAccountInDB.getBindDevice()) {
                aliasAccountDAO.updateClientAndDevice(aliasAccount.getClientId(), aliasAccount.getAlias(), aliasAccount.getBindDevice());
            }
        } else {
            def appData = appDataDAO.getAppData(appId);
            if (!appData) {
                throw new Exception("can't find AppData from db with appId & providerType");
            }

            aliasAccountInDB = new UserAccount("appData": appData, "alias": aliasAccount.getAlias(),
                    "clientId": aliasAccount.getClientId(), "bindDevice": aliasAccount.getBindDevice())
            aliasAccountDAO.addAliasAccount(aliasAccountInDB);
        }

        return aliasAccountInDB;
    }

    @Override
    UserAccount getAliasAccount(String alias) {
        aliasAccountDAO.findByAlias(alias)
    }

    @Override
    void updateLocation(String alias, Location location) {
        aliasAccountDAO.updateLocation(alias, location)
    }

    @Override
    List<UserAccount> getNearbyAccounts(String[] aliasArray, Location location, double radius) {
        aliasAccountDAO.getAround(aliasArray, location, radius);
    }

    @Override
    List<UserAccount> getNearbyAccounts(Location location, double radius) {
        aliasAccountDAO.getAround(location, radius)
    }
}
