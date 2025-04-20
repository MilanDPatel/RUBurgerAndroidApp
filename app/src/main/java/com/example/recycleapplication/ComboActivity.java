package com.example.recycleapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recycleapplication.model.Bread;
import com.example.recycleapplication.model.Burger;
import com.example.recycleapplication.model.Combo;
import com.example.recycleapplication.model.Item;
import com.example.recycleapplication.model.Order;
import com.example.recycleapplication.model.Protein;
import com.example.recycleapplication.model.Sandwich; // Added import for Sandwich class

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComboActivity extends AppCompatActivity {

    // UI Components
    private Spinner spinnerSide;
    private Spinner spinnerDrink;
    private Button btnAddComboToOrder;
    private Button btnGoBack;
    private TextView tvMainItemDetails;
    private TextView tvComboPrice;

    // Model objects
    private Item selectedMainItem; // Can be a burger or a sandwich
    private Item selectedSide;
    private Item selectedDrink;
    private Combo currentCombo;
    private Order currentOrder;
    private boolean isBurger; // Flag to determine if main item is a burger or sandwich

    // Data
    private List<String> sideNames;
    private List<String> drinkNames;
    private Map<String, Double> sidePrices;
    private Map<String, Double> drinkPrices;

    // Fixed combo add-on price
    private static final double COMBO_ADDON_PRICE = 2.00;

    // Formatter for price display
    private final DecimalFormat df = new DecimalFormat("$#,##0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combo);

        // Get data passed from previous Activity
        if (getIntent().hasExtra("burger") && getIntent().hasExtra("order")) {
            selectedMainItem = (Burger) getIntent().getSerializableExtra("burger");
            currentOrder = (Order) getIntent().getSerializableExtra("order");
            isBurger = true;
        } else if (getIntent().hasExtra("sandwich") && getIntent().hasExtra("order")) {
            selectedMainItem = (Sandwich) getIntent().getSerializableExtra("sandwich");
            currentOrder = (Order) getIntent().getSerializableExtra("order");
            isBurger = false;
        } else {
            Toast.makeText(this, "Error: Main item data missing", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Initialize combo
        currentCombo = new Combo();
        currentCombo.setSandwich(selectedMainItem);

        // Initialize UI components
        initializeComponents();

        // Load data for sides and drinks
        loadItemData();

        // Set up spinners
        setupSpinners();

        // Display main item details
        displayMainItemDetails();

        // Initialize default selections
        if (!sideNames.isEmpty()) {
            selectedSide = createSideItem(sideNames.get(0));
            currentCombo.setSide(selectedSide);
        }

        if (!drinkNames.isEmpty()) {
            selectedDrink = createDrinkItem(drinkNames.get(0));
            currentCombo.setDrink(selectedDrink);
        }

        // Initial price calculation
        updateComboPrice();
    }

    private void initializeComponents() {
        spinnerSide = findViewById(R.id.spinnerSide);
        spinnerDrink = findViewById(R.id.spinnerDrink);
        btnAddComboToOrder = findViewById(R.id.btnAddComboToOrder);
        btnGoBack = findViewById(R.id.btnGoBack);
        tvMainItemDetails = findViewById(R.id.tvBurgerDetails); // Keep using the existing ID
        tvComboPrice = findViewById(R.id.tvComboPrice);

        // Button listeners
        btnAddComboToOrder.setOnClickListener(v -> addComboToOrder());
        btnGoBack.setOnClickListener(v -> finish());
    }

    private void loadItemData() {
        // Initialize side items data
        sideNames = new ArrayList<>();
        sidePrices = new HashMap<>();

        sideNames.add("Chips (Small)");
        sideNames.add("Apple (Small)");

        // All sides are included in the combo price
        sidePrices.put("Chips (Small)", 0.0);
        sidePrices.put("Apple (Small)", 0.0);

        // Initialize drink items data
        drinkNames = new ArrayList<>();
        drinkPrices = new HashMap<>();

        drinkNames.add("Cola (Medium)");
        drinkNames.add("Tea (Medium)");
        drinkNames.add("Juice (Medium)");

        // All drinks are included in the combo price
        drinkPrices.put("Cola (Medium)", 0.0);
        drinkPrices.put("Tea (Medium)", 0.0);
        drinkPrices.put("Juice (Medium)", 0.0);
    }

    private Item createSideItem(String sideName) {
        Item item = new Item();
        item.setPrice(sidePrices.get(sideName));
        item.setQuantity(1);
        return item;
    }

    private Item createDrinkItem(String drinkName) {
        Item item = new Item();
        item.setPrice(drinkPrices.get(drinkName));
        item.setQuantity(1);
        return item;
    }

    private void setupSpinners() {
        // Side spinner setup
        ArrayAdapter<String> sideAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                sideNames
        );
        sideAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSide.setAdapter(sideAdapter);
        spinnerSide.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedSideName = sideNames.get(position);
                selectedSide = createSideItem(selectedSideName);
                currentCombo.setSide(selectedSide);
                updateComboPrice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        // Drink spinner setup
        ArrayAdapter<String> drinkAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                drinkNames
        );
        drinkAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDrink.setAdapter(drinkAdapter);
        spinnerDrink.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedDrinkName = drinkNames.get(position);
                selectedDrink = createDrinkItem(selectedDrinkName);
                currentCombo.setDrink(selectedDrink);
                updateComboPrice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void displayMainItemDetails() {
        StringBuilder details = new StringBuilder();

        if (isBurger) {
            Burger burger = (Burger) selectedMainItem;
            details.append("Selected Burger:\n");
            details.append(burger.getBread().toString()).append(" bun with ");
            details.append(burger.isDoublePatty() ? "double" : "single").append(" patty\n");

            // Add toppings for burger
            if (!burger.getAddOns().isEmpty()) {
                details.append("Toppings: ");
                for (int i = 0; i < burger.getAddOns().size(); i++) {
                    details.append(burger.getAddOns().get(i).toString());
                    if (i < burger.getAddOns().size() - 1) {
                        details.append(", ");
                    }
                }
                details.append("\n");
            }

            // Add quantity
            details.append("Quantity: ").append(burger.getQuantity()).append("\n");
            details.append("Price: ").append(df.format(calculateMainItemPrice())).append("\n");
        } else {
            // Display sandwich details using the Sandwich class
            Sandwich sandwich = (Sandwich) selectedMainItem;
            details.append("Selected Sandwich:\n");
            details.append(sandwich.getBread().toString()).append(" with ");
            details.append(sandwich.getProtein().toString()).append("\n");

            // Add toppings for sandwich
            if (!sandwich.getAddOns().isEmpty()) {
                details.append("Toppings: ");
                for (int i = 0; i < sandwich.getAddOns().size(); i++) {
                    details.append(sandwich.getAddOns().get(i).toString());
                    if (i < sandwich.getAddOns().size() - 1) {
                        details.append(", ");
                    }
                }
                details.append("\n");
            }

            // Add quantity
            details.append("Quantity: ").append(sandwich.getQuantity()).append("\n");
            details.append("Price: ").append(df.format(calculateMainItemPrice())).append("\n");
        }

        tvMainItemDetails.setText(details.toString());
    }

    private void updateComboPrice() {
        double mainItemPrice = calculateMainItemPrice();
        double comboPrice = mainItemPrice + COMBO_ADDON_PRICE;

        // Update price display
        String priceText = (isBurger ? "Burger" : "Sandwich") + " Price: " + df.format(mainItemPrice) + "\n" +
                "Combo Addition: " + df.format(COMBO_ADDON_PRICE) + "\n" +
                "Total Combo Price: " + df.format(comboPrice);

        tvComboPrice.setText(priceText);
    }

    private double calculateMainItemPrice() {
        if (isBurger) {
            // Use burger calculation method
            Burger burger = (Burger) selectedMainItem;
            double basePrice = 6.99;
            if (burger.isDoublePatty()) {
                basePrice += 2.50;
            }
            for (int i = 0; i < burger.getAddOns().size(); i++) {
                basePrice += burger.getAddOns().get(i).getPrice();
            }
            return basePrice * burger.getQuantity();
        } else {
            // For sandwich, use the item price from the Sandwich object
            Sandwich sandwich = (Sandwich) selectedMainItem;

            double basePrice = 10.99;

            if (sandwich.getProtein() == Protein.ROAST_BEEF) {
                basePrice = 10.99;
            }
            else if (sandwich.getProtein() == Protein.SALMON) {
                basePrice = 9.99;
            }
            else if (sandwich.getProtein() == Protein.CHICKEN) {
                basePrice = 8.99;
            }


            // Add price for add-ons
            for (int i = 0; i < sandwich.getAddOns().size(); i++) {
                basePrice += sandwich.getAddOns().get(i).getPrice();
            }

            return basePrice * sandwich.getQuantity();
        }
    }

    private double calculateComboPrice() {
        return calculateMainItemPrice() + COMBO_ADDON_PRICE;
    }

    private void addComboToOrder() {
        // Create a new combo item
        Item comboItem = new Item();
        double finalPrice = calculateComboPrice();

        // Make sure we don't add $0 combos
        if (finalPrice <= COMBO_ADDON_PRICE) {
            Toast.makeText(this, "Error: Invalid main item price", Toast.LENGTH_SHORT).show();
            return;
        }

        comboItem.setPrice(finalPrice);
        comboItem.setQuantity(1);

        // Add the combo item to the order
        currentOrder.addItem(comboItem);

        // Return result to previous Activity
        Intent resultIntent = new Intent();
        resultIntent.putExtra("combo_added", true);
        resultIntent.putExtra("order", currentOrder);
        setResult(RESULT_OK, resultIntent);

        Toast.makeText(this, "Combo added to order", Toast.LENGTH_SHORT).show();
        finish();
    }
}