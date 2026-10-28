package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RecipeDetailActivity extends AppCompatActivity {

    // Key used to receive the recipe's id from the Suggested Recipes screen
    public static final String EXTRA_RECIPE_ID = "recipe_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recipe_detail);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.detailRoot), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        long recipeId = getIntent().getLongExtra(EXTRA_RECIPE_ID, -1);
        Recipe recipe = new DatabaseHelper(this).getRecipe(recipeId);
        if (recipe == null) {
            finish(); // nothing to show
            return;
        }

        ((TextView) findViewById(R.id.textDetailName)).setText(recipe.getName());
        ((TextView) findViewById(R.id.textDetailSteps)).setText(recipe.getSteps());

        StringBuilder sb = new StringBuilder();
        for (RecipeIngredient ing : recipe.getIngredients()) {
            sb.append("• ").append(formatQuantity(ing.getQuantity()))
                    .append(" ").append(ing.getUnit())
                    .append(" ").append(ing.getName()).append("\n");
        }
        ((TextView) findViewById(R.id.textDetailIngredients)).setText(sb.toString().trim());

        findViewById(R.id.buttonBack).setOnClickListener(v -> finish());
    }

    // Shows 200 instead of 200.0
    private String formatQuantity(double q) {
        return q == Math.rint(q) ? String.valueOf((long) q) : String.valueOf(q);
    }
}