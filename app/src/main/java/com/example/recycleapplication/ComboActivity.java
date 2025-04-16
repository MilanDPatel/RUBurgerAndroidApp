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

import com.example.recycleapplication.model.Burger;
import com.example.recycleapplication.model.Combo;
import com.example.recycleapplication.model.Item;
import com.example.recycleapplication.model.Order;

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
    private TextView tvBurgerDetails;
    private TextView tvComboPrice;

    // Model objects
    private Burger selectedBurger;
    private Item selectedSide;
    private Item selectedDrink;
    private Combo currentCombo;
    private Order currentOrder;

    // Data
    private List<String> sideNames;
    private List<String> drinkNames;
    private Map<String, Double> sidePrices;
    private Map<String, Double> drinkPrices;

    // Formatter for price display
    private final DecimalFormat df = new DecimalFormat("$#,##0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combo);

        // Get data passed from BurgerActivity
        if (getIntent().hasExtra("burger") && getIntent().hasExtra("order")) {
            selectedBurger = (Burger) getIntent().getSerializableExtra("burger");
            currentOrder = (Order) getIntent().getSerializableExtra("order");
        } else {
            Toast.makeText(this, "Error: Burger data missing", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Initialize combo
        currentCombo = new Combo();
        currentCombo.setSandwich(selectedBurger);

        // Initialize UI components
        initializeComponents();

        // Load data for sides and drinks
        loadItemData();

        // Set up spinners
        setupSpinners();

        // Display burger details
        displayBurgerDetails();

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
        tvBurgerDetails = findViewById(R.id.tvBurgerDetails);
        tvComboPrice = findViewById(R.id.tvComboPrice);

        // Button listeners
        btnAddComboToOrder.setOnClickListener(v -> addComboToOrder());
        btnGoBack.setOnClickListener(v -> finish());
    }

    private void loadItemData() {
        // Initialize side items data
        sideNames = new ArrayList<>();
        sidePrices = new HashMap<>();

        sideNames.add("French Fries");
        sideNames.add("Onion Rings");
        sideNames.add("Side Salad");
        sideNames.add("Coleslaw");

        sidePrices.put("French Fries", 2.99);
        sidePrices.put("Onion Rings", 3.49);
        sidePrices.put("Side Salad", 3.99);
        sidePrices.put("Coleslaw", 2.49);

        // Initialize drink items data
        drinkNames = new ArrayList<>();
        drinkPrices = new HashMap<>();

        drinkNames.add("Soda");
        drinkNames.add("Water");
        drinkNames.add("Iced Tea");
        drinkNames.add("Lemonade");
        drinkNames.add("Coffee");

        drinkPrices.put("Soda", 1.99);
        drinkPrices.put("Water", 1.49);
        drinkPrices.put("Iced Tea", 2.29);
        drinkPrices.put("Lemonade", 2.49);
        drinkPrices.put("Coffee", 1.79);
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
        List<String> sideDisplayItems = new ArrayList<>();
        for (String sideName : sideNames) {
            sideDisplayItems.add(sideName + " - " + df.format(sidePrices.get(sideName)));
        }

        ArrayAdapter<String> sideAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                sideDisplayItems
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
        List<String> drinkDisplayItems = new ArrayList<>();
        for (String drinkName : drinkNames) {
            drinkDisplayItems.add(drinkName + " - " + df.format(drinkPrices.get(drinkName)));
        }

        ArrayAdapter<String> drinkAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                drinkDisplayItems
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

    private void displayBurgerDetails() {
        StringBuilder details = new StringBuilder("Selected Burger:\n");

        // Add burger type details
        details.append(selectedBurger.getBread().toString()).append(" bun with ");
        details.append(selectedBurger.isDoublePatty() ? "double" : "single").append(" patty\n");

        // Add toppings
        if (!selectedBurger.getAddOns().isEmpty()) {
            details.append("Toppings: ");
            for (int i = 0; i < selectedBurger.getAddOns().size(); i++) {
                details.append(selectedBurger.getAddOns().get(i).toString());
                if (i < selectedBurger.getAddOns().size() - 1) {
                    details.append(", ");
                }
            }
            details.append("\n");
        }

        // Add quantity and price
        details.append("Quantity: ").append(selectedBurger.getQuantity()).append("\n");

        tvBurgerDetails.setText(details.toString());
    }

    private void updateComboPrice() {
        double burgerPrice = calculateBurgerPrice();
        double sidePrice = (selectedSide != null) ? selectedSide.getPrice() : 0;
        double drinkPrice = (selectedDrink != null) ? selectedDrink.getPrice() : 0;

        double totalIndividualPrice = burgerPrice + sidePrice + drinkPrice;
        // Apply combo discount (10% off)
        double comboDiscount = 0.1;
        double discountedPrice = totalIndividualPrice * (1 - comboDiscount);

        // Calculate savings
        double savings = totalIndividualPrice - discountedPrice;

        // Update price display
        String priceText = "Individual Items: " + df.format(totalIndividualPrice) + "\n" +
                "Combo Price: " + df.format(discountedPrice) + "\n" +
                "You Save: " + df.format(savings);

        tvComboPrice.setText(priceText);
    }

    private double calculateBurgerPrice() {
        double basePrice = 6.99;
        if (selectedBurger.isDoublePatty()) {
            basePrice += 2.50;
        }
        for (int i = 0; i < selectedBurger.getAddOns().size(); i++) {
            basePrice += selectedBurger.getAddOns().get(i).getPrice();
        }
        return basePrice * selectedBurger.getQuantity();
    }

    private void addComboToOrder() {
        // Ensure all combo items are selected
        if (selectedSide == null || selectedDrink == null) {
            Toast.makeText(this, "Please select both a side and a drink", Toast.LENGTH_SHORT).show();
            return;
        }

        // Since Order.addItem expects an Item, we'll add the
        // burger, side, and drink individually to the order
        // instead of adding the combo directly.
        currentOrder.addItem(selectedBurger);
        currentOrder.addItem(selectedSide);
        currentOrder.addItem(selectedDrink);

        // Return result to BurgerActivity
        Intent resultIntent = new Intent();
        resultIntent.putExtra("combo_added", true);
        resultIntent.putExtra("order", currentOrder);
        setResult(RESULT_OK, resultIntent);

        Toast.makeText(this, "Combo added to order", Toast.LENGTH_SHORT).show();
        finish();
    }
}