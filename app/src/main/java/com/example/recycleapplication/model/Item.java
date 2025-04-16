package com.example.recycleapplication.model;

import java.io.Serializable;

public class Item implements Serializable {
    private int quantity;
    private double price;

    public Item() {
        this.quantity = 1;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
