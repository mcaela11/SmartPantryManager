package com.example.smartpantrymanager;

import android.app.Activity;
import android.content.Intent;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/** Shared bottom navigation: each tab opens its screen using an Intent. */
public class BottomNavHelper {

    public static void setup(Activity activity, BottomNavigationView nav, int selectedId) {
        nav.setSelectedItemId(selectedId); // highlight the current screen's tab
        nav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == selectedId) return true; // already on this screen

            Intent intent;
            if (id == R.id.nav_pantry) {
                intent = new Intent(activity, MainActivity.class);
                // Return to the existing pantry screen instead of stacking a new one
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            } else if (id == R.id.nav_suggested) {
                intent = new Intent(activity, SuggestedRecipesActivity.class);
            } else {
                intent = new Intent(activity, SettingsActivity.class);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            activity.startActivity(intent);

            // Only the pantry stays underneath; the other two replace each other
            if (selectedId != R.id.nav_pantry) activity.finish();
            return false; // don't change this screen's highlighted tab
        });
    }
}