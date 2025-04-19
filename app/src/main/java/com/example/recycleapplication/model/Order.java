package com.example.recycleapplication.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class Order implements Serializable {
    private List<Item> items;

    public Order() {
        this.items = new ArrayList<>();
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
