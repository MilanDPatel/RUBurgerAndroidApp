package com.example.project5.model;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 Burger Class where users can customize their burgers with
 patty selection, protein, and bread.
 @author Milan Patel
 */

public class Burger extends Item {
    private Bread bread;
    private Protein protein;
    private boolean doublePatty;
    private List<AddOns> addOns;
    private int quantity;

    /**
     * Default constructor with no parameters.
     * Initializes protein to Beef and doublePatty to false
     */
    public Burger() {
        super();
        this.bread = Bread.BRIOCHE;
        this.protein = Protein.BEEF_PATTY;
        this.doublePatty = false;
        this.addOns = new ArrayList<>();
    }

    /**
     * Gets the type of bread used for the burger.
     * @return the Bread type of the burger
     */
    public Bread getBread() {
        return bread;
    }

    /**
     * Sets the type of bread for the burger.
     * @param bread the Bread type to set
     */
    public void setBread(Bread bread) {
        this.bread = bread;
    }

    /**
     * Gets the protein (patty) of the burger.
     * @return the Protein of the burger
     */
    public Protein getProtein() {
        return protein;
    }

    /**
     * Sets the protein (patty) for the burger.
     * @param protein the Protein to set
     */
    public void setProtein(Protein protein) {
        this.protein = protein;
    }

    /**
     * Checks if the burger has a double patty.
     * @return true if double patty, false otherwise
     */
    public boolean isDoublePatty() {
        return doublePatty;
    }

    /**
     * Sets whether the burger should have a double patty.
     * @param doublePatty true to set double patty, false otherwise
     */
    public void setDoublePatty(boolean doublePatty) {
        this.doublePatty = doublePatty;
    }

    /**
     * Sets the quantity of burgers.
     * @param quantity the number of burgers
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the quantity of burgers.
     * @return the quantity of burgers
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Gets the list of add-ons selected for the burger.
     * @return list of AddOns
     */
    public List<AddOns> getAddOns() {
        return addOns;
    }

    /**
     * Calculates and returns the total price of the burger based on
     * protein price, add-ons, double patty, and quantity.
     * @return the total price of the burger(s)
     */
    @Override
    public double getPrice() {
        double burgerPrice = protein.getPrice();

        if (doublePatty) {
            burgerPrice += 2.50; // Extra charge for double patty
        }
        for (AddOns addOn : addOns) {
            burgerPrice += addOn.getPrice();
        }

        return quantity * burgerPrice;
    }

    /**
     * Provides a description of the burger, including patty type, bread,
     * and selected add-ons, without price or quantity.
     * @return the burger description
     */
    @Override
    public String desc() {
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
        return sb.toString();
    }

    /**
     * Returns a string representation of the burger, including its description,
     * quantity, and total price.
     * @return formatted string containing burger details
     */
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