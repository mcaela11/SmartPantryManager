package com.example.smartpantrymanager;

import java.util.ArrayList;
import java.util.List;



public class Recipe {

    // Unique id for this recipe (comes from the database)
    private long id;
    // Name of the recipe
    private String name;
    // The cooking steps as one big String
    private String steps;
    // All the ingredients this recipe needs - starts as an empty list so it's never null
    private List<RecipeIngredient> ingredients = new ArrayList<>();

    // Constructor - sets the basic recipe info. Ingredients get added separately
    // (see addIngredient below) since they come from a separate database table.
    public Recipe(long id, String name, String steps) {
        this.id = id;
        this.name = name;
        this.steps = steps;
    }

    // Getters for the basic fields
    public long getId() { return id; }
    public String getName() { return name; }
    public String getSteps() { return steps; }

    // Getter/setter for the whole ingredient list at once
    // (setter is handy when loading ingredients from the database in one shot)
    public List<RecipeIngredient> getIngredients() { return ingredients; }
    public void setIngredients(List<RecipeIngredient> ingredients) { this.ingredients = ingredients; }

    // Convenience method - add ONE ingredient to the list without dealing with the whole list
    public void addIngredient(RecipeIngredient ingredient) { ingredients.add(ingredient); }
}