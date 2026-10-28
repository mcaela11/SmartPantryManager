package com.example.smartpantrymanager;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/** Add/Edit screen: creates, updates or deletes a pantry item, with input validation. */
public class AddEditItemActivity extends AppCompatActivity {

    // Key used to pass the item's id from the list screen via an Intent
    public static final String EXTRA_ITEM_ID = "item_id";

    private static final String[] UNITS = {"g", "kg", "ml", "l", "pcs"};

    private DatabaseHelper dbHelper;
    private EditText editName, editQuantity, editExpiry;
    private Spinner spinnerUnit;
    private long itemId = -1; // -1 means "adding a new item"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_edit_item);

        int pad = (int) (16 * getResources().getDisplayMetrics().density);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.addEditRoot), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left + pad, bars.top + pad, bars.right + pad, bars.bottom + pad);
            return insets;
        });

        dbHelper = new DatabaseHelper(this);
        editName = findViewById(R.id.editName);
        editQuantity = findViewById(R.id.editQuantity);
        editExpiry = findViewById(R.id.editExpiry);
        spinnerUnit = findViewById(R.id.spinnerUnit);
        TextView textTitle = findViewById(R.id.textTitle);
        Button buttonSave = findViewById(R.id.buttonSave);
        Button buttonCancel = findViewById(R.id.buttonCancel);
        Button buttonDelete = findViewById(R.id.buttonDelete);

        spinnerUnit.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, UNITS));

        // If an id was passed in the Intent, we are editing an existing item
        itemId = getIntent().getLongExtra(EXTRA_ITEM_ID, -1);
        if (itemId != -1) {
            textTitle.setText("Edit Ingredient");
            loadItem();
            buttonDelete.setVisibility(View.VISIBLE); // only shown when editing
            buttonDelete.setOnClickListener(v -> confirmDelete());
        }

        buttonSave.setOnClickListener(v -> saveItem());
        buttonCancel.setOnClickListener(v -> finish());
    }

    // Fills the form with the existing item's values
    private void loadItem() {
        PantryItem item = dbHelper.getPantryItem(itemId);
        if (item == null) {
            finish();
            return;
        }
        editName.setText(item.getName());
        double q = item.getQuantity();
        editQuantity.setText(q == Math.rint(q) ? String.valueOf((long) q) : String.valueOf(q));
        if (item.getExpiryDate() != null) {
            editExpiry.setText(item.getExpiryDate());
        }
        for (int i = 0; i < UNITS.length; i++) {
            if (UNITS[i].equals(item.getUnit())) {
                spinnerUnit.setSelection(i);
                break;
            }
        }
    }

    // Asks for confirmation, then deletes the item from the database
    private void confirmDelete() {
        new AlertDialog.Builder(this)
                .setTitle("Delete ingredient")
                .setMessage("Are you sure you want to delete this ingredient?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    dbHelper.deletePantryItem(itemId);
                    Toast.makeText(this, "Ingredient deleted", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    // Validates the input, then creates or updates the item
    private void saveItem() {
        String name = editName.getText().toString().trim();
        String quantityText = editQuantity.getText().toString().trim();
        String expiry = editExpiry.getText().toString().trim();
        String unit = spinnerUnit.getSelectedItem().toString();

        if (name.isEmpty()) {
            editName.setError("Name is required");
            return;
        }

        double quantity;
        try {
            quantity = Double.parseDouble(quantityText);
        } catch (NumberFormatException e) {
            editQuantity.setError("Enter a valid number");
            return;
        }
        if (quantity <= 0) {
            editQuantity.setError("Quantity must be greater than 0");
            return;
        }

        if (!expiry.isEmpty() && (!expiry.matches("\\d{4}-\\d{2}-\\d{2}") || !isValidDate(expiry))) {
            editExpiry.setError("Enter a real date in the format yyyy-MM-dd");
            return;
        }

        String expiryValue = expiry.isEmpty() ? null : expiry;

        if (itemId == -1) {
            dbHelper.addPantryItem(new PantryItem(0, name, quantity, unit, expiryValue));
            Toast.makeText(this, "Ingredient added", Toast.LENGTH_SHORT).show();
        } else {
            dbHelper.updatePantryItem(new PantryItem(itemId, name, quantity, unit, expiryValue));
            Toast.makeText(this, "Ingredient updated", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    // Checks the date really exists (rejects 2026-99-99 or 2026-02-30)
    private boolean isValidDate(String text) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            format.setLenient(false);
            format.parse(text);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}