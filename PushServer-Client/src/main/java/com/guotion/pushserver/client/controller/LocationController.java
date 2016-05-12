package com.guotion.pushserver.client.controller;

import com.guotion.pushserver.client.bean.LocationBean;
import com.guotion.pushserver.client.util.ResponseDataHandler;
import com.guotion.pushserver.service.bean.Location;
import com.guotion.pushserver.service.entity.AliasAccount;
import com.guotion.pushserver.service.service.AliasAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping({"/location"})
public class LocationController {

    @Autowired
    private AliasAccountService aliasAccountService;

    @RequestMapping(value = {"/getAroundPoints"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    @ResponseBody
    public void getAroundPoints(Location location, Double radius, HttpServletResponse response) {
        List<AliasAccount> list = this.aliasAccountService.getAround(location, radius.doubleValue());
        List locations = new LinkedList();
        for (AliasAccount aliasAccount : list) {
            locations.add(new LocationBean(aliasAccount.getLocation(), aliasAccount.getAlias()));
        }
        ResponseDataHandler.dealList(locations, null, response);
    }
}