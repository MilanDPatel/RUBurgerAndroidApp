package com.example.recycleapplication.model;

/**
 Enum representing types of add ons and each type is associated with a price.
 @author Milan Patel
 */
public enum AddOns {
    LETTUCE("Lettuce", 0.30),
    TOMATOES("Tomatoes", 0.30),
    ONIONS("Onions", 0.30),
    AVOCADO("Avocado", 0.50),
    CHEESE("Cheese", 1.00);

    private final String name;
    private final double price;

    /**
     * Parameterized constructor with 2 parameters.
     * @param name the name associated with the addon.
     * @param price the price associated with the addon.
     */
    AddOns(String name, double price) {
        this.name = name;
        this.price = price;
    }

    /**
     * Returns the price associated with the addon.
     * @return the price of the addon as a double
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns a textual representation of the addons.
     * @return a legible string of the addons as a String.
     */
    @Override
    public String toString() {
        return name;
    }
}
