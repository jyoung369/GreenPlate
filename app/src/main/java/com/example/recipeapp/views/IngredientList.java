package com.example.recipeapp.views;

import static java.security.AccessController.getContext;

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
import com.example.recipeapp.model.Ingredient;
import com.example.recipeapp.viewmodels.PantryViewModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class IngredientList extends AppCompatActivity {
    private PantryViewModel pantryViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_list);

        Button backButton = findViewById(R.id.viewIngredientPageButton);
        backButton.setOnClickListener(v -> finish());

        pantryViewModel = new PantryViewModel();
        ArrayList<String> ingredientList = new ArrayList<>();
        pantryViewModel.readIngredients();
        pantryViewModel.readIngredientQuantities();

        LinearLayout ingredientListLayout = findViewById(R.id.ingredients_layout);
        LayoutInflater inflater = LayoutInflater.from(this);

        pantryViewModel.getIngredientData().observe(this, ingredients -> {
            ingredientListLayout.removeAllViews();

            for (Ingredient ingredient : pantryViewModel.getIngredientData().getValue()) {
                // Inflate the ingredient card layout
                System.out.println("Ingredient: " + ingredient);
                View cardView = inflater.inflate(R.layout.ingredient_card, null);
                TextView name = cardView.findViewById(R.id.ingredient_name_textview);
                TextView ingredientQuantity = cardView.findViewById(R.id.ingredient_quantity_textview);
                // Set the name of the ingredient
                name.setText(ingredient.getName());
                System.out.println("Quantity: " + ingredient.getQuantity());
                ingredientQuantity.setText(ingredient.getQuantity().toString() + "g");
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
