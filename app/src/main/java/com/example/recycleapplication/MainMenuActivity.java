package com.example.recycleapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.recycleapplication.model.Order;

public class MainMenuActivity extends AppCompatActivity {

    private Order currentOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        // Initialize order
        currentOrder = new Order();

        // Drinks button
        Button drinksButton = findViewById(R.id.btn_drinks); // Use your actual button ID
        drinksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, BeveragesActivity.class);
                startActivity(intent);
            }
        });

        // Sides button
        Button sidesButton = findViewById(R.id.btn_sides);
        sidesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, SidesActivity.class);
                startActivity(intent);
            }
        });

        // Burgers button
        Button burgersButton = findViewById(R.id.btn_burgers);
        burgersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, BurgerActivity.class);
                intent.putExtra("order", currentOrder);
                startActivity(intent);
            }
        });

        Button sandwichesButton = findViewById(R.id.btn_sandwiches);
        sandwichesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, SandwichActivity.class);
                intent.putExtra("order", currentOrder);
                startActivity(intent);
            }
        });

        Button viewOrderButton = findViewById(R.id.btn_view_order);
        viewOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, CurrentOrderActivity.class);
                intent.putExtra("order", currentOrder);
                startActivity(intent);
            }
        });


    }
}