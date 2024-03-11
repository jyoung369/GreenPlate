package com.example.recipeapp.viewmodels;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import com.example.recipeapp.model.Meal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MealViewModel {

    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public void inputMeal(Context context, EditText mealName, EditText calories, EditText mealDate) {
        String nameOfMeal = mealName.getText().toString();
        String cals = calories.getText().toString();
        String date = mealDate.getText().toString();

        if (nameOfMeal.isEmpty()) {
            mealName.setError("Please enter the name of your meal!");
        } else if (cals.isEmpty()) {
            calories.setError("Please enter the amount of calories in your meal!");
        } else if (date.isEmpty()) {
            mealDate.setError("Please enter when you had your meal!");
        }
        else {
            Meal myMeal = new Meal(nameOfMeal, cals, date);
            FirebaseDatabase database = FirebaseDatabase
                    .getInstance("https://recipeapp-1fba1-default-rtdb.firebaseio.com/");
            DatabaseReference mealsref = database.getReference().child("meals/"
                    + user.getUid());

            mealsref.push().setValue(myMeal)
                    .addOnSuccessListener(success -> {
                        Toast.makeText(context,
                                "Meal inputted successfully!", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(failure -> {
                        Toast.makeText(context,
                                "Could not input meal", Toast.LENGTH_SHORT).show();
                    });
        }
    }
}
