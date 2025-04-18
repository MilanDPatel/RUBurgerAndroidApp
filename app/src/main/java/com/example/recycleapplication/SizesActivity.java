package com.example.recycleapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SizesActivity extends AppCompatActivity {
    private static final String TAG = "SizesActivity";

    private TextView itemNameText;
    private ImageView itemImageView;
    private RadioGroup sizeRadioGroup;
    private TextView quantityText;
    private TextView totalPriceText;
    private int quantity = 1;
    private double basePrice = 1.99;
    private double currentPrice = 1.99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sizes);

        // Initialize views
        itemNameText = findViewById(R.id.item_name);
        itemImageView = findViewById(R.id.item_image);
        sizeRadioGroup = findViewById(R.id.size_radio_group);
        quantityText = findViewById(R.id.quantity_text);
        totalPriceText = findViewById(R.id.total_price);
        Button minusButton = findViewById(R.id.minus_button);
        Button plusButton = findViewById(R.id.plus_button);
        Button addToCartButton = findViewById(R.id.add_to_cart_button);

        // Get data from intent
        String itemName = getIntent().getStringExtra("item_name");
        int imageResId = getIntent().getIntExtra("image_res_id", 0);
        basePrice = getIntent().getDoubleExtra("base_price", 1.99);
        currentPrice = basePrice;

        // Set initial price
        updateTotalPrice();

        // Get price adjustments from resources
        float mediumAdjustment = Float.parseFloat(getResources().getString(R.string.medium_price_adjustment));
        float largeAdjustment = Float.parseFloat(getResources().getString(R.string.large_price_adjustment));

        // Set initial item name and image
        itemNameText.setText(itemName);
        if (imageResId != 0) {
            itemImageView.setImageResource(imageResId);
        }

        // Set up radio group listener
        sizeRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selectedRadioButton = findViewById(checkedId);
            if (selectedRadioButton != null) {
                String size = selectedRadioButton.getText().toString();
                switch (size) {
                    case "Small":
                        currentPrice = basePrice;
                        break;
                    case "Medium":
                        currentPrice = basePrice + mediumAdjustment;
                        break;
                    case "Large":
                        currentPrice = basePrice + largeAdjustment;
                        break;
                }
                updateTotalPrice(); // Update price when size changes
            }
        });

        // Set up quantity buttons
        minusButton.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                quantityText.setText(String.valueOf(quantity));
                updateTotalPrice(); // Update price when quantity decreases
            }
        });

        plusButton.setOnClickListener(v -> {
            quantity++;
            quantityText.setText(String.valueOf(quantity));
            updateTotalPrice(); // Update price when quantity increases
        });

        // Set up add to cart button
        addToCartButton.setOnClickListener(v -> {
            RadioButton selectedRadioButton = findViewById(sizeRadioGroup.getCheckedRadioButtonId());
            String selectedSize = selectedRadioButton != null ? selectedRadioButton.getText().toString() : "Small";

            String message = "Added to cart: " + quantity + " " + selectedSize + " " + itemName;
            Toast.makeText(SizesActivity.this, message, Toast.LENGTH_SHORT).show();

            // Here you would typically save the item to a cart data structure
            // For now, we'll just finish the activity
            finish();
        });
    }

    // Method to update the total price based on quantity and size
    private void updateTotalPrice() {
        double total = currentPrice * quantity;
        totalPriceText.setText(String.format("$%.2f", total)); // Update price on UI
    }
}
