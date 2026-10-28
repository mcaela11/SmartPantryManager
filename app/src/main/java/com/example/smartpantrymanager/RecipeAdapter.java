package com.example.smartpantrymanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

// A parameter should never be null
import androidx.annotation.NonNull;
// RecyclerView = the list widget we're adapting data into
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

// Adapter that takes a List<Recipe> and turns each one into a row in the RecyclerView.
// RecyclerView only creates a few row views and recycles them as you scroll
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    // lets the adapter tell whoever is listening
    // Keeps the adapter from being  too dependent from fragmnets and activities
    public interface OnRecipeClickListener {
        void onRecipeClick(Recipe recipe);
    }

    // The data we're displaying
    private final List<Recipe> recipes;
    // The listener to notify when a row is tapped
    private final OnRecipeClickListener listener;


    public RecipeAdapter(List<Recipe> recipes, OnRecipeClickListener listener) {
        this.recipes = recipes;
        this.listener = listener;
    }

    // Called when RecyclerView needs a NEW row view (only happens a few times, not for every item).
    // We inflate the item_recipe.xml layout and wrap it in a ViewHolder.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe, parent, false);
        // false = don't attach to parent yet, RecyclerView will handle that
        return new ViewHolder(view);
    }

    // Called every time a row becomes visible - here we fill the row with the recipe's data.

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Grab the recipe for this position
        Recipe recipe = recipes.get(position);
        // Put the name in the row's name TextView
        holder.name.setText(recipe.getName());
        //  (counting the ingredient list size)
        holder.info.setText(recipe.getIngredients().size() + " ingredients");
        // When the row is tapped, tell the listener which recipe was clicked
        holder.itemView.setOnClickListener(v -> listener.onRecipeClick(recipe));
    }

    // How many items are in the list - RecyclerView needs this to know how many rows to draw
    @Override
    public int getItemCount() {
        return recipes.size();
    }

    // ViewHolder = holds references to the views inside ONE row, so we don't have to
    // call findViewById over and over while scrolling (that would be slow).
    static class ViewHolder extends RecyclerView.ViewHolder {
        // The two TextViews inside each row
        TextView name, info;

        ViewHolder(View itemView) {
            super(itemView);
            // Find the views inside this row ONCE, store them for reuse
            name = itemView.findViewById(R.id.textRecipeName);
            info = itemView.findViewById(R.id.textRecipeInfo);
        }
    }
}