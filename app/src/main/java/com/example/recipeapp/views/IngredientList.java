package com.example.recipeapp.views;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.recipeapp.R;
import com.example.recipeapp.model.Ingredient;
import com.example.recipeapp.viewmodels.PantryViewModel;


import org.w3c.dom.Text;

import java.util.ArrayList;

public class IngredientList extends AppCompatActivity {
    private PantryViewModel pantryViewModel;
    private EditText ingredientName;
    private EditText ingredientQuantity;
    private EditText caloriesPerServing;
    private Button expirationDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_ingredient);
        ingredientName = findViewById(R.id.ingredientName);
        ingredientQuantity = findViewById(R.id.ingredientQuantity);
        caloriesPerServing = findViewById(R.id.caloriesPerServing);
        expirationDate = findViewById(R.id.expirationDate);

        PantryViewModel vm = new PantryViewModel();

        expirationDate.setOnClickListener(v ->
                vm.showDatePickerDialog(this, expirationDate));


        ArrayList<String> ingData = new ArrayList<>();
        vm.getIngredientData().observe(this, info -> {
            for (Ingredient ingredient : info) {
                ingData.add(ingredient.getName());
            }
        });
        // ArrayList<String> ingredientList = new ArrayList<>();
        vm.readIngredients();
        Button ingInput = findViewById(R.id.inputIngButton);
        ingInput.setOnClickListener(v -> {
            // ArrayList<String> ingList = new ArrayList<>();
            // vm.readIngredients();
            String ingredientName1 = ingredientName.getText().toString();
            String ingredientQuantityStr = ingredientQuantity.getText().toString();
            String calServingStr = caloriesPerServing.getText().toString();
            if (!ingredientQuantityStr.isEmpty() && !calServingStr.isEmpty()) {
                int ingredientQuantity1 = Integer.parseInt(ingredientQuantityStr);
                int calServing = Integer.parseInt(calServingStr);
                if (!ingredientName1.isEmpty() && ingredientQuantity1 > 0 && calServing >= 0) {
                    if (!ingData.contains(ingredientName1)) {
                        vm.inputIngredient(this, ingredientName, ingredientQuantity,
                                caloriesPerServing, expirationDate);
                    } else {
                        ingredientName.setError("Cannot accept duplicate ingredients!");
                    }
                } else {
                    if (ingredientName1.isEmpty()) {
                        ingredientName.setError("Please enter an ingredient name");
                    }
                    if (ingredientQuantity1 <= 0) {
                        ingredientQuantity.setError("Please enter a valid quantity!");
                    }
                    if (calServing < 0) {
                        caloriesPerServing.setError("Please enter a valid calories per serving.");
                    }
                }
            } else {
                if (ingredientQuantityStr.isEmpty()) {
                    ingredientQuantity.setError("Please enter an ingredient quantity.");
                }
                if (calServingStr.isEmpty()) {
                    caloriesPerServing.setError("Please enter calories per serving.");
                }
            }
        });

        Button viewIngredientListButton = findViewById(R.id.viewIngredientListButton);
        viewIngredientListButton.setOnClickListener(v -> finish());
    }
}
