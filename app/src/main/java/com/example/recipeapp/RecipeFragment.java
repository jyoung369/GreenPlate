package com.example.recipeapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.recipeapp.viewmodels.RecipeViewModel;

public class RecipeFragment extends Fragment {

    public RecipeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)  {
        Spinner filterSpinner = view.findViewById(R.id.FilterSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireActivity(),
                R.array.filter_options_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(adapter);

        Button addRecipeButton = view.findViewById(R.id.AddRecipeButton);
        addRecipeButton.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(getActivity(), RecipeInput.class);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        RecipeViewModel recipeViewModel = new RecipeViewModel();
        recipeViewModel.readRecipes();
        System.out.println(recipeViewModel.getRecipeList());
    }
}