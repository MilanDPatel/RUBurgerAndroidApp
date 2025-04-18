package com.example.recycleapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BeveragesActivity extends AppCompatActivity {
    private static final String TAG = "BeveragesActivity";
    private ArrayList<Item> beverageItems = new ArrayList<>();

    // Images for beverage items
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
                beverageItems.add(new Item(beverageNames[i], beverageImages[i], beveragePrices[i]));
            }
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error setting up beverage items", e);
            return false;
        }
    }
}
