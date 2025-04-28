package com.example.recycleapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

/**
 * ItemSelectedActivity class that displays the name of selected item.
 * @author Aditya Shah
 */
public class ItemSelectedActivity extends AppCompatActivity {
    private Button btn_itemName;

    /**
     * Start of the activity.
     * Sets up the layout and button.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_selected);
        btn_itemName = findViewById(R.id.btn1);
        Intent intent = getIntent();
        btn_itemName.setText(intent.getStringExtra("ITEM"));
    }
}
