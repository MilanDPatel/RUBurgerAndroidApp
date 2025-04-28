package com.example.recycleapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recycleapplication.model.AddOns;
import com.example.recycleapplication.model.Bread;
import com.example.recycleapplication.model.Combo;
import com.example.recycleapplication.model.Order;
import com.example.recycleapplication.model.Protein;
import com.example.recycleapplication.model.Sandwich;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * SandwichActivity allows users to customize and add a sandwich to their order.
 * Users can choose bread, protein, toppings (add-ons), quantity, and make a combo.
 * @author Aditya Shah
 */
public class SandwichActivity extends AppCompatActivity {

    private Spinner spinnerBread;
    private RadioGroup radioGroupProtein;
    private RadioButton radioRoastBeef, radioSalmon, radioChicken;
    private CheckBox checkLettuce, checkTomatoes, checkOnions, checkAvocado, checkCheese;
    private Button btnDecreaseQuantity, btnIncreaseQuantity, btnAddToOrder, btnCombo;
    private TextView tvQuantity, tvPrice;

    private Sandwich currentSandwich;
    private Combo combo;
    private Order currentOrder;
    private int quantity = 1;

    private final DecimalFormat df = new DecimalFormat("$#,##0.00");

    /**
     * The start of the sandwich activity.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sandwich);

        currentOrder = Order.getInstance();

        initializeComponents();

        setupListeners();

        resetSandwich();
    }

    /**
     * Initializes the components such as the spinner, radio buttons, and buttons.
     */
    private void initializeComponents() {
        spinnerBread = findViewById(R.id.spinnerBread);
        ArrayAdapter<Bread> breadAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, Bread.values());
        breadAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBread.setAdapter(breadAdapter);

        radioGroupProtein = findViewById(R.id.radioGroupProtein);
        radioRoastBeef = findViewById(R.id.radioRoastBeef);
        radioSalmon = findViewById(R.id.radioSalmon);
        radioChicken = findViewById(R.id.radioChicken);

        checkLettuce = findViewById(R.id.checkLettuce);
        checkTomatoes = findViewById(R.id.checkTomatoes);
        checkOnions = findViewById(R.id.checkOnions);
        checkAvocado = findViewById(R.id.checkAvocado);
        checkCheese = findViewById(R.id.checkCheese);

        btnDecreaseQuantity = findViewById(R.id.btnDecreaseQuantity);
        tvQuantity = findViewById(R.id.tvQuantity);
        btnIncreaseQuantity = findViewById(R.id.btnIncreaseQuantity);

        tvPrice = findViewById(R.id.tvPrice);

        btnAddToOrder = findViewById(R.id.btnAddToOrder);
        btnCombo = findViewById(R.id.btnCombo);
    }

    /**
     * Sets up the listeners for the spinner, radio buttons, and buttons.
     */
    private void setupListeners() {
        spinnerBread.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            /**
             * Updates price when item is selected.
             * @param parent The AdapterView where the selection happened
             * @param view The view within the AdapterView that was clicked
             * @param position The position of the view in the adapter
             * @param id The row id of the item that is selected
             */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updatePrice();
            }

            /**
             * Does nothing when no item is selected.
             * @param parent The AdapterView that now contains no selected item.
             */
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        radioGroupProtein.setOnCheckedChangeListener((group, checkedId) -> updatePrice());

        setupCheckBoxListeners();

        btnDecreaseQuantity.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                tvQuantity.setText(String.valueOf(quantity));
                updatePrice();
            }
        });

        btnIncreaseQuantity.setOnClickListener(v -> {
            if (quantity < 5) {
                quantity++;
                tvQuantity.setText(String.valueOf(quantity));
                updatePrice();
            }
        });

        btnAddToOrder.setOnClickListener(v -> addToOrder());
        btnCombo.setOnClickListener(v -> showComboOptions());
    }

    /**
     * Sets up the listeners for checkboxes for addons.
     */
    private void setupCheckBoxListeners() {
        CheckBox[] checkBoxes = {checkLettuce, checkTomatoes, checkOnions, checkAvocado, checkCheese};
        for (CheckBox checkBox : checkBoxes) {
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> updatePrice());
        }
    }

    /**
     * Updates the price text field with updated price.
     */
    private void updatePrice() {
        updateCurrentSandwich();
        double totalPrice = getSandwichPrice() * quantity;
        tvPrice.setText(df.format(totalPrice));
    }

    /**
     * Updates the sandwich the current selections.
     */
    private void updateCurrentSandwich() {
        currentSandwich.setBread((Bread) spinnerBread.getSelectedItem());

        if (radioRoastBeef.isChecked()) {
            currentSandwich.setProtein(Protein.ROAST_BEEF);
        } else if (radioSalmon.isChecked()) {
            currentSandwich.setProtein(Protein.SALMON);
        } else if (radioChicken.isChecked()) {
            currentSandwich.setProtein(Protein.CHICKEN);
        }

        currentSandwich.getAddOns().clear();
        if (checkLettuce.isChecked()) currentSandwich.getAddOns().add(AddOns.LETTUCE);
        if (checkTomatoes.isChecked()) currentSandwich.getAddOns().add(AddOns.TOMATOES);
        if (checkOnions.isChecked()) currentSandwich.getAddOns().add(AddOns.ONIONS);
        if (checkAvocado.isChecked()) currentSandwich.getAddOns().add(AddOns.AVOCADO);
        if (checkCheese.isChecked()) currentSandwich.getAddOns().add(AddOns.CHEESE);

        currentSandwich.setQuantity(quantity);
    }

    /**
     * Computes the price of the sandwich.
     * @return the price of the sandwich.
     */
    private double getSandwichPrice() {
        double basePrice = currentSandwich.getProtein().getPrice();
        for (AddOns addOn : currentSandwich.getAddOns()) {
            basePrice += addOn.getPrice();
        }
        return basePrice;
    }

    /**
     * Resets the current sandwich to default.
     */
    private void resetSandwich() {
        currentSandwich = new Sandwich();
        currentSandwich.setProtein(Protein.ROAST_BEEF);
        currentSandwich.setBread(Bread.BRIOCHE);
        currentSandwich.setQuantity(1);
        currentSandwich.getAddOns().clear();

        combo = null;
        quantity = 1;

        spinnerBread.setSelection(0);
        radioRoastBeef.setChecked(true);
        checkLettuce.setChecked(false);
        checkTomatoes.setChecked(false);
        checkOnions.setChecked(false);
        checkAvocado.setChecked(false);
        checkCheese.setChecked(false);
        tvQuantity.setText("1");

        updatePrice();
    }

    /**
     * Adds the sandwich to current order.
     */
    private void addToOrder() {
        updateCurrentSandwich();
        currentOrder.addItem(currentSandwich);

        Toast.makeText(this, "Sandwich added to order", Toast.LENGTH_SHORT).show();
        resetSandwich();
    }

    /**
     * Provides an option to create a combo and goes to combo activity if clicked.
     */
    private void showComboOptions() {
        updateCurrentSandwich();

        Intent intent = new Intent(this, ComboActivity.class);
        intent.putExtra("sandwich", currentSandwich);
        startActivity(intent);
    }

    /**
     * Creates popup message when combo is added to order.
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode The integer result code returned by the child activity
     *                   through its setResult().
     * @param data An Intent, which can return result data to the caller
     *               (various data can be attached to Intent "extras").
     *
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("combo_added")) {
                boolean comboAdded = data.getBooleanExtra("combo_added", false);
                if (comboAdded) {
                    resetSandwich();
                    Toast.makeText(this, "Combo added to order", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * Retrieves information about the sandwich in a string.
     * @return a string with information about the sandwich.
     */
    public String getAddOnsDescription() {
        StringBuilder addOnsDesc = new StringBuilder();

        if (currentSandwich.getAddOns().isEmpty()) {
            return "None";
        }

        for (AddOns addOn : currentSandwich.getAddOns()) {
            if (addOnsDesc.length() > 0) {
                addOnsDesc.append(", ");
            }
            addOnsDesc.append(addOn.toString());
        }

        return addOnsDesc.toString();
    }
}
