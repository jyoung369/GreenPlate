package com.example.recipeapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recipeapp.model.Recipe;
import com.example.recipeapp.views.WelcomeActivity;

import java.util.List;

public class RecipeDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_details_activity);
        Recipe recipe = (Recipe) getIntent().getSerializableExtra("recipe");

        if (recipe != null) {
            TextView recipeName = findViewById(R.id.name_of_recipe);
            TextView calories = findViewById(R.id.calories_in_recipe);
            ListView ingredients = findViewById(R.id.ingredients_of_recipe);
            TextView instructions = findViewById(R.id.instructions_of_recipe);

            recipeName.setText(recipe.getName());
            String calsWithLabel = recipe.getCalories() + " calories";
            calories.setText(calsWithLabel);

            List<String> ings = recipe.getIngredients();
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, ings);
            ingredients.setAdapter(adapter);

            instructions.setText(recipe.getInstructions());
        }
    }

}
