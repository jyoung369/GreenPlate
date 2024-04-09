package com.example.recipeapp.views;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.recipeapp.R;
import com.example.recipeapp.model.Ingredient;
import com.example.recipeapp.viewmodels.PantryViewModel;
import com.example.recipeapp.viewmodels.ShoppingListViewModel;


import java.util.ArrayList;

public class ShoppingInputActivity extends AppCompatActivity {
    private EditText ingredientName;
    private EditText ingredientQuantity;
    private EditText caloriesPerServing;
    private Button expirationDate;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_shopping_input);
        ShoppingListViewModel vm = new ShoppingListViewModel();
        Button viewShoppingListButton = findViewById(R.id.viewShoppingListButton);
        viewShoppingListButton.setOnClickListener(v -> finish());
        ingredientName = findViewById(R.id.ingredientName);
        ingredientQuantity = findViewById(R.id.ingredientQuantity);
        caloriesPerServing = findViewById(R.id.caloriesPerServing);
        expirationDate = findViewById(R.id.expirationDate);
        Button ingInput = findViewById(R.id.inputIngButton);

        expirationDate.setOnClickListener(v ->
                vm.showDatePickerDialog(this, expirationDate));


        ArrayList<String> ingData = new ArrayList<>();
        vm.getShoppingList().observe(this, info -> {
            for (Ingredient ingredient : info) {
                ingData.add(ingredient.getName());
            }
        });
        ingInput.setOnClickListener(v -> {
            String ingredientName1 = ingredientName.getText().toString();
            String ingredientQuantityStr = ingredientQuantity.getText().toString();
            String calServingStr = caloriesPerServing.getText().toString();
            String date = expirationDate.getText().toString();
            if (!ingredientQuantityStr.isEmpty() && !calServingStr.isEmpty()) {
                int ingredientQuantity1 = Integer.parseInt(ingredientQuantityStr);
                int calServing = Integer.parseInt(calServingStr);
                if (!ingredientName1.isEmpty() && ingredientQuantity1 > 0 && calServing >= 0) {
                    if (!ingData.contains(ingredientName1)) {
                        vm.addItem(this, ingredientName1, ingredientQuantity1,
                                calServing, date);
                        Intent intent = new Intent(this, WelcomeActivity.class);
                        startActivity(intent);
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
    }
}
