package com.guotion.pushserver.client.controller;

import com.guotion.pushserver.client.util.ResponseDataHandler;
import com.guotion.pushserver.service.entity.AppData;
import com.guotion.pushserver.service.service.AppDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping({"/appDataController"})
public class AppDataController {

    @Autowired
    private AppDataService appDataService;

    @RequestMapping(value = {"/addAppData"}, method = {RequestMethod.POST})
    @ResponseBody
    public void addAppData(HttpServletRequest request, HttpServletResponse response) {
        String appId = request.getParameter("appId");
        String appKey = request.getParameter("appKey");
        String master = request.getParameter("master");

        AppData appData = new AppData(master, appKey, appId);
        try {
            this.appDataService.addAppData(appData);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseDataHandler.dealBoolean(false, "server error", response);
            return;
        }
        ResponseDataHandler.dealObject(appData, null, response);
    }
}