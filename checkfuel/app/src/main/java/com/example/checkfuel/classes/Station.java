package com.example.checkfuel.classes;

public class Station {

    private String station,location,phone,email,permit,key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPermit() {
        return permit;
    }

    public void setPermit(String permit) {
        this.permit = permit;
    }

    public Station(String key,String station,String location,String phone,String email,String permit) {
        this.key = key;
        this.station = station;
        this.location = location;
        this.phone = phone;
        this.email = email;
        this.permit = permit;
    }
}
