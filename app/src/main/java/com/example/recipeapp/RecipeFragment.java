package com.example.recipeapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.recipeapp.model.Recipe;
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
        RecipeViewModel recipeViewModel = new RecipeViewModel();
        FilterContext filterContext = new FilterContext(new NoFilter());
        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        filterContext.setStrategy(new CalorieFilter());
                        break;
                    case 2:
                        filterContext.setStrategy(new IngredientCountFilter());
                        break;
                    default:
                        filterContext.setStrategy(new NoFilter());
                        break;
                }
                recipeViewModel.filterAndUpdateRecipes(filterContext);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                filterContext.setStrategy(new NoFilter());
            }
        });
        Button addRecipeButton = view.findViewById(R.id.AddRecipeButton);
        addRecipeButton.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(getActivity(), RecipeInput.class);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        recipeViewModel.readRecipes(filterContext);

        LinearLayout recipeListLayout = view.findViewById(R.id.RecipeListLayout);
        LayoutInflater inflater = LayoutInflater.from(getContext());

        recipeViewModel.getRecipeLiveData().observe(getViewLifecycleOwner(), recipes -> {
            recipeListLayout.removeAllViews();
            for (Recipe r : recipeViewModel.getRecipeLiveData().getValue()) {
                View cardView = inflater.inflate(R.layout.recipe_card, null);
                TextView name = cardView.findViewById(R.id.recipe_name_textview);
                SpannableString recipeName = new SpannableString("Recipe Name: " + r.getName());
                recipeName.setSpan(new StyleSpan(Typeface.BOLD), 0, 11,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                name.setText(recipeName);
                TextView calories = cardView.findViewById(R.id.recipe_calories_textview);
                SpannableString caloriesLabel = new SpannableString("Calories: " + r.getCalories());
                caloriesLabel.setSpan(new StyleSpan(Typeface.BOLD), 0, 9,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                calories.setText(caloriesLabel);
                TextView instructions = cardView.findViewById(R.id.recipe_instructions_textview);
                instructions.setText(r.getInstructions());
                LinearLayout ingredientsListLayout = cardView.findViewById(
                        R.id.recipe_ingredients_layout);
                for (int i = 0; i < r.getIngredients().size(); i++) {
                    TextView ingredient = new TextView(requireContext());
                    ingredient.setText(r.getIngredients().get(i) + ": "
                            + r.getQuantities().get(i) + "g");
                    ingredientsListLayout.addView(ingredient);
                }
                recipeListLayout.addView(cardView);
                TextView spacer = new TextView(requireContext());
                recipeListLayout.addView(spacer);
            }
        });
    }
}