package com.example.project5;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project5.model.Item; // Import the correct Item class
import com.example.project5.R;

import java.util.ArrayList;

/**
 * SidesActivity class allows users to selects sides from a list of side items.
 * @author Aditya Shah
 */
public class SidesActivity extends AppCompatActivity {
    private static final String TAG = "SidesActivity";
    private ArrayList<Item> sideItems = new ArrayList<>();

    private int[] sideImages = {
            R.drawable.fries,
            R.drawable.chips,
            R.drawable.appleslices,
            R.drawable.onionring
    };

    /**
     * Start of the SidesActivity.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
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

    /**
     * Sets up th list of side options along with their images, names, and prices.
     * @return true if set up is successful, false otherwise.
     */
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
                double price = Double.parseDouble(sidePrices[i].replace("$", ""));

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