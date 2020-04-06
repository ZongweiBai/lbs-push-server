package com.github.baymin.pushserver.endpoint

import com.gexin.rp.sdk.http.IGtPush
import com.github.baymin.pushserver.constant.AppConstant
import com.github.baymin.pushserver.entity.UserAccount
import com.github.baymin.pushserver.model.UserAccountVO
import com.github.baymin.pushserver.model.LocationInfo
import com.github.baymin.pushserver.model.PushMultiVO
import com.github.baymin.pushserver.model.PushOneVO
import com.github.baymin.pushserver.service.UserAccountService
import com.github.baymin.pushserver.service.AppDataService
import com.github.baymin.pushserver.service.MessagePushService
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 消息推送接口
 * @author Zongwei* @date 2020/4/5 21:36
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/push")
class MessagePushEndpoint {

    @Autowired
    private UserAccountService aliasAccountService

    @Autowired
    private AppDataService appDataService

    @Autowired
    private MessagePushService messagePushService

    @PostMapping("/device/bind")
    void bindAlias(@RequestBody UserAccountVO accountVO) {
        def aliasAccount = new UserAccount("clientId": accountVO.getClientId(),
                "alias": accountVO.getAlias(), "bindDevice": accountVO.getBindDevice())

        def aliasAccountInDB = aliasAccountService.bindAlias(aliasAccount, accountVO.getAppId())
        def push = new IGtPush(AppConstant.GT_PUSH_URL, aliasAccountInDB.getAppData().getAppKey(), aliasAccountInDB.getAppData().getMaster())
        if (!push.connect()) {
            log.error("注册到个推失败")
        }
    }

    @PostMapping("/message/one")
    void pushTransmissionMessageToOne(@RequestBody PushOneVO pushOneVO) {
        if (!pushOneVO || !pushOneVO.getAlias() || !pushOneVO.getContent()) {
            log.error("参数不足")
            return
        }
        def aliasAccount = this.aliasAccountService.getAliasAccount(pushOneVO.getAlias())
        messagePushService.pushMessageToOne(aliasAccount, pushOneVO.getContent())
    }

    @PostMapping("/message/multi")
    List<LocationInfo> pushTransmissionMessageToMulti(@RequestBody PushMultiVO pushMultiVO) {
        if (!pushMultiVO || !pushMultiVO.getContent() || !pushMultiVO.getRadius()
                || !pushMultiVO.getAppId() || !pushMultiVO.getAliases()
                || !pushMultiVO.getLocation() || !pushMultiVO.getLocation().getLatitude() || !pushMultiVO.getLocation().getLongitude()) {
            log.error("参数不足")
            return
        }

        def appData = appDataService.getAppData(pushMultiVO.getAppId());
        if (!appData) {
            log.error("appId不正确")
            return;
        }
        def aliasAccounts = aliasAccountService.getNearbyAccounts(pushMultiVO.getAliases(), pushMultiVO.getLocation(), pushMultiVO.getRadius())

        messagePushService.pushMessageToMulti(aliasAccounts, appData, pushMultiVO.getContent())
    }

}
