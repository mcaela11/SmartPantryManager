Smart Pantry Manager

An Android app that helps reduce food waste. The user tracks the ingredients they have at home and 
the app suggests recipes they can cook using only those ingredients. A recipe is suggested only when 
every ingredient is in the pantry in at least the required quantity.

Built for Mobile App Development 700.
Student: YOUR NAME (YOUR STUDENT NUMBER)

Features
-Add, edit and delete pantry items (name, quantity, unit, optional expiry date) with input validation
-Pantry list screen (RecyclerView with a custom adapter) reading from the database
-20 recipes pre-loaded on first run
-Suggested Recipes screen using the strict-matching rule, with a message when nothing matches
-Recipe Detail screen showing ingredients and method
-Settings screen with an expiring-soon alert switch, which highlights items expiring within 3 days 
(orange) or already expired (red)
-Bottom navigation between Pantry, Recipes and Settings
-Strict-matching rule

A recipe appears in Suggested Recipes only if every ingredient it needs is in the pantry in
at least the required quantity. If a recipe needs 5 ingredients and the pantry has 4, it is not 
shown. 
Matching handles simple real-world differences:
Capitalisation and spacing (Eggs and eggs)
Plurals (tomatoes and tomato)
Unit conversion (kg to g, l to ml)
Duplicate pantry entries are added together

Database choice: SQLite
The app uses SQLite through SQLiteOpenHelper (the DatabaseHelper class). I chose SQLite because it
is built into Android, works offline, needs no server or account, and fits a personal pantry app 
where the data belongs to one user on one device. It's also the only one I know from class and from
the notes. 
Data persists after the app is closed. There are three tables: pantry, recipes, and 
recipe_ingredients (linked to recipes by recipe_id with ON DELETE CASCADE). 
Settings are stored with SharedPreferences.

Setup and run
Install Android Studio and clone this repository.
Open the project folder in Android Studio and let Gradle sync finish.
Create an emulator (Device Manager) or connect an Android phone with USB debugging on.
Press Run. The recipes are loaded automatically on first launch.

Requirements: Android 6.0 (API 23) or higher.

Testing the matching rule
Add egg (3 pcs), bread (2 pcs) and butter (10 g) to the pantry.
Open the Recipes tab. Recipes whose ingredients are all covered appear.
Delete the butter. Recipes that need butter disappear.

A unit test for the matching logic is in app/src/test (RecipeMatcherTest).

