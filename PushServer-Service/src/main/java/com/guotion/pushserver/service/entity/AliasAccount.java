package com.guotion.pushserver.service.entity;

import com.guotion.pushserver.service.bean.Location;
import com.guotion.pushserver.service.enums.DeviceType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "t_alias_account")
public class AliasAccount {
    private String id;
    private AppData appData;
    private String clientId;

    @Indexed(unique = true)
    private String alias;
    private DeviceType bindDevice;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2D)
    private Location location;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AppData getAppData() {
        return this.appData;
    }

    public void setAppData(AppData appData) {
        this.appData = appData;
    }

    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public DeviceType getBindDevice() {
        return this.bindDevice;
    }

    public void setBindDevice(DeviceType bindDevice) {
        this.bindDevice = bindDevice;
    }
}