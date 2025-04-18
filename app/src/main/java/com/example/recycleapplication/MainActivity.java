package com.example.recycleapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Main Activity that serves as a navigation hub to different menu categories
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find buttons to navigate to different menu categories
        Button beveragesButton = findViewById(R.id.btn_beverages);
        Button sidesButton = findViewById(R.id.btn_sides);

        // Set click listeners for navigation buttons
        beveragesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BeveragesActivity.class);
                startActivity(intent);
            }
        });

        sidesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SidesActivity.class);
                startActivity(intent);
            }
        });
    }
}