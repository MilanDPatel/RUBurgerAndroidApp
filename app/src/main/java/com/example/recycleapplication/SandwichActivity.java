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
import com.example.recycleapplication.model.Combo;
import com.example.recycleapplication.model.Order;
import com.example.recycleapplication.model.Protein;
import com.example.recycleapplication.model.Sandwich;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SandwichActivity extends AppCompatActivity {

    // UI Components
    private Spinner spinnerBread;
    private RadioGroup radioGroupProtein;
    private RadioButton radioRoastBeef, radioSalmon, radioChicken;
    private CheckBox checkLettuce, checkTomatoes, checkOnions, checkAvocado, checkCheese;
    private Button btnDecreaseQuantity, btnIncreaseQuantity, btnAddToOrder, btnCombo;
    private TextView tvQuantity, tvPrice;

    // Model objects
    private Sandwich currentSandwich;
    private Combo combo;
    private Order currentOrder;
    private int quantity = 1;

    // Formatter for price display
    private final DecimalFormat df = new DecimalFormat("$#,##0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sandwich);

        // Initialize order if not passed in
        currentOrder = Order.getInstance();

        // Initialize UI components
        initializeComponents();

        // Setup listeners for user interactions
        setupListeners();

        // Initialize sandwich defaults
        resetSandwich();
    }

    private void initializeComponents() {
        // Bread spinner
        spinnerBread = findViewById(R.id.spinnerBread);
        ArrayAdapter<Bread> breadAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, Bread.values());
        breadAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBread.setAdapter(breadAdapter);

        // Protein radio group
        radioGroupProtein = findViewById(R.id.radioGroupProtein);
        radioRoastBeef = findViewById(R.id.radioRoastBeef);
        radioSalmon = findViewById(R.id.radioSalmon);
        radioChicken = findViewById(R.id.radioChicken);

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

        // Protein selection listener
        radioGroupProtein.setOnCheckedChangeListener((group, checkedId) -> updatePrice());

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
            if (quantity < 5) {  // Match with JavaFX limit
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
        updateCurrentSandwich();
        double totalPrice = getSandwichPrice() * quantity;
        tvPrice.setText(df.format(totalPrice));
    }

    private void updateCurrentSandwich() {
        currentSandwich.setBread((Bread) spinnerBread.getSelectedItem());

        // Set protein based on selected radio button
        if (radioRoastBeef.isChecked()) {
            currentSandwich.setProtein(Protein.ROAST_BEEF);
        } else if (radioSalmon.isChecked()) {
            currentSandwich.setProtein(Protein.SALMON);
        } else if (radioChicken.isChecked()) {
            currentSandwich.setProtein(Protein.CHICKEN);
        }

        // Clear and update add-ons
        currentSandwich.getAddOns().clear();
        if (checkLettuce.isChecked()) currentSandwich.getAddOns().add(AddOns.LETTUCE);
        if (checkTomatoes.isChecked()) currentSandwich.getAddOns().add(AddOns.TOMATOES);
        if (checkOnions.isChecked()) currentSandwich.getAddOns().add(AddOns.ONIONS);
        if (checkAvocado.isChecked()) currentSandwich.getAddOns().add(AddOns.AVOCADO);
        if (checkCheese.isChecked()) currentSandwich.getAddOns().add(AddOns.CHEESE);

        currentSandwich.setQuantity(quantity);
    }

    private double getSandwichPrice() {
        double basePrice = currentSandwich.getProtein().getPrice();
        for (AddOns addOn : currentSandwich.getAddOns()) {
            basePrice += addOn.getPrice();
        }
        return basePrice;
    }

    private void resetSandwich() {
        currentSandwich = new Sandwich();
        currentSandwich.setProtein(Protein.ROAST_BEEF);
        currentSandwich.setBread(Bread.BRIOCHE);
        currentSandwich.setQuantity(1);
        currentSandwich.getAddOns().clear();

        combo = null;
        quantity = 1;

        // Reset UI components
        spinnerBread.setSelection(0); // Brioche
        radioRoastBeef.setChecked(true);
        checkLettuce.setChecked(false);
        checkTomatoes.setChecked(false);
        checkOnions.setChecked(false);
        checkAvocado.setChecked(false);
        checkCheese.setChecked(false);
        tvQuantity.setText("1");

        updatePrice();
    }

    private void addToOrder() {
        updateCurrentSandwich();
        currentOrder.addItem(currentSandwich);

        Toast.makeText(this, "Sandwich added to order", Toast.LENGTH_SHORT).show();
        resetSandwich();
    }

    private void showComboOptions() {
        updateCurrentSandwich();

        Intent intent = new Intent(this, ComboActivity.class);
        intent.putExtra("sandwich", currentSandwich);
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
                    resetSandwich();
                    Toast.makeText(this, "Combo added to order", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public String getAddOnsDescription() {
        StringBuilder addOnsDesc = new StringBuilder();

        if (currentSandwich.getAddOns().isEmpty()) {
            return "None";
        }

        for (AddOns addOn : currentSandwich.getAddOns()) {
            if (addOnsDesc.length() > 0) {
                addOnsDesc.append(", ");
            }
            addOnsDesc.append(addOn.toString());
        }

        return addOnsDesc.toString();
    }
}
