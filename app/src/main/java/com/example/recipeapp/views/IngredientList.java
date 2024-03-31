package com.example.recipeapp.views;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.recipeapp.R;
import com.example.recipeapp.model.Ingredient;
import com.example.recipeapp.viewmodels.PantryViewModel;


import java.util.ArrayList;

public class IngredientList extends AppCompatActivity {
    private PantryViewModel pantryViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_ingredient);
    }
}
