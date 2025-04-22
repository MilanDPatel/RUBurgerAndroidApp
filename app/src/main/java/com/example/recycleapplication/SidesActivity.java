package com.example.recycleapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycleapplication.model.Item; // Import the correct Item class

import java.util.ArrayList;

public class SidesActivity extends AppCompatActivity {
    private static final String TAG = "SidesActivity";
    private ArrayList<Item> sideItems = new ArrayList<>();

    // Make sure these drawable resources exist in your res/drawable folder
    private int[] sideImages = {
            R.drawable.fries,
            R.drawable.chips,
            R.drawable.appleslices,
            R.drawable.onionring
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setContentView(R.layout.activity_sides);

            RecyclerView rcView = findViewById(R.id.rcView_sides);
            if (rcView == null) {
                Log.e(TAG, "RecyclerView not found in layout");
                Toast.makeText(this, "Layout error: RecyclerView not found", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }

            if (!setupSideItems()) {
                Log.e(TAG, "Failed to set up side items");
                Toast.makeText(this, "Error loading side items", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }

            ItemsAdapter adapter = new ItemsAdapter(this, sideItems);
            rcView.setAdapter(adapter);
            rcView.setLayoutManager(new LinearLayoutManager(this));

        } catch (Exception e) {
            Log.e(TAG, "Error in onCreate", e);
            Toast.makeText(this, "Error starting activity: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private boolean setupSideItems() {
        try {
            String[] sideNames = getResources().getStringArray(R.array.sideNames);
            String[] sidePrices = getResources().getStringArray(R.array.sidePrices);

            if (sideNames.length == 0) {
                Log.e(TAG, "sideNames array is empty");
                return false;
            }

            Log.d(TAG, "sideNames length: " + sideNames.length + ", sideImages length: " + sideImages.length);

            int count = Math.min(sideNames.length, sideImages.length);
            count = Math.min(count, sidePrices.length);

            for (int i = 0; i < count; i++) {
                // Parse the price string to a double
                double price = Double.parseDouble(sidePrices[i].replace("$", ""));

                // Create item using the model Item class constructor
                Item sideItem = new Item(sideNames[i], price);
                sideItem.setImageResourceId(sideImages[i]);

                sideItems.add(sideItem);
            }
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error setting up side items", e);
            return false;
        }
    }
}