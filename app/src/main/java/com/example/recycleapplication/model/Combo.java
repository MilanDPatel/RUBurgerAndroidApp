package com.example.recycleapplication.model;

import java.io.Serializable;

/**
 Combo Class where users can order a combo of
 a burger or sandwich with a drink and a side.
 @author Milan Patel
 */
public class Combo implements Serializable {
    private Item sandwich;
    private Item side;
    private Item drink;

    /**
     * Default constructor.
     * Initializes an empty Combo with no sandwich, side, or drink set.
     */
    public Combo() {
    }

    /**
     * Gets the sandwich item of the combo.
     * @return the sandwich Item
     */
    public Item getSandwich() {
        return sandwich;
    }

    /**
     * Sets the sandwich item of the combo.
     * @param sandwich the Item to set as the sandwich
     */
    public void setSandwich(Item sandwich) {
        this.sandwich = sandwich;
    }

    /**
     * Gets the side item of the combo.
     * @return the side Item
     */
    public Item getSide() {
        return side;
    }

    /**
     * Sets the side item of the combo.
     * @param side the Item to set as the side
     */
    public void setSide(Item side) {
        this.side = side;
    }

    /**
     * Gets the drink item of the combo.
     * @return the drink Item
     */
    public Item getDrink() {
        return drink;
    }

    /**
     * Sets the drink item of the combo.
     * @param drink the Item to set as the drink
     */
    public void setDrink(Item drink) {
        this.drink = drink;
    }

    /**
     * Returns a string representation of the combo,
     * including the sandwich, side, and drink descriptions.
     * @return formatted string containing combo details
     */
    @Override
    public String toString() {
        return "Combo " + sandwich.toString() + side.toString() + drink.toString();
    }
}

