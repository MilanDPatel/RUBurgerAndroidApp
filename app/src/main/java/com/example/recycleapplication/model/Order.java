package com.example.recycleapplication.model;
import java.util.ArrayList;
import java.util.List;

/**
 * Order class is a singleton class representing an order of selected menu items.
 * Contains the selected menu items and assigned order number.
 * @author Aditya Shah
 */
public final class Order {
    private static Order order;
    private List<Item> items;
    private int number;
    private final double TAX_RATE = 0.06625;

    /**
     * Default constructor
     * Initializes the items list and order number.
     */
    public Order() {
        this.items = new ArrayList<>();
        number = 1;
    }

    /**
     * Constructor with order parameter to make a copy of another order.
     * @param order the order to make a copy of.
     */
    public Order(Order order) {
        this.number = order.number;
        this.items = new ArrayList<>(order.items);
    }

    /**
     * Retrieves the singleton instance of Order class.
     * @return the singleton instance of Order.
     */
    public static synchronized Order getInstance() {
        if (order == null) {
            order = new Order();
        }
        return order;
    }

    /**
     * Adds an item to an order.
     * @param item the item to add.
     */
    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * Retrieves the list of items in the order.
     * @return the items in the order as a list.
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * Removes an item at a certain index from order.
     * @param index the index of the item to remove.
     */
    public void removeItem(int index) {
        if (index >= 0 && index < items.size()) {
            items.remove(index);
        }
    }

    /**
     * Sets the order number.
     * @param num the new order number.
     */
    public void setNumber(int num) {
        number = num;
    }

    /**
     * Retrieves the order number.
     * @return the order number.
     */
    public int getNumber() {
        return number;
    }

    /**
     * Removes all items from the order.
     */
    public void clear() {
        items.clear();
    }

    /**
     * Sums the prices of all the items in the order.
     * @return the total cost of the order.
     */
    public double getTotal() {
        double total = 0;
        for (Item item: getItems()) {
            total += item.getPrice();
        }
        total += total * TAX_RATE;
        return total;
    }
}
