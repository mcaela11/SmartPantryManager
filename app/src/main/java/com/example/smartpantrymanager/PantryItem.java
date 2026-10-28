// Package declaration - this file lives in the com.example.smartpantrymanager package
package com.example.smartpantrymanager;

// Simple model class that represents ONE pantry item (one ingredient in the pantry).
// Basically just a container for data - no logic, just fields + getters/setters.
public class PantryItem {

    // Unique id for this item (comes from the database when we save it)
    private long id;
    // Name of the ingredient, e.g. "Milk", "Eggs"
    private String name;
    // How much we have - double so we can do things like 1.5 kg
    private double quantity;
    // Unit of measurement, e.g. "kg", "g", "liters", "pcs"
    private String unit;
    // Expiry date stored as a String (we just keep it as text for simplicity)
    private String expiryDate;

    // Empty constructor - required by some libraries (like database/JSON stuff)
    // so they can create the object first then fill in the fields.
    public PantryItem() {}

    // Full constructor - easiest way to create an item in one line with all values set
    public PantryItem(long id, String name, double quantity, String unit, String expiryDate) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.expiryDate = expiryDate;
    }

    // Getters and setters - standard way to read/change the fields from outside this class
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getQuantity() { return quantity; }
    public void setQuantity(double quantity) { this.quantity = quantity; }
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
    public String getExpiryDate() { return expiryDate; }
    public void setExpiryDate(String expiryDate) { this.expiryDate = expiryDate; }
}