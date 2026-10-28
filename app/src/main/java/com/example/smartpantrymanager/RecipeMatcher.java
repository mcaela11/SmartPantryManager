package com.example.smartpantrymanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/** Core business logic: a recipe is suggested only if EVERY ingredient is in stock. */
public class RecipeMatcher {

    private static final double EPSILON = 0.0001; // avoids floating-point rounding problems

    // Returns only the recipes the user can make right now
    public static List<Recipe> getSuggestedRecipes(List<Recipe> recipes, List<PantryItem> pantry) {
        Map<String, Double> stock = buildStock(pantry);
        List<Recipe> suggested = new ArrayList<>();
        for (Recipe recipe : recipes) {
            if (canMake(recipe, stock)) suggested.add(recipe);
        }
        return suggested;
    }

    // True only if every ingredient is available in at least the required quantity
    public static boolean canMake(Recipe recipe, Map<String, Double> stock) {
        if (recipe.getIngredients().isEmpty()) return false;
        for (RecipeIngredient ing : recipe.getIngredients()) {
            Double have = stock.get(key(ing.getName(), ing.getUnit()));
            double need = toBase(ing.getQuantity(), ing.getUnit());
            if (have == null || have + EPSILON < need) {
                return false; // one missing or short ingredient rules the recipe out
            }
        }
        return true;
    }

    // Totals the pantry by normalised name + unit type, in base units (g, ml, pcs)
    public static Map<String, Double> buildStock(List<PantryItem> pantry) {
        Map<String, Double> stock = new HashMap<>();
        for (PantryItem item : pantry) {
            String k = key(item.getName(), item.getUnit());
            double qty = toBase(item.getQuantity(), item.getUnit());
            Double existing = stock.get(k);
            stock.put(k, existing == null ? qty : existing + qty);
        }
        return stock;
    }

    private static String key(String name, String unit) {
        return normalizeName(name) + "|" + dimension(unit);
    }

    public static String normalizeName(String raw) {
        String s = raw.toLowerCase(Locale.ROOT).trim().replaceAll("\\s+", " ");
        return singular(s);
    }

    // Simple singularisation: tomatoes -> tomato, berries -> berry, eggs -> egg
    private static String singular(String s) {
        if (s.length() <= 3) return s;
        if (s.endsWith("ies")) return s.substring(0, s.length() - 3) + "y";
        if (s.endsWith("oes") || s.endsWith("ches") || s.endsWith("shes") || s.endsWith("xes")) {
            return s.substring(0, s.length() - 2);
        }
        if (s.endsWith("s") && !s.endsWith("ss") && !s.endsWith("us")) {
            return s.substring(0, s.length() - 1);
        }
        return s;
    }

    private static String dimension(String unit) {
        switch (unit.toLowerCase(Locale.ROOT).trim()) {
            case "g": case "kg": return "mass";
            case "ml": case "l": return "volume";
            default: return "count";
        }
    }

    private static double toBase(double qty, String unit) {
        switch (unit.toLowerCase(Locale.ROOT).trim()) {
            case "kg": case "l": return qty * 1000;
            default: return qty;
        }
    }
}