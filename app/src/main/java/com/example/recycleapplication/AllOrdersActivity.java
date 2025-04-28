package com.example.recycleapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.recycleapplication.model.Item;
import com.example.recycleapplication.model.Order;
import com.example.recycleapplication.model.OrderManager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Activity to display and manage all orders.
 * Allows users to view details of existing orders and cancel orders if desired.
 * Author: Milan Patel
 */

public class AllOrdersActivity extends AppCompatActivity {
    private Spinner spinnerOrders;
    private ListView listViewItems;
    private TextView textTotal;
    private Button btnCancelOrder;
    private ArrayAdapter<Item> itemAdapter;
    private ArrayAdapter<Integer> orderNumberAdapter;
    private OrderManager orderManager;
    private List<Order> orders;
    private final DecimalFormat df = new DecimalFormat("$#,##0.00");

    /**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after
     * previously being shut down, this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_orders);

        spinnerOrders = findViewById(R.id.spinnerOrders);
        listViewItems = findViewById(R.id.listViewItems);
        textTotal = findViewById(R.id.textTotal);
        btnCancelOrder = findViewById(R.id.btnCancelOrder);

        orderManager = OrderManager.getInstance();
        orders = new ArrayList<>(orderManager.getOrders());

        List<Integer> orderNumbers = new ArrayList<>();
        for (Order order : orders) {
            orderNumbers.add(order.getNumber());
        }

        orderNumberAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, orderNumbers);
        orderNumberAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOrders.setAdapter(orderNumberAdapter);

        spinnerOrders.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                Integer selectedNum = orderNumberAdapter.getItem(position);
                if (selectedNum != null) updateDetails(selectedNum);
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {

            }
        });

        btnCancelOrder.setOnClickListener(v -> {
            int position = spinnerOrders.getSelectedItemPosition();
            Integer selectedNum = orderNumberAdapter.getItem(position);

            if (selectedNum != null) {
                new AlertDialog.Builder(this)
                        .setTitle("Cancel Order")
                        .setMessage("Are you sure you want to cancel this order?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            Order order = orderManager.getOrder(selectedNum);
                            orderManager.removeOrder(order);
                            orders.remove(order);
                            orderNumberAdapter.remove(selectedNum);
                            Toast.makeText(this, "Order " + selectedNum + " canceled", Toast.LENGTH_SHORT).show();
                            itemAdapter.clear();
                            if (!orders.isEmpty()) {
                                updateDetails(orderNumberAdapter.getItem(0));
                            } else {
                                itemAdapter.clear();
                                textTotal.setText("");
                            }
                        })
                        .setNegativeButton("No", (dialog, which) -> {
                            dialog.dismiss();
                        })
                        .show();
            }
            else {
                Toast.makeText(this, "No order selected to cancel", Toast.LENGTH_SHORT).show();
            }
        });

        if (!orders.isEmpty()) {
            updateDetails(0);
        }

    }

    /**
     * Updates the displayed details based on the selected order number.
     * @param number the order number to display
     */
    public void updateDetails(int number) {
        Order selectedOrder = orderManager.getOrder(number);
        if (selectedOrder == null) {
            return;
        }

        itemAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, selectedOrder.getItems());
        listViewItems.setAdapter(itemAdapter);
        textTotal.setText(df.format(selectedOrder.getTotal()));
    }
}
