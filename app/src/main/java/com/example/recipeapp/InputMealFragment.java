package com.example.recipeapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.recipeapp.views.InputMealActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class InputMealFragment extends Fragment {

    public InputMealFragment() {
        // Required empty public constructor
    }

    private EditText editTextHeight;
    private EditText editTextWeight;
    private Spinner spinnerGender;

    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_input_meal, container, false);


        // Find references to the buttons in the inflated layout
        Button button1 = view.findViewById(R.id.button1);
        Button button2 = view.findViewById(R.id.button2);

        // Set click listener for Button 1
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button1 click
                // Perform an action, e.g., start another activity or execute some logic
            }
        });

        // Set click listener for Button 2
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button2 click
                // Perform an action, e.g., start another activity or execute some logic
            }
        });

        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        EditText mealName = view.findViewById(R.id.mealName);
        EditText calories = view.findViewById(R.id.calorieCount);
        super.onViewCreated(view, savedInstanceState);
        super.onCreate(savedInstanceState);
        Button input = view.findViewById(R.id.inputButton);
        input.setOnClickListener(v -> inputMeal(mealName, calories));
    }

    class Meal {

        private String name;
        private String calories;
        public Meal (String name, String calories) {
            this.name = name;
            this.calories = calories;
        }
        public String getMealName() {
            return name;
        }
        public String getCalories() {
            return calories;
        }
    }
    private void inputMeal(EditText mealName, EditText calories) {
        String nameOfMeal = mealName.getText().toString();
        String cals = calories.getText().toString();
        if (nameOfMeal.isEmpty()) {
            mealName.setError("Please enter the name of your meal!");
        } else if (cals.isEmpty()) {
            calories.setError("Please enter the amount of calories in your meal!");
        } else {
            Meal myMeal = new Meal(nameOfMeal, cals);

            FirebaseDatabase database = FirebaseDatabase
                    .getInstance("https://recipeapp-1fba1-default-rtdb.firebaseio.com/");
            DatabaseReference mealsref = database.getReference().child("meals/"
                    + user.getUid());

            mealsref.push().setValue(myMeal)
                    .addOnSuccessListener(success -> {
                        Toast.makeText(requireContext(),
                                "Meal inputted successfully!", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(failure -> {
                        Toast.makeText(requireContext(),
                                "Could not input meal", Toast.LENGTH_SHORT).show();
                    });
        }
    }

}
