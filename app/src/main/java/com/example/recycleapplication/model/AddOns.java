package com.example.recycleapplication.model;

public enum AddOns {
    LETTUCE("Lettuce", 0.25),
    TOMATOES("Tomatoes", 0.25),
    ONIONS("Onions", 0.25),
    AVOCADO("Avocado", 1.50),
    CHEESE("Cheese", 0.75);

    private final String name;
    private final double price;

    AddOns(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name;
    }
}
