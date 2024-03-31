package com.example.recipeapp;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.recipeapp.model.Ingredient;
import com.example.recipeapp.viewmodels.PantryViewModel;
import com.example.recipeapp.views.IngredientList;

import java.util.ArrayList;
import java.util.HashMap;

public class IngredientFragment extends Fragment {
    private EditText ingredientName;
    private EditText ingredientQuantity;
    private EditText caloriesPerServing;
    private Button expirationDate;

    public IngredientFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ingredient, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ingredientName = view.findViewById(R.id.ingredientName);
        ingredientQuantity = view.findViewById(R.id.ingredientQuantity);
        caloriesPerServing = view.findViewById(R.id.caloriesPerServing);
        expirationDate = view.findViewById(R.id.expirationDate);

        PantryViewModel vm = new PantryViewModel();

        expirationDate.setOnClickListener(v ->
                vm.showDatePickerDialog(requireContext(), expirationDate));
        

        ArrayList<String> ingData = new ArrayList<>();
        vm.getIngredientData().observe(getViewLifecycleOwner(), info -> {
            for (Ingredient ingredient : info) {
                ingData.add(ingredient.getName());
            }
        });

        // ArrayList<String> ingredientList = new ArrayList<>();
        vm.readIngredients();

        Button ingInput = view.findViewById(R.id.inputIngButton);
        ingInput.setOnClickListener(v -> {
            // ArrayList<String> ingList = new ArrayList<>();
            // vm.readIngredients();
            

            String ingredientName1 = ingredientName.getText().toString();
            int ingredientQuantity1 = Integer.parseInt(ingredientQuantity.getText().toString());
            if (!ingData.contains(ingredientName1)) {
                vm.inputIngredient(requireContext(), ingredientName, ingredientQuantity,
                        caloriesPerServing, expirationDate);
            } else if (ingredientQuantity1 <= 0) {
                ingredientQuantity.setError("Please enter a valid quantity!");
                ingredientName.setError("Cannot accept duplicate ingredients!");
            } else {
                ingredientName.setError("Cannot accept duplicate ingredients!"); 
            }
        });

        Button viewIngredientListButton = view.findViewById(R.id.viewIngredientListButton);
        viewIngredientListButton.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), IngredientList.class);
            startActivity(intent);
        });

        viewCopy = view;

        super.onViewCreated(view, savedInstanceState);
        super.onCreate(savedInstanceState);
    }
}
