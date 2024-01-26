package com.example.checkfuel;

import com.google.android.gms.maps.model.LatLng;

public class FillingStationInfo {
    private String username;
    private LatLng location;

    public FillingStationInfo(String username, LatLng location) {
        this.username = username;
        this.location = location;
    }

    public String getUsername() {
        return username;
    }

    public LatLng getLocation() {
        return location;
    }
}



