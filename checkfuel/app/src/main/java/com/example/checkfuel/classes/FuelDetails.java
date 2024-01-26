package com.example.checkfuel.classes;

public class FuelDetails {
    private String name;
    private String station;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private String key;

    public void setName(String name) {
        this.name = name;
    }

    public String getAvailability() {
        return availability;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    private String availability;
    private double price;

    public FuelDetails(String station,String name, double price,String availability) {
        this.station = station;
        this.name = name;
        this.price = price;
        this.availability = availability;
    }

    public FuelDetails(String key,String station,String name, double price,String availability) {
        this.key = key;
        this.station = station;
        this.name = name;
        this.price = price;
        this.availability = availability;
    }
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Price per liter: " + price + " Availability: "+availability;
    }
}
