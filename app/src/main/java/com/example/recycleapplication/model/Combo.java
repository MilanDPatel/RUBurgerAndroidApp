package com.example.recycleapplication.model;

import java.io.Serializable;

public class Combo implements Serializable {
    private Item sandwich;
    private Item side;
    private Item drink;

    public Combo() {
    }

    public Item getSandwich() {
        return sandwich;
    }

    public void setSandwich(Item sandwich) {
        this.sandwich = sandwich;
    }

    public Item getSide() {
        return side;
    }

    public void setSide(Item side) {
        this.side = side;
    }

    public Item getDrink() {
        return drink;
    }

    public void setDrink(Item drink) {
        this.drink = drink;
    }

    public String toString() {
        return "Combo " + sandwich.toString() + side.toString() + drink.toString();
    }
}

