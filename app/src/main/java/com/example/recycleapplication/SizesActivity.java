package com.example.recycleapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    private double basePrice = 2.99;
    private double currentPrice = 2.99;

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
        basePrice = getIntent().getDoubleExtra("base_price", 2.99);
        currentPrice = basePrice;

        // Set initial values
        itemNameText.setText(itemName);
        itemImageView.setImageResource(imageResId);
        updateTotalPrice();

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
                        currentPrice = basePrice * 1.5;
                        break;
                    case "Large":
                        currentPrice = basePrice * 2;
                        break;
                }
                updateTotalPrice();
            }
        });

        // Set up quantity buttons
        minusButton.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                quantityText.setText(String.valueOf(quantity));
                updateTotalPrice();
            }
        });

        plusButton.setOnClickListener(v -> {
            quantity++;
            quantityText.setText(String.valueOf(quantity));
            updateTotalPrice();
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

    private void updateTotalPrice() {
        double total = currentPrice * quantity;
        totalPriceText.setText(String.format("$%.2f", total));
    }
}

