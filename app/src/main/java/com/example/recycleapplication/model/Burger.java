package com.example.recycleapplication.model;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class Burger extends Item {
    private Bread bread;
    private Protein protein;
    private boolean doublePatty;
    private List<AddOns> addOns;
    private int quantity;

    public Burger() {
        super();
        this.bread = Bread.BRIOCHE;
        this.protein = Protein.BEEF_PATTY;
        this.doublePatty = false;
        this.addOns = new ArrayList<>();
    }

    public Bread getBread() {
        return bread;
    }

    public void setBread(Bread bread) {
        this.bread = bread;
    }

    public Protein getProtein() {
        return protein;
    }

    public void setProtein(Protein protein) {
        this.protein = protein;
    }

    public boolean isDoublePatty() {
        return doublePatty;
    }

    public void setDoublePatty(boolean doublePatty) {
        this.doublePatty = doublePatty;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
    public List<AddOns> getAddOns() {
        return addOns;
    }

    @Override
    public double getPrice() {
        double burgerPrice = protein.getPrice();

        if (doublePatty) {
            burgerPrice += 2.50;
        }

        for (AddOns addOn : addOns) {
            burgerPrice += addOn.getPrice();
        }

        return quantity * burgerPrice;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(doublePatty ? "Double" : "Single").append(" Burger on ").append(bread);


        if (!addOns.isEmpty()) {
            sb.append(" with ");
            StringJoiner joiner = new StringJoiner(", ");
            for (AddOns addOn : addOns) {
                joiner.add(addOn.toString());
            }
            sb.append(joiner);
        }


        sb.append(" (").append(quantity).append(")");
        sb.append(" $").append(String.format("%.2f", getPrice()));


        return sb.toString();
    }
}