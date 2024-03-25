package com.example.recipeapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.anychart.scales.Linear;
import com.example.recipeapp.model.Recipe;
import com.example.recipeapp.viewmodels.RecipeViewModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

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


        LinearLayout recipeListLayout = view.findViewById(R.id.RecipeListLayout);
        LayoutInflater inflater = LayoutInflater.from(getContext());

        recipeViewModel.getRecipeLiveData().observe(getViewLifecycleOwner(), recipes->{
            for (Recipe r : recipeViewModel.getRecipeLiveData().getValue()){
                View cardView = inflater.inflate(R.layout.recipe_card, null);
                TextView name = cardView.findViewById(R.id.recipe_name_textview);
                SpannableString recipeName = new SpannableString("Recipe Name: " + r.name);
                recipeName.setSpan(new StyleSpan(Typeface.BOLD), 0, 11, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                name.setText(recipeName);
                TextView calories = cardView.findViewById(R.id.recipe_calories_textview);
                SpannableString caloriesLabel = new SpannableString("Calories: " + r.calories);
                caloriesLabel.setSpan(new StyleSpan(Typeface.BOLD), 0, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                calories.setText(caloriesLabel);
                TextView instructions = cardView.findViewById(R.id.recipe_instructions_textview);
                instructions.setText(r.instructions);
                TextView available = cardView.findViewById(R.id.recipe_ingredients_available_textview);
                LinearLayout ingredientsListLayout = cardView.findViewById(R.id.recipe_ingredients_layout);
                for (int i=0;i<r.ingredients.size();i++){
                    TextView ingredient = new TextView(requireContext());
                    ingredient.setText(r.ingredients.get(i) + ": " + r.quantities.get(i));
                    ingredientsListLayout.addView(ingredient);
                }
                recipeListLayout.addView(cardView);
                TextView spacer = new TextView(requireContext());
                recipeListLayout.addView(spacer);
            }
        });
    }
}