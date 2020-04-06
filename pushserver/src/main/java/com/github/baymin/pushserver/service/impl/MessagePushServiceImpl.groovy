package com.github.baymin.pushserver.service.impl


import com.gexin.rp.sdk.base.impl.ListMessage
import com.gexin.rp.sdk.base.impl.SingleMessage
import com.gexin.rp.sdk.base.impl.Target
import com.gexin.rp.sdk.base.payload.APNPayload
import com.gexin.rp.sdk.http.IGtPush
import com.gexin.rp.sdk.template.TransmissionTemplate
import com.github.baymin.pushserver.constant.AppConstant
import com.github.baymin.pushserver.constant.DeviceType
import com.github.baymin.pushserver.entity.UserAccount
import com.github.baymin.pushserver.entity.AppData
import com.github.baymin.pushserver.model.LocationInfo
import com.github.baymin.pushserver.service.MessagePushService
import groovy.util.logging.Slf4j
import org.springframework.stereotype.Service

import java.util.stream.Collectors

/**
 * TODO 这里描述class的作用
 * @author Zongwei* @date 2020/4/5 21:58
 */
@Slf4j
@Service
class MessagePushServiceImpl implements MessagePushService {

    @Override
    void pushMessageToOne(UserAccount aliasAccount, String content) {
        if (aliasAccount.getBindDevice() == DeviceType.ANDROID) {
            androidPushToPerson(aliasAccount, content)
        } else {
            iosPushToPerson(aliasAccount, content)
        }
    }

    @Override
    List<LocationInfo> pushMessageToMulti(List<UserAccount> aliasAccounts, AppData appData, String content) {
        def androidList = aliasAccounts.stream().filter({ account -> account.getBindDevice() == DeviceType.ANDROID }).collect(Collectors.toList())
        def iosList = aliasAccounts.stream().filter({ account -> account.getBindDevice() == DeviceType.IOS }).collect(Collectors.toList())

        aliasAccounts.each { UserAccount aliasAccount ->
            if (aliasAccount.getBindDevice() == DeviceType.ANDROID) {
                androidList.add(aliasAccount)
            } else {
                iosList.add(aliasAccount)
            }
        }

        def LocationInfoList = []
        try {
            LocationInfoList.addAll(androidPushToMultiPerson(androidList, appData, content));
            LocationInfoList.addAll(iosPushToMultiPerson(iosList, appData, content));
        } catch (Exception e) {
            log.error("推送消息失败", e)
        }
        return LocationInfoList
    }

    /**
     * Android推送到指定人
     */
    private static void androidPushToPerson(UserAccount aliasAccount, String content) {
        def push = new IGtPush(AppConstant.GT_PUSH_URL, aliasAccount.getAppData().getAppKey(), aliasAccount.getAppData().getMaster());
        push.connect();

        def template = new TransmissionTemplate("transmissionType": 2, "appId": aliasAccount.getAppData().getAppId(),
                "transmissionContent": content, "appkey": aliasAccount.getAppData().getAppKey())
        def singleMessage = new SingleMessage("offline": true, "offlineExpireTime": 86400000L, "data": template)
        def target = new Target("clientId": aliasAccount.getClientId(), "appId": aliasAccount.getAppData().getAppId())

        def result = push.pushMessageToSingle(singleMessage, target);
        System.out.println(result.getResponse().toString());
    }

    /**
     * IOS推送到指定人
     */
    private static void iosPushToPerson(UserAccount aliasAccount, String content) {
        def push = new IGtPush(AppConstant.GT_PUSH_URL, aliasAccount.getAppData().getAppKey(), aliasAccount.getAppData().getMaster());
        push.connect()

        def apnPayload = new APNPayload("badge": 1, "contentAvailable": 1, "sound": "default")
        def transmissionTemplate = new TransmissionTemplate("appId": aliasAccount.getAppData().getAppId(), "appkey": aliasAccount.getAppData().getAppKey(),
                "transmissionContent": content, "transmissionType": 2)
        transmissionTemplate.setAPNInfo(apnPayload)

        def singleMessage = new SingleMessage("offline": true, "offlineExpireTime": 86400000L, "data": transmissionTemplate)

        def target = new Target("appId": aliasAccount.getAppData().getAppId(), "clientId": aliasAccount.getClientId())

        def result = push.pushMessageToSingle(singleMessage, target);
        System.out.println(result.getResponse().toString());
    }

    /**
     * Android推送到多个人
     */
    private static List<LocationInfo> androidPushToMultiPerson(List<UserAccount> aliasAccounts, AppData appData, String content) throws IOException {
        System.setProperty("gexin.rp.sdk.pushlist.needDetails", "true")
        def push = new IGtPush(AppConstant.GT_PUSH_URL, appData.getAppKey(), appData.getMaster())
        push.connect()

        def template = new TransmissionTemplate("transmissionType": 2, "appId": appData.getAppId(), "transmissionContent": content, "appkey": appData.getAppKey())
        def listMessage = new ListMessage("data": template, "offline": true, "offlineExpireTime": 86400000L)

        def pushTargets = []
        if (aliasAccounts.size() > 0) {
            aliasAccounts.each { UserAccount aliasAccount ->
                Target target = new Target("appId": appData.getAppId(), "clientId": aliasAccount.getClientId())
                pushTargets.add(target)
            }
            def taskId = push.getContentId(listMessage)
            def result = push.pushMessageToList(taskId, pushTargets);
            getNotifyClientLocation(result.getResponse().get("details").toString(), aliasAccounts);
        }
        return []
    }

    /**
     * IOS推送到多个人
     */
    private static List<LocationInfo> iosPushToMultiPerson(List<UserAccount> aliasAccounts, AppData appData, String content) throws IOException {
        def push = new IGtPush(AppConstant.GT_PUSH_URL, appData.getAppKey(), appData.getMaster())
        push.connect()

        def apnPayload = new APNPayload("badge": 1, "contentAvailable": 1, "sound": "default")
        def transmissionTemplate = new TransmissionTemplate("appId": appData.getAppId(), "appkey": appData.getAppKey(),
                "transmissionContent": content, "transmissionType": 2)
        transmissionTemplate.setAPNInfo(apnPayload)
        def listMessage = new ListMessage("data": transmissionTemplate, "offline": true, "offlineExpireTime": 86400000L)

        def pushTargets = []
        if (aliasAccounts.size() > 0) {
            aliasAccounts.each { UserAccount aliasAccount ->
                def target = new Target("appId": appData.getAppId(), "clientId": aliasAccount.getClientId())
                pushTargets.add(target)
            }
            def taskId = push.getContentId(listMessage)
            def result = push.pushMessageToList(taskId, pushTargets)
            getNotifyClientLocation(result.getResponse().get("details").toString(), aliasAccounts);
        }
        return []
    }

    static List<LocationInfo> getNotifyClientLocation(String detail, List<UserAccount> list) {
        detail = detail.substring(1, detail.length() - 1)
        def clientIdWithResultArray = detail.split(",")
        def locationList = []
        clientIdWithResultArray.each { String clientIdWithResult ->
            def clientId = clientIdWithResult.split("=")[0]
            list.each { UserAccount aliasAccount ->
                if (clientId.trim() == aliasAccount.getClientId()) {
                    locationList.add(new LocationInfo("location": aliasAccount.getLocation(), "alias": aliasAccount.getAlias()))
                }
            }
        }
        return locationList
    }
}
