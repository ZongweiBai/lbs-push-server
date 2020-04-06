package com.github.baymin.pushserver.service

import com.github.baymin.pushserver.entity.UserAccount
import com.github.baymin.pushserver.entity.AppData
import com.github.baymin.pushserver.model.LocationInfo

/**
 * 消息推送业务接口
 * @author Zongwei* @date 2020/4/5 21:56
 */
interface MessagePushService {

    void pushMessageToOne(UserAccount aliasAccount, String content)

    List<LocationInfo> pushMessageToMulti(List<UserAccount> aliasAccounts, AppData appData, String content)
}