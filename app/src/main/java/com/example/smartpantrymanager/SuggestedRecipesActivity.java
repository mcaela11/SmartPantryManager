package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SuggestedRecipesActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private RecipeAdapter adapter;
    private TextView textNoMatches;
    private final List<Recipe> suggested = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_suggested_recipes);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.suggestedRoot), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        dbHelper = new DatabaseHelper(this);
        textNoMatches = findViewById(R.id.textNoMatches);

        RecyclerView recyclerView = findViewById(R.id.recyclerSuggested);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Tapping a recipe opens its detail screen, passing the recipe id in the Intent
        adapter = new RecipeAdapter(suggested, recipe -> {
            Intent intent = new Intent(this, RecipeDetailActivity.class);
            intent.putExtra(RecipeDetailActivity.EXTRA_RECIPE_ID, recipe.getId());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        // Bottom navigation: highlights the Recipes tab
        BottomNavHelper.setup(this, findViewById(R.id.bottomNav), R.id.nav_suggested);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Re-run the strict match every time the screen appears
        suggested.clear();
        suggested.addAll(RecipeMatcher.getSuggestedRecipes(
                dbHelper.getAllRecipes(), dbHelper.getAllPantryItems()));
        adapter.notifyDataSetChanged();
        textNoMatches.setVisibility(suggested.isEmpty() ? View.VISIBLE : View.GONE);
    }
}