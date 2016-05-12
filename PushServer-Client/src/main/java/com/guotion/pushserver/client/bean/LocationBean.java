package com.guotion.pushserver.client.bean;

import com.guotion.pushserver.service.bean.Location;

/**
 * Created by baymin on 2016/5/11.
 */
public class LocationBean {

    private Location location;
    private String account;

    public LocationBean() {
    }

    public LocationBean(Location location, String account) {
        this.location = location;
        this.account = account;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
