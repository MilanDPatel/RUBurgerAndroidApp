package com.example.recycleapplication.model;

import java.io.Serializable;
import java.text.DecimalFormat;

public class Item implements Serializable {
    private String itemName;
    private int quantity;
    private double price;
    private int imageResourceId;

    public Item() {
        this.quantity = 1;
    }

    public Item(String itemName, double price) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = 1;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return quantity * price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public String getFormattedPrice() {
        DecimalFormat df = new DecimalFormat("$#,##0.00");
        return df.format(getPrice());
    }

    public String desc() {
        return null;
    }

    @Override
    public String toString() {
        return itemName + " - " + getFormattedPrice() + " (" + quantity + ")";
    }
}