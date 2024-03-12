package com.example.recipeapp.viewmodels;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.recipeapp.model.Meal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MealViewModel {

    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private MutableLiveData<Integer> dailyCount = new MutableLiveData<>();

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

    public LiveData<Integer> getDailyCount() {
        return dailyCount;
    }

    public void readDailyMeals() {
        FirebaseDatabase database = FirebaseDatabase
                .getInstance("https://recipeapp-1fba1-default-rtdb.firebaseio.com/");
        DatabaseReference mealsRef = database.getReference().child("meals/"
                + user.getUid());
        mealsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int calorieCount = 0;
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                Calendar calendar = Calendar.getInstance();
                String today = dateFormat.format(calendar.getTime());
                for (DataSnapshot meal: snapshot.getChildren()) {
                    if (today.equals(meal.child("date").getValue(String.class))) {
                        String calorieString = meal.child("calories").getValue(String.class);
                        calorieCount = calorieCount + Integer.parseInt(calorieString);
                    }
                }
                dailyCount.setValue(calorieCount);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error);
            }
        });
    }
}
