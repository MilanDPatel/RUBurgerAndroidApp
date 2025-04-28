package com.example.recycleapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.recycleapplication.model.Burger;
import com.example.recycleapplication.model.Item;
import com.example.recycleapplication.model.Order;
import com.example.recycleapplication.model.OrderManager;
import com.example.recycleapplication.model.Sandwich;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * CurrentOrderActivity allows users to add their items to the cart.
 * Users can remove items or order them.
 * Author: Milan Patel
 */

public class CurrentOrderActivity extends AppCompatActivity {
    private ListView orderListView;
    private Button removeSelectedButton, placeOrderButton;
    private TextView subtotalText, taxText, totalText;

    private ArrayAdapter<Item> adapter;
    private Order currentOrder;
    private OrderManager orderManager;

    private final double TAX_RATE = 0.06625;
    private final DecimalFormat df = new DecimalFormat("$#,##0.00");

    /**
     * Initializes the activity, sets up the UI elements, and handles the logic for creating the order.
     * @param savedInstanceState The saved instance state, containing data on the previous activity state if available.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_order);

        currentOrder = Order.getInstance();
        orderManager = OrderManager.getInstance();

        orderListView = findViewById(R.id.orderListView);
        removeSelectedButton = findViewById(R.id.removeSelectedButton);
        placeOrderButton = findViewById(R.id.btnPlaceOrder);

        subtotalText = findViewById(R.id.subtotalText);
        taxText = findViewById(R.id.taxText);
        totalText = findViewById(R.id.totalText);

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_single_choice,
                currentOrder.getItems());

        Log.d("AdapterDebug", "Adapter count: " + adapter.getCount());
        for (int i = 0; i < adapter.getCount(); i++) {
            Log.d("AdapterDebug", "Item at " + i + ": " + adapter.getItem(i));
        }
        orderListView.setAdapter(adapter);

        updateTotals();
        removeSelectedButton.setOnClickListener(v -> {
            int pos = orderListView.getCheckedItemPosition();
            if (pos != ListView.INVALID_POSITION) {
                currentOrder.removeItem(pos);
                adapter.notifyDataSetChanged();
                orderListView.clearChoices();
                updateTotals();
            }
        });
        placeOrderButton.setOnClickListener(v -> {
            if (!currentOrder.getItems().isEmpty()) {

                StringBuilder orderSummary = new StringBuilder();
                for (Item item : currentOrder.getItems()) {
                    orderSummary.append("-").append(item.toString()).append("\n");
                }
                orderSummary.append("\nTotal: ").append(df.format(currentOrder.getTotal()));

                new AlertDialog.Builder(this)
                        .setTitle("Confirm Your Order")
                        .setMessage(orderSummary.toString())
                        .setPositiveButton("Confirm", (dialog, which) -> {
                            Order order = new Order(currentOrder);
                            orderManager.addOrder(order);
                            Toast.makeText(this, "Order has been placed", Toast.LENGTH_SHORT).show();
                            currentOrder.clear();
                            int num = currentOrder.getNumber();
                            currentOrder.setNumber(num + 1);
                            adapter.notifyDataSetChanged();
                            orderListView.clearChoices();
                            updateTotals();
                        })
                        .setNegativeButton("Cancel", (dialog, which) -> {
                            dialog.dismiss();
                        })
                        .show();
            }
        });
    }

    /**
     * Updates the subtotal, tax, and total values displayed in the UI
     */
    private void updateTotals() {
        double subtotal = 0;
        for (Item item: currentOrder.getItems()) {
            subtotal += item.getPrice();
        }

        double tax = subtotal * TAX_RATE;
        double total = subtotal + tax;

        subtotalText.setText(df.format(subtotal));
        totalText.setText(df.format(total));
        taxText.setText(df.format(tax));
    }

}
