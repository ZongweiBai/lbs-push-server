package com.github.baymin.pushserver.repository

import com.github.baymin.pushserver.constant.DeviceType
import com.github.baymin.pushserver.entity.UserAccount
import com.github.baymin.pushserver.model.Location

/**
 * UserAccountDao
 * @author Zongwei* @date 2020/4/2 23:21
 */
interface UserAccountDAO {

    UserAccount findByAlias(String alias)

    void addAliasAccount(UserAccount aliasAccount)

    void updateLocation(String alias, Location location)

    void updateClientAndDevice(String alias, String clientId, DeviceType device)

    List<UserAccount> getAround(String[] aliasArray, Location location, double radius)

    List<UserAccount> getAround(Location location, double radius)

}
