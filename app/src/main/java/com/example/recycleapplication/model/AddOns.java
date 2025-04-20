package com.example.recycleapplication.model;

public enum AddOns {
    LETTUCE("Lettuce", 0.30),
    TOMATOES("Tomatoes", 0.30),
    ONIONS("Onions", 0.30),
    AVOCADO("Avocado", 0.50),
    CHEESE("Cheese", 1.00);

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
