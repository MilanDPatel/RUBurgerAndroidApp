package com.example.recycleapplication.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents a sandwich item in the restaurant application.
 * Contains information about the bread type, protein choice, add-ons, and quantity.
 */
public class Sandwich extends Item {

    private Bread bread;
    private Protein protein;
    private ArrayList<AddOns> addOns;
    private int quantity;

    /**
     * Default constructor initializes a sandwich with default values.
     */
    public Sandwich() {
        this.bread = Bread.BRIOCHE;
        this.protein = Protein.BEEF_PATTY;
        this.addOns = new ArrayList<>();
        this.quantity = 1;
    }

    /**
     * Parameterized constructor for creating a sandwich with specific attributes.
     *
     * @param bread    The type of bread
     * @param protein  The protein choice
     * @param addOns   List of add-ons
     * @param quantity The quantity of sandwiches
     */
    public Sandwich(Bread bread, Protein protein, ArrayList<AddOns> addOns, int quantity) {
        this.bread = bread;
        this.protein = protein;
        this.addOns = new ArrayList<>(addOns);
        this.quantity = quantity;
    }

    /**
     * Gets the bread type.
     *
     * @return The bread type
     */
    public Bread getBread() {
        return bread;
    }

    /**
     * Sets the bread type.
     *
     * @param bread The bread type to set
     */
    public void setBread(Bread bread) {
        this.bread = bread;
    }

    /**
     * Gets the protein choice.
     *
     * @return The protein choice
     */
    public Protein getProtein() {
        return protein;
    }

    /**
     * Sets the protein choice.
     *
     * @param protein The protein choice to set
     */
    public void setProtein(Protein protein) {
        this.protein = protein;
    }

    /**
     * Gets the list of add-ons.
     *
     * @return The list of add-ons
     */
    public ArrayList<AddOns> getAddOns() {
        return addOns;
    }

    /**
     * Sets the list of add-ons.
     *
     * @param addOns The list of add-ons to set
     */
    public void setAddOns(ArrayList<AddOns> addOns) {
        this.addOns = new ArrayList<>(addOns);
    }

    /**
     * Gets the quantity.
     *
     * @return The quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity.
     *
     * @param quantity The quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public double getPrice() {
        double basePrice = protein.getPrice();

        for (AddOns addOn : addOns) {
            basePrice += addOn.getPrice();
        }

        return basePrice * quantity;
    }

    public String desc() {
        StringBuilder description = new StringBuilder();
        description.append(protein.toString()).append(" sandwich on ").append(bread.toString());

        if (!addOns.isEmpty()) {
            description.append(" with ");
            for (int i = 0; i < addOns.size(); i++) {
                description.append(addOns.get(i).toString());
                if (i < addOns.size() - 1) {
                    description.append(", ");
                }
            }
        }
        return description.toString();
    }

    /**
     * Creates a description of the sandwich.
     *
     * @return A string description of the sandwich
     */

    public String getDescription() {
        StringBuilder description = new StringBuilder();
        description.append(protein.toString()).append(" sandwich on ").append(bread.toString());

        if (!addOns.isEmpty()) {
            description.append(" with ");
            for (int i = 0; i < addOns.size(); i++) {
                description.append(addOns.get(i).toString());
                if (i < addOns.size() - 1) {
                    description.append(", ");
                }
            }
        }

        description.append(" (").append(quantity).append(")");


        description.append("- $" ).append(String.format("%.2f", getPrice()));
        return description.toString();
    }

    /**
     * Returns a string representation of the sandwich.
     *
     * @return A string representation of the sandwich
     */
    @Override
    public String toString() {
        return getDescription();
    }
}
