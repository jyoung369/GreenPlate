package com.example.recipeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.recipeapp.views.WelcomeActivity;

public class RecipeInput extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_recipe_input);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, RecipeInputFragment.newInstance())
                    .commitNow();
        }
        Button backButton = findViewById(R.id.buttonToRecipe);
        backButton.setOnClickListener(back -> {
            Intent intent = new Intent(RecipeInput.this, WelcomeActivity.class);
            startActivity(intent);
        });

        Button addIngredientButton = findViewById(R.id.buttonAddIngredient);
        addIngredientButton.setOnClickListener(v -> addIngredientEditText());
    }
    private void addIngredientEditText() {
        LinearLayout ingredientsListLayout = findViewById(R.id.llIngredientsList);
        EditText editText = new EditText(this);
        ingredientsListLayout.addView(editText, 0);
    }
}
