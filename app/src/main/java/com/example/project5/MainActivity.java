package com.example.project5;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.project5.R;

/**
 * MainActivity class represents a navigation hub to different menu categories.
 * @author Aditya Shah
 */
public class MainActivity extends AppCompatActivity {

    /**
     * The start of the activity.
     * Sets up layout and buttons.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button beveragesButton = findViewById(R.id.btn_beverages);
        Button sidesButton = findViewById(R.id.btn_sides);

        beveragesButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Starts beverages view when button is clicked.
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BeveragesActivity.class);
                startActivity(intent);
            }
        });

        sidesButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Starts sides view when button is clicked.
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SidesActivity.class);
                startActivity(intent);
            }
        });
    }
}