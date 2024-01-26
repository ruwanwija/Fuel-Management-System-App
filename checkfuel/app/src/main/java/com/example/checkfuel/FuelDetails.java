package com.example.checkfuel;

public class FuelDetails {
    private String name;
    private double price;

    public FuelDetails(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Price per liter: " + price;
    }
}
