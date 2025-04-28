package com.example.project5.model;

import java.util.ArrayList;
import java.util.List;

/**
 * OrderManager class is a singleton class representing a manager of all placed orders.
 * Contains a list of placed orders.
 * @author Aditya Shah
 */
public final class OrderManager {
    private static OrderManager orderManager;
    private List<Order> orders;

    /**
     * Default constructor.
     * Initializes the list of orders.
     */
    public OrderManager() {
        this.orders = new ArrayList<>();
    }

    /**
     * Retrieves the singleton instance of OrderManager.
     * @return the singleton instance of OrderManager.
     */
    public static OrderManager getInstance() {
        if (orderManager == null) {
            orderManager = new OrderManager();
        }
        return orderManager;
    }

    /**
     * Retrieves an order by the given order number.
     * @param number the order number to look for.
     * @return the order with the given number, null if non-existent.
     */
    public Order getOrder(int number) {
        for (Order order: orders) {
            if (order.getNumber() == number) {
                return order;
            }
        }
        return null;
    }

    /**
     * Adds a order to the list of orders.
     * @param order the order to add.
     */
    public void addOrder(Order order) {
        orders.add(order);
    }

    /**
     * Removes an order from the list of orders.
     * @param order the order to remove.
     */
    public void removeOrder(Order order) {
        orders.remove(order);
    }

    /**
     * Retrieves the list of orders.
     * @return the list of orders.
     */
    public List<Order> getOrders() {
        return orders;
    }
}
