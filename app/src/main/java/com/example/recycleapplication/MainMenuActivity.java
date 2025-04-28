package com.example.recycleapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.recycleapplication.model.Order;

/**
 * MainMenuActivity class represents the main navigation view.
 * @author Aditya Shah
 */
public class MainMenuActivity extends AppCompatActivity {

    /**
     * The start of the activity.
     * Initializes the layout and buttons.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button drinksButton = findViewById(R.id.btn_drinks); // Use your actual button ID
        drinksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, BeveragesActivity.class);
                startActivity(intent);
            }
        });

        Button sidesButton = findViewById(R.id.btn_sides);
        sidesButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Starts sides view when button is clicked.
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, SidesActivity.class);
                startActivity(intent);
            }
        });

        Button burgersButton = findViewById(R.id.btn_burgers);
        burgersButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Starts burger view when button is clicked.
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, BurgerActivity.class);
                startActivity(intent);
            }
        });

        Button sandwichesButton = findViewById(R.id.btn_sandwiches);
        sandwichesButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Starts sandwich view when button is clicked.
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, SandwichActivity.class);
                startActivity(intent);
            }
        });

        Button viewOrderButton = findViewById(R.id.btn_view_order);
        viewOrderButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Starts view order when button is clicked.
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, CurrentOrderActivity.class);
                startActivity(intent);
            }
        });

        Button viewOrdersButton = findViewById(R.id.btn_view_all_orders);
        viewOrdersButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Starts view all orders when button is clicked.
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, AllOrdersActivity.class);
                startActivity(intent);
            }
        });


    }
}