package com.example.recipeapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.recipeapp.IngredientFragment;
import com.example.recipeapp.R;
import com.example.recipeapp.viewmodels.PantryViewModel;

import java.util.ArrayList;

public class IngredientList extends AppCompatActivity {
    private PantryViewModel pantryViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_list);

        Button backButton = findViewById(R.id.viewIngredientPageButton);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(IngredientList.this, IngredientFragment.class);
            startActivity(intent);
        });

        pantryViewModel = new PantryViewModel();

        LinearLayout ingredientListLayout = findViewById(R.id.ingredients_layout);

        pantryViewModel.getData().observe(this, ingredients -> {
            ingredientListLayout.removeAllViews();
            for (String ingredient : ingredients) {
                // Inflate the ingredient card layout
                View cardView = LayoutInflater.from(this).inflate(R.layout.ingredient_card, ingredientListLayout, false);
                TextView name = cardView.findViewById(R.id.ingredient_name_textview);
                // Set the name of the ingredient
                name.setText(ingredient);
                // Add the card view to the layout
                ingredientListLayout.addView(cardView);
                // Add a spacer
                View spacer = new View(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 16); // Adjust height as needed
                spacer.setLayoutParams(layoutParams);
                ingredientListLayout.addView(spacer);
            }
        });
    }
}
