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

/**
 * ComboActivity allows users to add a combo to their order.
 * Users can choose a drink and side with their burger/sandwich.
 * Author: Milan Patel
 */

public class ComboActivity extends AppCompatActivity {

    private Spinner spinnerSide;
    private Spinner spinnerDrink;
    private Button btnAddComboToOrder;
    private Button btnGoBack;
    private TextView tvMainItemDetails;
    private TextView tvComboPrice;

    private Item selectedMainItem;
    private Item selectedSide;
    private Item selectedDrink;
    private Combo currentCombo;
    private Order currentOrder;
    private boolean isBurger;

    private List<String> sideNames;
    private List<String> drinkNames;
    private Map<String, Double> sidePrices;
    private Map<String, Double> drinkPrices;


    private static final double COMBO_ADDON_PRICE = 2.00;


    private final DecimalFormat df = new DecimalFormat("$#,##0.00");

    /**
     * Initializes the activity, processes data passed from the previous activity,
     * initializes combo, loads item data, sets up spinners, and updates the UI.
     * @param savedInstanceState the current state of data
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combo);

        if (getIntent().hasExtra("burger")) {
            selectedMainItem = (Burger) getIntent().getSerializableExtra("burger");
            isBurger = true;
        } else if (getIntent().hasExtra("sandwich")) {
            selectedMainItem = (Sandwich) getIntent().getSerializableExtra("sandwich");
            isBurger = false;
        } else {
            Toast.makeText(this, "Error: Main item data missing", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        currentOrder = Order.getInstance();
        currentCombo = new Combo();
        currentCombo.setSandwich(selectedMainItem);
        initializeComponents();
        loadItemData();

        setupSpinners();

        displayMainItemDetails();

        if (!sideNames.isEmpty()) {
            selectedSide = createSideItem(sideNames.get(0));
            currentCombo.setSide(selectedSide);
        }

        if (!drinkNames.isEmpty()) {
            selectedDrink = createDrinkItem(drinkNames.get(0));
            currentCombo.setDrink(selectedDrink);
        }

        updateComboPrice();
    }

    /**
     * Initializes UI components such as spinners, buttons, and text views.
     * Also sets up button click listeners.
     */
    private void initializeComponents() {
        spinnerSide = findViewById(R.id.spinnerSide);
        spinnerDrink = findViewById(R.id.spinnerDrink);
        btnAddComboToOrder = findViewById(R.id.btnAddComboToOrder);
        btnGoBack = findViewById(R.id.btnGoBack);
        tvMainItemDetails = findViewById(R.id.tvBurgerDetails); // Keep using the existing ID
        tvComboPrice = findViewById(R.id.tvComboPrice);

        btnAddComboToOrder.setOnClickListener(v -> addComboToOrder());
        btnGoBack.setOnClickListener(v -> finish());
    }

    /**
     * Loads the available items for sides and drinks, including their prices.
     */
    private void loadItemData() {
        sideNames = new ArrayList<>();
        sidePrices = new HashMap<>();

        sideNames.add("Chips (Small)");
        sideNames.add("Apple (Small)");

        sidePrices.put("Chips (Small)", 0.0);
        sidePrices.put("Apple (Small)", 0.0);

        drinkNames = new ArrayList<>();
        drinkPrices = new HashMap<>();

        drinkNames.add("Cola (Medium)");
        drinkNames.add("Tea (Medium)");
        drinkNames.add("Juice (Medium)");

        drinkPrices.put("Cola (Medium)", 0.0);
        drinkPrices.put("Tea (Medium)", 0.0);
        drinkPrices.put("Juice (Medium)", 0.0);
    }

    /**
     * Creates an Item object representing a side based on the side name.
     * @param sideName The name of the selected side.
     * @return The created Item representing the side.
     */
    private Item createSideItem(String sideName) {
        Item item = new Item();
        item.setItemName(sideName);
        item.setPrice(sidePrices.get(sideName));
        item.setQuantity(1);
        return item;
    }

    /**
     * Creates an Item object representing a drink based on the drink name.
     * @param drinkName The name of the selected drink.
     * @return The created Item representing the drink.
     */
    private Item createDrinkItem(String drinkName) {
        Item item = new Item();
        item.setItemName(drinkName);
        item.setPrice(drinkPrices.get(drinkName));
        item.setQuantity(1);
        return item;
    }

    /**
     * Sets up the spinners for selecting sides and drinks, with listeners for item selection.
     */
    private void setupSpinners() {
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
            }
        });
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
            }
        });
    }

    /**
     * Displays the details of the selected main item (burger or sandwich).
     */
    private void displayMainItemDetails() {
        StringBuilder details = new StringBuilder();
        if (isBurger) {
            Burger burger = (Burger) selectedMainItem;
            details.append("Selected Burger(s):\n");
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
            details.append("Quantity: ").append(burger.getQuantity()).append("\n");
            details.append("Price: ").append(df.format(calculateMainItemPrice() * burger.getQuantity())).append("\n");
        } else {
            Sandwich sandwich = (Sandwich) selectedMainItem;
            details.append("Selected Sandwich(s):\n");
            details.append(sandwich.getBread().toString()).append(" with ");
            details.append(sandwich.getProtein().toString()).append("\n");
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
            details.append("Quantity: ").append(sandwich.getQuantity()).append("\n");
            details.append("Price: ").append(df.format(calculateMainItemPrice() * sandwich.getQuantity())).append("\n");
        }
        tvMainItemDetails.setText(details.toString());
    }

    /**
     * Updates the displayed combo price, including the base price and combo addon price.
     */
    private void updateComboPrice() {
        double mainItemPrice = calculateMainItemPrice();
        double comboPrice = mainItemPrice + COMBO_ADDON_PRICE;

        // Update price display
        String priceText = (isBurger ? "Burger" : "Sandwich") + " Price: " + df.format(mainItemPrice) + "\n" +
                "Combo Addition: " + df.format(COMBO_ADDON_PRICE) + "\n" +
                "Total Combo Price: " + df.format(comboPrice);

        tvComboPrice.setText(priceText);
    }

    /**
     * Calculates the price of the main item (burger or sandwich).
     * @return The calculated price of the main item.
     */
    private double calculateMainItemPrice() {
        if (isBurger) {
            Burger burger = (Burger) selectedMainItem;
            double basePrice = 6.99;
            if (burger.isDoublePatty()) {
                basePrice += 2.50;
            }
            for (int i = 0; i < burger.getAddOns().size(); i++) {
                basePrice += burger.getAddOns().get(i).getPrice();
            }
            return basePrice;
        } else {
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

            for (int i = 0; i < sandwich.getAddOns().size(); i++) {
                basePrice += sandwich.getAddOns().get(i).getPrice();
            }
            return basePrice;
        }
    }

    /**
     * Calculates the total combo price, including the main item price and combo addon price.
     * @return The calculated combo price.
     */
    private double calculateComboPrice() {
        return calculateMainItemPrice() + COMBO_ADDON_PRICE;
    }

    /**
     * Adds the current combo to the order and returns a result to the previous activity.
     * Displays a toast message indicating the combo was added.
     */
    private void addComboToOrder() {
        Item comboItem = new Item();
        double finalPrice = calculateComboPrice();

        if (finalPrice <= COMBO_ADDON_PRICE) {
            Toast.makeText(this, "Error: Invalid main item price", Toast.LENGTH_SHORT).show();
            return;
        }

        String name = "Combo " + selectedMainItem.desc() + " " + selectedSide.getItemName() + " " + selectedDrink.getItemName();
        comboItem.setItemName(name);
        comboItem.setPrice(finalPrice);
        comboItem.setQuantity(selectedMainItem.getQuantity());

        currentOrder.addItem(comboItem);

        Intent resultIntent = new Intent();
        resultIntent.putExtra("combo_added", true);
        setResult(RESULT_OK, resultIntent);

        Toast.makeText(this, "Combo added to order", Toast.LENGTH_SHORT).show();
        finish();
    }
}