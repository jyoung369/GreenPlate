package com.example.recipeapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.recipeapp.viewmodels.PantryViewModel;

public class IngredientFragment extends Fragment {

    private EditText ingredientName;
    private EditText ingredientQuantity;
    private EditText caloriesPerServing;

    public IngredientFragment() {
        // Required empty public constructor
    }

/*    public IngredientFragment newInstance() {
        return new IngredientFragment();
    }*/

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

        PantryViewModel vm = new PantryViewModel();

        Button ingInput = view.findViewById(R.id.inputIngButton);
        ingInput.setOnClickListener(v -> vm.inputIngredient(requireContext(), ingredientName, ingredientQuantity, caloriesPerServing));

        super.onViewCreated(view, savedInstanceState);
        super.onCreate(savedInstanceState);
    }

}