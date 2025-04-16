package com.example.recycleapplication;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SidesActivity extends AppCompatActivity {
    private static final String TAG = "SidesActivity";
    private ArrayList<Item> sideItems = new ArrayList<>();

    // Make sure these drawable resources exist in your res/drawable folder
    private int[] sideImages = {
            R.drawable.banana,
            R.drawable.grapes,
            R.drawable.mango
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
            if (sideNames.length == 0) {
                Log.e(TAG, "sideNames array is empty");
                return false;
            }

            Log.d(TAG, "sideNames length: " + sideNames.length + ", sideImages length: " + sideImages.length);

            int count = Math.min(sideNames.length, sideImages.length);
            for (int i = 0; i < count; i++) {
                // Instead of directly passing the resource ID, we'll create a version of the Item class
                // that accepts a scaled-down version of the image resource ID
                sideItems.add(new Item(sideNames[i], sideImages[i], "$2.99"));
            }
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error setting up side items", e);
            return false;
        }
    }
}