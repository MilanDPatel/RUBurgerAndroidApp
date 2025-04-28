package com.example.recycleapplication.model;

/**
 Enum representing types of bread for burgers and sandwiches.
 @author Milan Patel
 */
public enum Bread {
    BRIOCHE("Brioche"),
    WHEAT("Wheat"),
    PRETZEL("Pretzel"),
    BAGEL("Bagel"),
    SOURDOUGH("Sourdough");

    private final String name;

    /**
     * Parameterized constructor with 1 parameter.
     * @param name the name associated with the bread.
     */
    Bread(String name) {
        this.name = name;
    }

    /**
     * Returns a textual representation of the bread.
     * @return a legible string of the bread.
     */
    @Override
    public String toString() {
        return name;
    }

}
