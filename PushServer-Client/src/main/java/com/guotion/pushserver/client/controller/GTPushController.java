package com.guotion.pushserver.client.controller;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.IQueryResult;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.guotion.pushserver.client.bean.LocationBean;
import com.guotion.pushserver.client.util.ResponseDataHandler;
import com.guotion.pushserver.service.bean.Location;
import com.guotion.pushserver.service.entity.AliasAccount;
import com.guotion.pushserver.service.entity.AppData;
import com.guotion.pushserver.service.enums.DeviceType;
import com.guotion.pushserver.service.service.AliasAccountService;
import com.guotion.pushserver.service.service.AppDataService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

@RequestMapping({"/GTPushController"})
@Controller
public class GTPushController {

    @Autowired
    private AliasAccountService aliasAccountService;

    @Autowired
    private AppDataService appDataService;
    private Logger logger = Logger.getLogger(GTPushController.class);

    @RequestMapping(value = {"/bindAlias"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public void bindAlias(String clientId, String alias, String appId, DeviceType device, HttpServletResponse response) {
        if ((alias == null) || (appId == null) || (clientId == null) || (device == null)) {
            ResponseDataHandler.dealBoolean(false, "alias can not be null", response);
            return;
        }
        try {
            AliasAccount aliasAccount = new AliasAccount();
            aliasAccount.setClientId(clientId);
            aliasAccount.setAlias(alias);
            aliasAccount.setBindDevice(device);

            AliasAccount savedAliasAccount = this.aliasAccountService.bindAlias(aliasAccount, appId);
            IGtPush push = new IGtPush("http://sdk.open.api.igexin.com/apiex.htm", savedAliasAccount.getAppData().getAppKey(), savedAliasAccount.getAppData().getMaster());
            push.connect();

            ResponseDataHandler.dealBoolean(true, null, response);
        } catch (Exception e) {
            e.printStackTrace();
            this.logger.error(getErrorMessage(e));
            ResponseDataHandler.dealBoolean(false, "server error", response);
        }
    }

    @RequestMapping(value = {"/pushTransmissionMessageToPerson"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public void pushTransmissionMessageToPerson(HttpServletRequest request, HttpServletResponse response) {
        String alias = request.getParameter("alias");
        String content = request.getParameter("content");
        if ((alias == null) || (content == null)) {
            ResponseDataHandler.dealBoolean(false, "参数不足", response);
            return;
        }
        AliasAccount aliasAccount = this.aliasAccountService.getAliasAccount(alias);
        try {
            if (aliasAccount.getBindDevice() == DeviceType.ANDROID)
                androidPushToPerson(aliasAccount, content);
            else {
                iosPushToPerson(aliasAccount, content);
            }
            ResponseDataHandler.dealBoolean(true, null, response);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseDataHandler.dealBoolean(false, "服务器异常", response);
        }
    }

    @RequestMapping(value = {"/pushTransmissionMessageToMultiPerson"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public void pushTransmissionMessageToMultiPerson(HttpServletRequest request, HttpServletResponse response) {
        String latitude = request.getParameter("latitude");
        String longitude = request.getParameter("longitude");
        String content = request.getParameter("content");
        String radius = request.getParameter("radius");
        String alias = request.getParameter("alias");
        String appId = request.getParameter("appId");
        if ((content == null) || (radius == null) || (appId == null) || (alias == null) || (latitude == null) || (longitude == null)) {
            System.out.println("参数不足");
            return;
        }

        AppData appData = this.appDataService.getAppData(appId);
        if (appData == null) {
            ResponseDataHandler.dealBoolean(false, "appId不正确", response);
            return;
        }
        System.out.println("alias.length=" + alias.split(";").length);
        List<AliasAccount> list = this.aliasAccountService.getAround(alias.split(";"), new Location(Double.parseDouble(longitude), Double.parseDouble(latitude)), Double.parseDouble(radius));
        System.out.println("fit alias.length=" + list.size());
        List androidList = new ArrayList();
        List iosList = new ArrayList();

        for (AliasAccount aliasAccount : list) {
            if (aliasAccount.getBindDevice() == DeviceType.ANDROID)
                androidList.add(aliasAccount);
            else {
                iosList.add(aliasAccount);
            }
        }

        List locationBeans = new ArrayList();
        try {
            locationBeans.addAll(androidPushToMultiPerson(list, appData, content));
            locationBeans.addAll(iosPushToMultiPerson(iosList, appData, content));
            ResponseDataHandler.dealList(locationBeans, null, response);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseDataHandler.dealBoolean(false, null, response);
        }
    }

    @RequestMapping(value = {"/updateLocation"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public void updateLocation(HttpServletRequest request, HttpServletResponse response) {
        String alias = request.getParameter("alias");
        String latitude = request.getParameter("latitude");
        String longitude = request.getParameter("longitude");
        if ((alias == null) || (latitude == null) || (longitude == null)) {
            ResponseDataHandler.dealBoolean(false, "参数不足", response);
            return;
        }
        try {
            this.aliasAccountService.updateLocation(alias, new Location(Double.parseDouble(longitude), Double.parseDouble(latitude)));
            ResponseDataHandler.dealBoolean(true, null, response);
        } catch (Exception e) {
            e.printStackTrace();
            this.logger.error(getErrorMessage(e));
            ResponseDataHandler.dealBoolean(false, "服务器异常", response);
        }
    }

    protected void androidPushToPerson(AliasAccount aliasAccount, String content) throws IOException {
        IGtPush push = new IGtPush("http://sdk.open.api.igexin.com/apiex.htm", aliasAccount.getAppData().getAppKey(), aliasAccount.getAppData().getMaster());
        push.connect();
        TransmissionTemplate template = new TransmissionTemplate();
        template.setTransmissionType(2);
        template.setAppId(aliasAccount.getAppData().getAppId());
        template.setTransmissionContent(content);
        template.setAppkey(aliasAccount.getAppData().getAppKey());

        IQueryResult queryResult = push.getClientIdStatus(aliasAccount.getAppData().getAppId(), aliasAccount.getClientId());
        System.out.println(queryResult.getResponse().toString());
        SingleMessage singleMessage = new SingleMessage();
        singleMessage.setOffline(true);
        singleMessage.setOfflineExpireTime(86400000L);

        singleMessage.setData(template);

        Target target = new Target();
        target.setClientId(aliasAccount.getClientId());
        target.setAppId(aliasAccount.getAppData().getAppId());

        IPushResult result = push.pushMessageToSingle(singleMessage, target);
        System.out.println(result.getResponse().toString());
    }

    protected void iosPushToPerson(AliasAccount aliasAccount, String content) throws IOException {
        IGtPush push = new IGtPush("http://sdk.open.api.igexin.com/apiex.htm", aliasAccount.getAppData().getAppKey(), aliasAccount.getAppData().getMaster());
        push.connect();

        TransmissionTemplate transmissionTemplate = new TransmissionTemplate();
        transmissionTemplate.setAppId(aliasAccount.getAppData().getAppId());
        transmissionTemplate.setAppkey(aliasAccount.getAppData().getAppKey());
        transmissionTemplate.setTransmissionContent(content);
        transmissionTemplate.setTransmissionType(2);

        APNPayload apnPayload = new APNPayload();
        apnPayload.setBadge(1);
        apnPayload.setContentAvailable(1);
        apnPayload.setSound("default");

        transmissionTemplate.setAPNInfo(apnPayload);

        SingleMessage singleMessage = new SingleMessage();
        singleMessage.setOffline(true);
        singleMessage.setOfflineExpireTime(86400000L);
        singleMessage.setData(transmissionTemplate);

        Target target = new Target();
        target.setAppId(aliasAccount.getAppData().getAppId());
        target.setClientId(aliasAccount.getClientId());

        IPushResult result = push.pushMessageToSingle(singleMessage, target);
        System.out.println(result.getResponse().toString());
    }

    protected List<LocationBean> androidPushToMultiPerson(List<AliasAccount> aliasAccounts, AppData appData, String content) throws IOException {
        System.setProperty("gexin.rp.sdk.pushlist.needDetails", "true");
        IGtPush push = new IGtPush("http://sdk.open.api.igexin.com/apiex.htm", appData.getAppKey(), appData.getMaster());
        push.connect();
        TransmissionTemplate template = new TransmissionTemplate();
        template.setTransmissionType(2);
        template.setAppId(appData.getAppId());
        template.setTransmissionContent(content);
        template.setAppkey(appData.getAppKey());
        ListMessage listMessage = new ListMessage();
        listMessage.setData(template);
        listMessage.setOffline(true);
        listMessage.setOfflineExpireTime(86400000L);

        List targets = new ArrayList();

        if (aliasAccounts.size() > 0) {
            for (AliasAccount aliasAccount : aliasAccounts) {
                Target target = new Target();
                target.setAppId(appData.getAppId());
                target.setClientId(aliasAccount.getClientId());
                targets.add(target);
            }
            String taskId = push.getContentId(listMessage);
            IPushResult result = push.pushMessageToList(taskId, targets);
            List locationList = getNotifyClientLocation(result.getResponse().get("details").toString(), aliasAccounts);
            return locationList;
        }
        return new ArrayList();
    }

    protected List<LocationBean> iosPushToMultiPerson(List<AliasAccount> aliasAccounts, AppData appData, String content) throws IOException {
        IGtPush push = new IGtPush("http://sdk.open.api.igexin.com/apiex.htm", appData.getAppKey(), appData.getMaster());
        push.connect();

        TransmissionTemplate transmissionTemplate = new TransmissionTemplate();
        transmissionTemplate.setAppId(appData.getAppId());
        transmissionTemplate.setAppkey(appData.getAppKey());
        transmissionTemplate.setTransmissionContent(content);
        transmissionTemplate.setTransmissionType(2);

        APNPayload apnPayload = new APNPayload();
        apnPayload.setBadge(1);
        apnPayload.setContentAvailable(1);
        apnPayload.setSound("default");

        transmissionTemplate.setAPNInfo(apnPayload);

        ListMessage listMessage = new ListMessage();
        listMessage.setData(transmissionTemplate);
        listMessage.setOffline(true);
        listMessage.setOfflineExpireTime(86400000L);

        List targets = new ArrayList();
        if (aliasAccounts.size() > 0) {
            for (AliasAccount aliasAccount : aliasAccounts) {
                Target target = new Target();
                target.setAppId(appData.getAppId());
                target.setClientId(aliasAccount.getClientId());
                targets.add(target);
            }
            String taskId = push.getContentId(listMessage);
            IPushResult result = push.pushMessageToList(taskId, targets);
            List locationList = getNotifyClientLocation(result.getResponse().get("details").toString(), aliasAccounts);
            return locationList;
        }
        return new ArrayList();
    }

    protected String getErrorMessage(Exception e) {
        Writer writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        e.printStackTrace(pw);
        pw.close();
        return writer.toString();
    }

    public List<LocationBean> getNotifyClientLocation(String detail, List<AliasAccount> list) {
        detail = detail.substring(1, detail.length() - 1);
        List locationList = new ArrayList();
        String[] clientIdWithResultArray = detail.split(",");
        String clientId;
        String result;
        for (String clientIdWithResult : clientIdWithResultArray) {
            clientId = clientIdWithResult.split("=")[0];
            result = clientIdWithResult.split("=")[1];

            for (AliasAccount aliasAccount : list) {
                if (clientId.trim().equals(aliasAccount.getClientId())) {
                    System.out.println("clientId=" + clientId + ",result=" + result + "," + aliasAccount.getAlias());
                    locationList.add(new LocationBean(aliasAccount.getLocation(), aliasAccount.getAlias()));
                    continue;
                }
            }
        }
        return locationList;
    }
}