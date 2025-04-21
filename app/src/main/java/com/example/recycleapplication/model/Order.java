package com.example.recycleapplication.model;
import java.util.ArrayList;
import java.util.List;
public final class Order {
    private static Order order;
    private List<Item> items;

    public Order() {
        this.items = new ArrayList<>();
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
    public void clear() {
        items.clear();
    }
}
