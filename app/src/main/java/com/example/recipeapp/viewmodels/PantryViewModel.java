package com.example.recipeapp.viewmodels;

import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.recipeapp.model.Ingredient;
import com.example.recipeapp.model.Meal;
import com.example.recipeapp.model.Recipe;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class PantryViewModel {
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public void inputIngredient(Context context, EditText ingredientName,
                          EditText quantity, EditText caloriesPerServing) {
        String ingredient_name = ingredientName.getText().toString();
        String strIngredient_quantity = quantity.getText().toString();
        String strIngredient_calories = caloriesPerServing.getText().toString();
        int ingredient_quantity = Integer.parseInt(quantity.getText().toString());
        int ingredient_calories = Integer.parseInt(caloriesPerServing.getText().toString());
        if (ingredient_name.isEmpty()) {
            ingredientName.setError("Please enter the name of your ingredient!");
        } else if (strIngredient_quantity.isEmpty()) {
            quantity.setError("Please enter the quantity of your ingredient!");
        } else if (strIngredient_calories.isEmpty()) {
            caloriesPerServing.setError("Please enter the calories per serving for this ingredient!");
        } else {
            ingredientName.setError(null);
            quantity.setError(null);
            caloriesPerServing.setError(null);
            Ingredient newIngredient = new Ingredient(ingredient_name, ingredient_quantity, ingredient_calories);
            FirebaseDatabase database = FirebaseDatabase
                    .getInstance("https://recipeapp-1fba1-default-rtdb.firebaseio.com/");
            DatabaseReference pantryDB = database.getReference().child("pantry/"
                    + user.getUid());

            pantryDB.push().setValue(newIngredient)
                    .addOnSuccessListener(success -> {
                        Toast.makeText(context,
                                "Ingredient inputted successfully!", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(failure -> {
                        Toast.makeText(context,
                                "Could not input ingredient", Toast.LENGTH_SHORT).show();
                    });
        }
    }

}
