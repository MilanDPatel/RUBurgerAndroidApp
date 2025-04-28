package com.example.recycleapplication.model;

/**
 * Protein Enum represents protein options for burgers/sandwiches.
 * Contains information such as price and name.
 * @author Aditya Shah
 */
public enum Protein {
    ROAST_BEEF("Roast beef", 10.99),
    SALMON("Salmon", 9.99),
    CHICKEN("Chicken", 8.99),
    BEEF_PATTY("Beef patty", 6.99);

    private final String name;
    private final double price;

    /**
     * Parameterized constructor with 2 parameters.
     * @param name the name associated with the protein.
     * @param price the price associated with the protein.
     */
    Protein(String name, double price) {
        this.name = name;
        this.price = price;
    }

    /**
     * Returns the price associated with the protein.
     * @return the price of the protein as a double
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns a textual representation of the protein.
     * @return a legible string of the protein as a String.
     */
    @Override
    public String toString() {
        return name;
    }
}
