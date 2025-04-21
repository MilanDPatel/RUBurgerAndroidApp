package com.example.recycleapplication.model;
import java.util.ArrayList;
import java.util.List;
public final class Order {
    private static Order order;
    private List<Item> items;
    private int number;
    private final double TAX_RATE = 0.06625;

    public Order() {
        this.items = new ArrayList<>();
        number = 1;
    }

    public Order(Order order) {
        this.number = order.number;
        this.items = new ArrayList<>(order.items);
    }
    public static synchronized Order getInstance() {
        if (order == null) {
            order = new Order();
        }
        return order;
    }
    public void addItem(Item item) {
        items.add(item);
    }

    public List<Item> getItems() {
        return items;
    }
    public void removeItem(int index) {
        if (index >= 0 && index < items.size()) {
            items.remove(index);
        }
    }

    public void setNumber(int num) {
        number = num;
    }
    public int getNumber() {
        return number;
    }
    public void clear() {
        items.clear();
    }

    public double getTotal() {
        double total = 0;
        for (Item item: getItems()) {
            total += item.getPrice();
        }
        total += total * TAX_RATE;
        return total;
    }
}
