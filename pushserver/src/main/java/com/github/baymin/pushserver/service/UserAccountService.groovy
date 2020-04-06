package com.github.baymin.pushserver.service

import com.github.baymin.pushserver.entity.UserAccount
import com.github.baymin.pushserver.model.Location

/**
 * UserAccount业务接口
 * @author Zongwei* @date 2020/4/2 23:07
 */
interface UserAccountService {

    UserAccount bindAlias(UserAccount aliasAccount, String appId) throws Exception

    UserAccount getAliasAccount(String alias)

    void updateLocation(String alias, Location location)

    List<UserAccount> getNearbyAccounts(String[] aliasArr, Location location, double radius)

    List<UserAccount> getNearbyAccounts(Location location, double radius)

}