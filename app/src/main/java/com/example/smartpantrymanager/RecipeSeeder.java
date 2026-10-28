package com.example.smartpantrymanager;

/** Pre-loads the recipe collection the first time the app runs. */
public class RecipeSeeder {

    public static void seedIfEmpty(DatabaseHelper db) {
        if (db.getRecipeCount() > 0) return; // already seeded

        add(db, "Peanut Butter Sandwich",
                "1. Spread peanut butter on the bread.\n2. Put the slices together and enjoy.",
                i("bread", 2, "slices"), i("peanut butter", 30, "g"));

        add(db, "Two-Minute Noodles",
                "1. Boil water and add the noodles.\n2. Cook for 2 minutes.\n3. Add the seasoning and stir.",
                i("instant noodles", 1, "pack"), i("water", 500, "ml"));

        add(db, "Toast with Butter",
                "1. Toast the bread.\n2. Spread butter on the toast.",
                i("bread", 2, "slices"), i("butter", 10, "g"));

        add(db, "Cereal and Milk",
                "1. Pour cereal into a bowl.\n2. Add milk and eat.",
                i("cereal", 50, "g"), i("milk", 200, "ml"));

        add(db, "Jam Sandwich",
                "1. Spread jam on the bread.\n2. Put the slices together.",
                i("bread", 2, "slices"), i("jam", 30, "g"));

        add(db, "Instant Soup",
                "1. Empty the soup powder into a mug.\n2. Add boiling water and stir well.",
                i("instant soup", 1, "packet"), i("water", 250, "ml"));

        add(db, "Cheese Sandwich",
                "1. Place cheese between two slices of bread.\n2. Cut the sandwich in half.",
                i("bread", 2, "slices"), i("cheese", 40, "g"));

        add(db, "Banana and Peanut Butter on Toast",
                "1. Peel and slice the banana.\n2. Toast the bread.\n3. Spread butter on the toast.\n"
                        + "4. Spread the peanut butter on the toast.\n5. Place the banana slices on top.",
                i("banana", 1, "pcs"), i("peanut butter", 20, "g"), i("butter", 10, "g"));

        add(db, "Scrambled Eggs",
                "1. Crack the eggs into a bowl and whisk with milk.\n2. Cook in a pan until done.",
                i("egg", 2, "pcs"), i("butter", 5, "g"), i("milk", 15, "ml"));

        add(db, "Yoghurt and Blueberries",
                "1. Put yoghurt in a bowl.\n2. Wash the blueberries.\n3. Add the blueberries.",
                i("yoghurt", 50, "g"), i("blueberries", 15, "g"));

        add(db, "Instant Oats",
                "1. Add oats and milk to a bowl.\n2. Microwave for 1-2 minutes, stirring halfway through.",
                i("oats", 50, "g"), i("milk", 200, "ml"));

        add(db, "Milo or Hot Chocolate",
                "1. Add the chocolate powder to a mug.\n2. Pour in warm milk and stir.",
                i("chocolate powder", 20, "g"), i("milk", 250, "ml"));

        add(db, "Cheese Toast",
                "1. Butter the slices of bread.\n2. Put the cheese between the slices.\n"
                        + "3. Butter the outside of the sandwich.\n4. Toast on the stove or in the air-fryer until the cheese melts.",
                i("bread", 2, "slices"), i("cheese", 40, "g"), i("butter", 10, "g"));

        add(db, "Egg on Toast",
                "1. Toast two slices of bread.\n2. Butter the toast.\n3. Fry the eggs on the stove.\n"
                        + "4. Add seasoning to the eggs.\n5. Place the eggs on the toast.",
                i("bread", 2, "slices"), i("egg", 2, "pcs"), i("butter", 10, "g"));

        add(db, "Instant Noodles with Egg",
                "1. Cook the noodles according to the packet instructions.\n2. Add an egg and cook until fully set.\n3. Stir in the seasoning.",
                i("instant noodles", 1, "pack"), i("egg", 1, "pcs"), i("water", 500, "ml"));

        add(db, "Avocado on Toast",
                "1. Toast the slices of bread.\n2. Cut the avocado into slices.\n3. Place the avocado on the toast.",
                i("bread", 2, "slices"), i("avocado", 1, "pcs"));

        add(db, "Butter Noodles",
                "1. Cook the instant noodles.\n2. Drain the water.\n3. Add the instant noodle seasoning.\n4. Stir in a spoon of butter.",
                i("instant noodles", 1, "pack"), i("butter", 15, "g"));

        add(db, "Muesli and Yoghurt",
                "1. Pour the muesli into a bowl.\n2. Add the yoghurt and stir.",
                i("muesli", 50, "g"), i("yoghurt", 100, "g"));

        add(db, "Butter Bread and Tea",
                "1. Butter two slices of bread.\n2. Put the slices together and cut in half.\n3. Make a cup of tea.",
                i("bread", 2, "slices"), i("butter", 15, "g"), i("tea", 1, "pcs"));

        add(db, "Chip Sandwich",
                "1. Butter two slices of bread.\n2. Place the chips on one slice of bread.\n3. Put the other slice on top and eat.",
                i("bread", 2, "slices"), i("chips", 1, "packet"), i("butter", 10, "g"));
    }

    // Shortcut for building an ingredient (ids are assigned by the database)
    private static RecipeIngredient i(String name, double qty, String unit) {
        return new RecipeIngredient(0, 0, name, qty, unit);
    }

    private static void add(DatabaseHelper db, String name, String steps, RecipeIngredient... ingredients) {
        Recipe r = new Recipe(0, name, steps);
        for (RecipeIngredient ing : ingredients) r.addIngredient(ing);
        db.addRecipe(r);
    }
}