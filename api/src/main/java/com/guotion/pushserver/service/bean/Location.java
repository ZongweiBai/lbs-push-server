package com.guotion.pushserver.service.bean;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "t_location")
public class Location {

    private double longitude;
    private double latitude;

    public Location() {
    }

    public Location(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String toString() {
        return "{longitude:" + this.longitude + ", latitude:" + this.latitude + '}';
    }
}