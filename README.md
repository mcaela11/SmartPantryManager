Smart Pantry Manager

A Java-based Android app designed to reduce food waste by tracking the ingredients you already 
have and suggesting recipes you can cook using only those items (strict matching: every ingredient
must be available in your pantry in the required quantity).

Features
-Pantry Management: Add, edit, and delete items (name, quantity, unit, and optional expiry date)
-Recipe Collection: Pre-loaded with a selection of recipes
-Suggested Recipes: Displays meal ideas using strict-matching logic
-Recipe Details: View full ingredient lists and cooking methods
-Settings: Customizable app preferences

Database
The app uses SQLite (via SQLiteOpenHelper). 
This was chosen because it is entirely local, requires no internet connection or user accounts, 
persists data between sessions, and is well-suited to the app's small relational model 
(pantry items, recipes, and recipe ingredients).

Setup / Run
-Clone the repository: git clone https://github.com/Mcaela11/SmartPantryManager.git
-Open the project folder in Android Studio and allow Gradle to sync.
-Run the app on an emulator or physical device (Minimum SDK 23).

Author
Mcaela Kiara Moodley, 402412625

