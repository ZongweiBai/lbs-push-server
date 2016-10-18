package com.guotion.pushserver.service.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "t_app_data")
public class AppData {

    @Id
    private String id;
    private String appId;
    private String appKey;
    private String master;

    public AppData() {
    }

    public AppData(String master, String appKey, String appId) {
        this.master = master;
        this.appKey = appKey;
        this.appId = appId;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppKey() {
        return this.appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getMaster() {
        return this.master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public String toString() {
        return "{id:'" + this.id + '\'' + ", appId:'" + this.appId + '\'' + ", appKey:'" + this.appKey + '\'' + ", master:'" + this.master + '\'' + '}';
    }
}