package com.example.recycleapplication;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recycleapplication.model.Item;
import com.example.recycleapplication.model.Order;

import java.text.DecimalFormat;

public class CurrentOrderActivity extends AppCompatActivity {
    private ListView orderListView;
    private Button removeSelectedButton, placeOrderButton;
    private TextView subtotalText, taxText, totalText;

    private ArrayAdapter<String> adapter;
    private Order currentOrder;

    private final double TAX_RATE = 0.06625;
    private final DecimalFormat df = new DecimalFormat("$#,##0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_order);

        if (getIntent().getSerializableExtra("order") != null) {
            currentOrder = (Order) getIntent().getSerializableExtra("order");
        }

        orderListView = findViewById(R.id.orderListView);
        removeSelectedButton = findViewById(R.id.removeSelectedButton);
        placeOrderButton = findViewById(R.id.btnPlaceOrder);

        subtotalText = findViewById(R.id.subtotalText);
        taxText = findViewById(R.id.taxText);
        totalText = findViewById(R.id.totalText);

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_single_choice,
                currentOrder.getStringItems());

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
                currentOrder.clear();
                adapter.notifyDataSetChanged();
                orderListView.clearChoices();
                updateTotals();
            }
        });
    }


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
