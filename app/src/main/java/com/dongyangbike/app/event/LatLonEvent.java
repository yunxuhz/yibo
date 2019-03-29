/*
 * Project: KomectWalking
 * 
 * File Created at 2016-5-16
 * 
 * Copyright 2016 CMCC Corporation Limited.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * ZYHY Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license.
 */
package com.dongyangbike.app.event;


public class LatLonEvent extends BaseEventBus{

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    private double lat;
    private double lon;

    public LatLonEvent(String name, double lat, double lon) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }



}

