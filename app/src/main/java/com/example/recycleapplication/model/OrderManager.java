package com.example.recycleapplication.model;

import java.util.ArrayList;
import java.util.List;

public final class OrderManager {
    private static OrderManager orderManager;
    private List<Order> orders;

    public OrderManager() {
        this.orders = new ArrayList<>();
    }

    public static OrderManager getInstance() {
        if (orderManager == null) {
            orderManager = new OrderManager();
        }
        return orderManager;
    }

    public Order getOrder(int number) {
        for (Order order: orders) {
            if (order.getNumber() == number) {
                return order;
            }
        }
        return null;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }
    public void removeOrder(Order order) {
        orders.remove(order);
    }

    public List<Order> getOrders() {
        return orders;
    }
}
