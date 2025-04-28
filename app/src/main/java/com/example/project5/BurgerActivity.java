package com.example.project5;


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

import com.example.project5.model.AddOns;
import com.example.project5.model.Bread;
import com.example.project5.model.Burger;
import com.example.project5.model.Combo;
import com.example.project5.model.Order;
import com.example.project5.model.Protein;
import com.example.project5.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * BurgerActivity allows users to customize and add a burger to their order.
 * Users can choose bread, patty type, toppings (add-ons), quantity, and make a combo.
 * Author: Milan Patel
 */

public class BurgerActivity extends AppCompatActivity {

    private Spinner spinnerBread;
    private RadioGroup radioGroupPatty;
    private RadioButton radioSinglePatty, radioDoublePatty;
    private CheckBox checkLettuce, checkTomatoes, checkOnions, checkAvocado, checkCheese;
    private Button btnDecreaseQuantity, btnIncreaseQuantity, btnAddToOrder, btnCombo;
    private TextView tvQuantity, tvPrice;

    private Burger currentBurger;
    private Combo combo;
    private Order currentOrder;
    private int quantity = 1;


    private final DecimalFormat df = new DecimalFormat("$#,##0.00");

    /**
     * Called when the activity is starting.
     * @param savedInstanceState If the activity is being
     * re-initialized after previously being shut down,
     * this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_burger);


        currentOrder = Order.getInstance();

        initializeComponents();

        setupListeners();

        resetBurger();
    }

    /**
     * Initializes UI components and adapters (like setting up the bread spinner).
     */
    private void initializeComponents() {
        spinnerBread = findViewById(R.id.spinnerBread);

        ArrayList<Bread> burgerBreads = new ArrayList<>();
        burgerBreads.add(Bread.BRIOCHE);
        burgerBreads.add(Bread.WHEAT);
        burgerBreads.add(Bread.PRETZEL);

        ArrayAdapter<Bread> breadAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, burgerBreads);
        breadAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBread.setAdapter(breadAdapter);

        radioGroupPatty = findViewById(R.id.radioGroupPatty);
        radioSinglePatty = findViewById(R.id.radioSinglePatty);
        radioDoublePatty = findViewById(R.id.radioDoublePatty);

        checkLettuce = findViewById(R.id.checkLettuce);
        checkTomatoes = findViewById(R.id.checkTomatoes);
        checkOnions = findViewById(R.id.checkOnions);
        checkAvocado = findViewById(R.id.checkAvocado);
        checkCheese = findViewById(R.id.checkCheese);

        btnDecreaseQuantity = findViewById(R.id.btnDecreaseQuantity);
        tvQuantity = findViewById(R.id.tvQuantity);
        btnIncreaseQuantity = findViewById(R.id.btnIncreaseQuantity);
        tvPrice = findViewById(R.id.tvPrice);

        btnAddToOrder = findViewById(R.id.btnAddToOrder);
        btnCombo = findViewById(R.id.btnCombo);
    }

    /**
     * Setup event listeners for user interaction.
     */
    private void setupListeners() {
        // Bread selection listener
        spinnerBread.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updatePrice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        radioGroupPatty.setOnCheckedChangeListener((group, checkedId) -> updatePrice());

        setupCheckBoxListeners();

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

        btnAddToOrder.setOnClickListener(v -> addToOrder());
        btnCombo.setOnClickListener(v -> showComboOptions());
    }

    /**
     * Setup change listeners for all topping checkboxes to trigger price updates.
     */
    private void setupCheckBoxListeners() {
        CheckBox[] checkBoxes = {checkLettuce, checkTomatoes, checkOnions, checkAvocado, checkCheese};
        for (CheckBox checkBox : checkBoxes) {
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> updatePrice());
        }
    }

    /**
     * Updates the displayed price based on current selections and quantity.
     */
    private void updatePrice() {
        updateCurrentBurger();
        double totalPrice = getBurgerPrice() * quantity;
        tvPrice.setText(df.format(totalPrice));
    }

    /**
     * Updates the Burger object based on the current UI state.
     */
    private void updateCurrentBurger() {
        currentBurger.setBread((Bread) spinnerBread.getSelectedItem());
        currentBurger.setDoublePatty(radioDoublePatty.isChecked());

        currentBurger.getAddOns().clear();
        if (checkLettuce.isChecked()) currentBurger.getAddOns().add(AddOns.LETTUCE);
        if (checkTomatoes.isChecked()) currentBurger.getAddOns().add(AddOns.TOMATOES);
        if (checkOnions.isChecked()) currentBurger.getAddOns().add(AddOns.ONIONS);
        if (checkAvocado.isChecked()) currentBurger.getAddOns().add(AddOns.AVOCADO);
        if (checkCheese.isChecked()) currentBurger.getAddOns().add(AddOns.CHEESE);

        currentBurger.setQuantity(quantity);
        currentBurger.setProtein(Protein.BEEF_PATTY);
    }

    /**
     * Calculates the base price of the burger with selected add-ons and patty type.
     * @return total price of one burger
     */
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

    /**
     * Resets the UI and current burger to default settings.
     */
    private void resetBurger() {
        currentBurger = new Burger();
        currentBurger.setProtein(Protein.BEEF_PATTY);
        currentBurger.setBread(Bread.BRIOCHE);
        currentBurger.setQuantity(1);
        currentBurger.getAddOns().clear();

        combo = null;
        quantity = 1;

        spinnerBread.setSelection(0);
        radioSinglePatty.setChecked(true);
        checkLettuce.setChecked(false);
        checkTomatoes.setChecked(false);
        checkOnions.setChecked(false);
        checkAvocado.setChecked(false);
        checkCheese.setChecked(false);
        tvQuantity.setText("1");

        updatePrice();
    }

    /**
     * Adds the current burger to the order and resets the burger for a new customization.
     */
    private void addToOrder() {
        updateCurrentBurger();
        currentOrder.addItem(currentBurger);

        Toast.makeText(this, "Burger added to order", Toast.LENGTH_SHORT).show();
        resetBurger();
    }

    /**
     * Launches ComboActivity to allow user to add sides and drinks to their burger.
     */
    private void showComboOptions() {
        updateCurrentBurger();

        Intent intent = new Intent(this, ComboActivity.class);
        intent.putExtra("burger", currentBurger);
        startActivity(intent);
    }

    /**
     * Handles result when coming back from ComboActivity.
     */
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
