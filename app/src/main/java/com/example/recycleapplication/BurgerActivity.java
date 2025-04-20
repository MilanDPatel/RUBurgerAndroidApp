package com.example.recycleapplication;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recycleapplication.model.AddOns;
import com.example.recycleapplication.model.Bread;
import com.example.recycleapplication.model.Burger;
import com.example.recycleapplication.model.Combo;
import com.example.recycleapplication.model.Order;
import com.example.recycleapplication.model.Protein;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class BurgerActivity extends AppCompatActivity {

    // UI Components
    private Spinner spinnerBread;
    private RadioGroup radioGroupPatty;
    private RadioButton radioSinglePatty, radioDoublePatty;
    private CheckBox checkLettuce, checkTomatoes, checkOnions, checkAvocado, checkCheese;
    private Button btnDecreaseQuantity, btnIncreaseQuantity, btnAddToOrder, btnCombo;
    private TextView tvQuantity, tvPrice;

    // Model objects
    private Burger currentBurger;
    private Combo combo;
    private Order currentOrder;
    private int quantity = 1;

    // Formatter for price display
    private final DecimalFormat df = new DecimalFormat("$#,##0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_burger);

        // Initialize order if not passed in
        if (getIntent().getSerializableExtra("order") != null) {
            currentOrder = (Order) getIntent().getSerializableExtra("order");
        } else {
            currentOrder = new Order();
        }

        // Initialize UI components
        initializeComponents();

        // Setup listeners for user interactions
        setupListeners();

        // Initialize burger defaults
        resetBurger();
    }

    private void initializeComponents() {
        // Bread spinner with filtered options
        spinnerBread = findViewById(R.id.spinnerBread);

        // Create a filtered list with only the three breads for burgers
        ArrayList<Bread> burgerBreads = new ArrayList<>();
        burgerBreads.add(Bread.BRIOCHE);
        burgerBreads.add(Bread.WHEAT);
        burgerBreads.add(Bread.PRETZEL);

        ArrayAdapter<Bread> breadAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, burgerBreads);
        breadAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBread.setAdapter(breadAdapter);

        // Rest of your initialization code remains the same
        // Patty radio group
        radioGroupPatty = findViewById(R.id.radioGroupPatty);
        radioSinglePatty = findViewById(R.id.radioSinglePatty);
        radioDoublePatty = findViewById(R.id.radioDoublePatty);

        // Add-ons checkboxes
        checkLettuce = findViewById(R.id.checkLettuce);
        checkTomatoes = findViewById(R.id.checkTomatoes);
        checkOnions = findViewById(R.id.checkOnions);
        checkAvocado = findViewById(R.id.checkAvocado);
        checkCheese = findViewById(R.id.checkCheese);

        // Quantity controls
        btnDecreaseQuantity = findViewById(R.id.btnDecreaseQuantity);
        tvQuantity = findViewById(R.id.tvQuantity);
        btnIncreaseQuantity = findViewById(R.id.btnIncreaseQuantity);

        // Price display
        tvPrice = findViewById(R.id.tvPrice);

        // Action buttons
        btnAddToOrder = findViewById(R.id.btnAddToOrder);
        btnCombo = findViewById(R.id.btnCombo);
    }

    private void setupListeners() {
        // Bread selection listener
        spinnerBread.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updatePrice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        // Patty selection listener
        radioGroupPatty.setOnCheckedChangeListener((group, checkedId) -> updatePrice());

        // Add-ons listeners
        setupCheckBoxListeners();

        // Quantity control listeners
        btnDecreaseQuantity.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                tvQuantity.setText(String.valueOf(quantity));
                updatePrice();
            }
        });

        btnIncreaseQuantity.setOnClickListener(v -> {
            if (quantity < 10) {
                quantity++;
                tvQuantity.setText(String.valueOf(quantity));
                updatePrice();
            }
        });

        // Action button listeners
        btnAddToOrder.setOnClickListener(v -> addToOrder());
        btnCombo.setOnClickListener(v -> showComboOptions());
    }

    private void setupCheckBoxListeners() {
        CheckBox[] checkBoxes = {checkLettuce, checkTomatoes, checkOnions, checkAvocado, checkCheese};
        for (CheckBox checkBox : checkBoxes) {
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> updatePrice());
        }
    }

    private void updatePrice() {
        updateCurrentBurger();
        double totalPrice = getBurgerPrice() * quantity;
        tvPrice.setText(df.format(totalPrice));
    }

    private void updateCurrentBurger() {
        currentBurger.setBread((Bread) spinnerBread.getSelectedItem());
        currentBurger.setDoublePatty(radioDoublePatty.isChecked());

        // Clear and update add-ons
        currentBurger.getAddOns().clear();
        if (checkLettuce.isChecked()) currentBurger.getAddOns().add(AddOns.LETTUCE);
        if (checkTomatoes.isChecked()) currentBurger.getAddOns().add(AddOns.TOMATOES);
        if (checkOnions.isChecked()) currentBurger.getAddOns().add(AddOns.ONIONS);
        if (checkAvocado.isChecked()) currentBurger.getAddOns().add(AddOns.AVOCADO);
        if (checkCheese.isChecked()) currentBurger.getAddOns().add(AddOns.CHEESE);

        currentBurger.setQuantity(quantity);
        currentBurger.setProtein(Protein.BEEF_PATTY);
    }

    private double getBurgerPrice() {
        double basePrice = 6.99;
        if (currentBurger.isDoublePatty()) {
            basePrice += 2.50;
        }
        for (AddOns addOn : currentBurger.getAddOns()) {
            basePrice += addOn.getPrice();
        }
        return basePrice;
    }

    private void resetBurger() {
        currentBurger = new Burger();
        currentBurger.setProtein(Protein.BEEF_PATTY);
        currentBurger.setBread(Bread.BRIOCHE);
        currentBurger.setQuantity(1);
        currentBurger.getAddOns().clear();

        combo = null;
        quantity = 1;

        // Reset UI components
        spinnerBread.setSelection(0); // Brioche
        radioSinglePatty.setChecked(true);
        checkLettuce.setChecked(false);
        checkTomatoes.setChecked(false);
        checkOnions.setChecked(false);
        checkAvocado.setChecked(false);
        checkCheese.setChecked(false);
        tvQuantity.setText("1");

        updatePrice();
    }

    private void addToOrder() {
        updateCurrentBurger();
        currentOrder.addItem(currentBurger);

        Toast.makeText(this, "Burger added to order", Toast.LENGTH_SHORT).show();
        resetBurger();
    }

    private void showComboOptions() {
        updateCurrentBurger();

        Intent intent = new Intent(this, ComboActivity.class);
        intent.putExtra("burger", currentBurger);
        intent.putExtra("order", currentOrder);
        startActivity(intent);
    }

    // Method to handle returning from the combo activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("combo_added")) {
                boolean comboAdded = data.getBooleanExtra("combo_added", false);
                if (comboAdded) {
                    resetBurger();
                    Toast.makeText(this, "Combo added to order", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
