package com.example.recycleapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.recycleapplication.model.Item;
import com.example.recycleapplication.model.Order;

/**
 * SizesActivity class allows the user to pick a size for their side or beverage.
 * @author Aditya Shah
 */
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
    private String itemName;
    private int imageResId;

    /**
     * The start of the SizesActivity.
     * Sets up listeners for the radio buttons and buttons.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sizes);

        itemNameText = findViewById(R.id.item_name);
        itemImageView = findViewById(R.id.item_image);
        sizeRadioGroup = findViewById(R.id.size_radio_group);
        quantityText = findViewById(R.id.quantity_text);
        totalPriceText = findViewById(R.id.total_price);
        Button minusButton = findViewById(R.id.minus_button);
        Button plusButton = findViewById(R.id.plus_button);
        Button addToCartButton = findViewById(R.id.add_to_cart_button);

        itemName = getIntent().getStringExtra("item_name");
        imageResId = getIntent().getIntExtra("image_res_id", 0);
        basePrice = getIntent().getDoubleExtra("base_price", 1.99);
        currentPrice = basePrice;

        updateTotalPrice();

        float mediumAdjustment = Float.parseFloat(getResources().getString(R.string.medium_price_adjustment));
        float largeAdjustment = Float.parseFloat(getResources().getString(R.string.large_price_adjustment));

        itemNameText.setText(itemName);
        if (imageResId != 0) {
            itemImageView.setImageResource(imageResId);
        }


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
                updateTotalPrice();
            }
        });


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


        addToCartButton.setOnClickListener(v -> {
            RadioButton selectedRadioButton = findViewById(sizeRadioGroup.getCheckedRadioButtonId());
            String selectedSize = selectedRadioButton != null ? selectedRadioButton.getText().toString() : "Small";


            Item item = new Item();
            item.setItemName(selectedSize + " " + itemName);
            item.setPrice(currentPrice);
            item.setQuantity(quantity);
            item.setImageResourceId(imageResId);

            Order currentOrder = Order.getInstance();

            currentOrder.addItem(item);


            String message = "Added to cart: " + quantity + " " + selectedSize + " " + itemName;
            Toast.makeText(SizesActivity.this, message, Toast.LENGTH_SHORT).show();

            finish();
        });
    }

    /**
     * Computes the total price of the item and updates the total price text field.
     */
    private void updateTotalPrice() {
        double total = currentPrice * quantity;
        totalPriceText.setText(String.format("$%.2f", total));
    }
}