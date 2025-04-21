package com.example.recycleapplication;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recycleapplication.model.Item;
import com.example.recycleapplication.model.OrderManager;

import java.text.DecimalFormat;

public class AllOrdersActivity extends AppCompatActivity {
    private Spinner spinnerOrders;
    private ListView listViewItems;
    private TextView textTotal;
    private Button btnCancelOrder;
    private ArrayAdapter<Item> itemAdapter;
    private ArrayAdapter<Integer> orderNumberAdapter;
    private OrderManager orderManager;
    private final DecimalFormat df = new DecimalFormat("$#,##0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_orders);

        spinnerOrders = findViewById(R.id.spinnerOrders);
        listViewItems = findViewById(R.id.listViewItems);
        textTotal = findViewById(R.id.textTotal);
        btnCancelOrder = findViewById(R.id.btnCancelOrder);

        orderManager = OrderManager.getInstance();

    }
}
