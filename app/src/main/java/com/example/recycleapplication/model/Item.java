package com.example.recycleapplication.model;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * Item class representing a general purchasable item.
 * Provides basic properties like name, price, quantity, and image resource ID.
 * Implements Serializable to allow object serialization.
 * Author: Milan Patel
 */
public class Item implements Serializable {
    private String itemName;
    private int quantity;
    private double price;
    private int imageResourceId;

    /**
     * Default constructor.
     * Initializes quantity to 1 by default.
     */
    public Item() {
        this.quantity = 1;
    }

    /**
     * Constructor with parameters for item name and price.
     * Initializes quantity to 1 by default.
     * @param itemName the name of the item
     * @param price the price of a single unit of the item
     */
    public Item(String itemName, double price) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = 1;
    }

    /**
     * Gets the name of the item.
     * @return the item name
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Sets the name of the item.
     * @param itemName the name to set
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * Gets the quantity of the item.
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the item.
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Calculates and gets the total price based on quantity and unit price.
     * @return the total price
     */
    public double getPrice() {
        return quantity * price;
    }

    /**
     * Sets the unit price of the item.
     * @param price the price per unit
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Sets the image resource ID for the item (useful for UI display).
     * @param imageResourceId the image resource ID to set
     */
    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    /**
     * Gets the image resource ID of the item.
     * @return the image resource ID
     */
    public int getImageResourceId() {
        return imageResourceId;
    }

    /**
     * Gets the total price formatted as a currency string.
     * @return formatted price string
     */
    public String getFormattedPrice() {
        DecimalFormat df = new DecimalFormat("$#,##0.00");
        return df.format(getPrice());
    }

    /**
     * Provides a description of the item.
     * This method is intended to be overridden by subclasses.
     * @return description of the item, default is null
     */
    public String desc() {
        return null;
    }

    /**
     * Returns a string representation of the item,
     * including the item name, formatted price, and quantity.
     * @return formatted string representing the item
     */
    @Override
    public String toString() {
        return itemName + " - " + getFormattedPrice() + " (" + quantity + ")";
    }
}