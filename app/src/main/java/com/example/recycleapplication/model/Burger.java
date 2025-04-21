package com.example.recycleapplication.model;

import java.util.ArrayList;
import java.util.List;
public class Burger extends Item {
    private Bread bread;
    private Protein protein;
    private boolean doublePatty;
    private List<AddOns> addOns;

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

        return burgerPrice;
    }
}