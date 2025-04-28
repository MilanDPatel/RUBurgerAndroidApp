package com.example.recycleapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

/**
 * Activity that displays a list of beverage items in a RecyclerView.
 * Loads beverage names, prices, and images from resources and displays them.
 * Author: Milan Patel
 */

public class BeveragesActivity extends AppCompatActivity {
    private static final String TAG = "BeveragesActivity";
    private ArrayList<com.example.recycleapplication.model.Item> beverageItems = new ArrayList<>();


    private int[] beverageImages = {
            R.drawable.cola,
            R.drawable.tea,
            R.drawable.juice,
            R.drawable.lemonade,
            R.drawable.orangejuice,
            R.drawable.sprite,
            R.drawable.rootbeer,
            R.drawable.icedtea,
            R.drawable.coffee,
            R.drawable.hotchocolate,
            R.drawable.strawberry,
            R.drawable.grape,
            R.drawable.cherry,
            R.drawable.vanilla,
            R.drawable.chocolate
    };

    /**
     * Called when the activity is starting.
     * @param savedInstanceState If the activity is being
     * re-initialized after previously being shut down,
     * this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setContentView(R.layout.activity_beverages);

            RecyclerView rcView = findViewById(R.id.rcView_beverages);
            if (rcView == null) {
                Log.e(TAG, "RecyclerView not found in layout");
                Toast.makeText(this, "Layout error: RecyclerView not found", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }

            if (!setupBeverageItems()) {
                Log.e(TAG, "Failed to set up beverage items");
                Toast.makeText(this, "Error loading beverage items", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }

            ItemsAdapter adapter = new ItemsAdapter(this, beverageItems);
            rcView.setAdapter(adapter);
            rcView.setLayoutManager(new LinearLayoutManager(this));

        } catch (Exception e) {
            Log.e(TAG, "Error in onCreate", e);
            Toast.makeText(this, "Error starting activity: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    /**
     * Sets up the list of beverage items by loading names, prices, and images.
     * @return true if items were successfully set up; false otherwise.
     */
    private boolean setupBeverageItems() {
        try {
            String[] beverageNames = getResources().getStringArray(R.array.beverageNames);
            String[] beveragePrices = getResources().getStringArray(R.array.beveragePrices);

            if (beverageNames.length == 0) {
                Log.e(TAG, "beverageNames array is empty");
                return false;
            }

            Log.d(TAG, "beverageNames length: " + beverageNames.length + ", beverageImages length: " + beverageImages.length);

            int count = Math.min(beverageNames.length, beverageImages.length);
            count = Math.min(count, beveragePrices.length);

            for (int i = 0; i < count; i++) {
                // Create a beverage Item using your model class
                double price = Double.parseDouble(beveragePrices[i].replace("$", ""));
                com.example.recycleapplication.model.Item beverage =
                        new com.example.recycleapplication.model.Item(beverageNames[i], price);
                beverage.setImageResourceId(beverageImages[i]);
                beverageItems.add(beverage);
            }
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error setting up beverage items", e);
            return false;
        }
    }
}