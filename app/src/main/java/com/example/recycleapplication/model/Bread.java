package com.example.recycleapplication.model;

public enum Bread {
    BRIOCHE("Brioche"),
    WHEAT("Wheat"),
    PRETZEL("Pretzel");

    private final String name;

    Bread(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
