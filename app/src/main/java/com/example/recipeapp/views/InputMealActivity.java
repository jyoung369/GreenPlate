package com.example.recipeapp.views;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.os.Bundle;

import com.example.recipeapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class InputMealActivity extends AppCompatActivity {


    DatabaseReference mealRef = FirebaseDatabase.getInstance().getReference("meals");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_meal);
    }
}