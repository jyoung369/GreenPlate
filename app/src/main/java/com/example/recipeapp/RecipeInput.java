package com.example.recipeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.recipeapp.viewmodels.RecipeInputViewModal;
import com.example.recipeapp.views.WelcomeActivity;


public class RecipeInput extends AppCompatActivity {
    private EditText nameInput;
    private EditText instrInput;
    private EditText calorieInput;
    private RecipeInputViewModal viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_recipe_input);
        viewModel = new ViewModelProvider(this).get(RecipeInputViewModal.class);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, RecipeInputFragment.newInstance())
                    .commitNow();
        }
        Button backButton = findViewById(R.id.buttonToRecipe);
        Button saveButton = findViewById(R.id.buttonSave);
        nameInput = findViewById(R.id.etRecipeName);
        instrInput = findViewById(R.id.etRecipeInstructions);
        calorieInput = findViewById(R.id.etCalorieCount);

        backButton.setOnClickListener(back -> {
            Intent intent = new Intent(RecipeInput.this, WelcomeActivity.class);
            startActivity(intent);
        });
        viewModel.getIsSaveSuccessful().observe(this, isSuccess -> {
            if (Boolean.TRUE.equals(isSuccess)) {
                Toast.makeText(this, "Recipe saved successfully.", Toast.LENGTH_SHORT).show();
            } else if (Boolean.FALSE.equals(isSuccess)) {
                Toast.makeText(this, "Failed to save recipe. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
        Button addIngredientButton = findViewById(R.id.buttonAddIngredient);
        addIngredientButton.setOnClickListener(v -> addIngredientEditText());
        saveButton.setOnClickListener(v -> saveRecipeInformation());
    }
    private void addIngredientEditText() {
        LinearLayout ingredientsListLayout = findViewById(R.id.llIngredientsList);
        EditText editText = new EditText(this);
        ingredientsListLayout.addView(editText, 0);
    }
    private void saveRecipeInformation() {
        String name = nameInput.getText().toString().trim();
        String instructions = instrInput.getText().toString().trim();
        String calories = calorieInput.getText().toString().trim();
        if (!viewModel.validateName(name) || !viewModel.validateCalories(calories)
                || !viewModel.validateInstructions(instructions)) {
            if (!viewModel.validateName(name)) {
                nameInput.setError("Please enter a name for this recipe");
                nameInput.requestFocus();
            }
            if (!viewModel.validateInstructions(instructions)) {
                instrInput.setError("Please enter instructions for this recipe");
                instrInput.requestFocus();
            }
            if (!viewModel.validateCalories(calories)) {
                calorieInput.setError("Please enter the calories for this recipe");
                calorieInput.requestFocus();
            }
        } else {
            viewModel.saveRecipe(name, instructions, calories, null);
        }
    }
}
