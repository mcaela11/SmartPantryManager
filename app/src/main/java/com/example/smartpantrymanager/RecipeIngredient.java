package com.example.smartpantrymanager;


public class RecipeIngredient {

    // Unique id for this ingredient row (comes from the database)
    private long id;
    // The id of the recipe this ingredient belongs to - links ingredients to their recipe
    private long recipeId;
    // Name of the ingredient, e.g. "Flour", "Sugar"
    private String name;
    // How much of it the recipe needs - double so 1.5 cups etc works
    private double quantity;
    // Unit of measurement, e.g. "cups", "g", "tbsp"
    private String unit;

    // Full constructor - creates an ingredient with everything set in one go
    public RecipeIngredient(long id, long recipeId, String name, double quantity, String unit) {
        this.id = id;
        this.recipeId = recipeId;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    // Getters only (no setters here) - this object is basically read-only once created.
    // Useful so nothing accidentally changes an ingredient after it's loaded.
    public long getId() { return id; }
    public long getRecipeId() { return recipeId; }
    public String getName() { return name; }
    public double getQuantity() { return quantity; }
    public String getUnit() { return unit; }
}