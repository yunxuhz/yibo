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

