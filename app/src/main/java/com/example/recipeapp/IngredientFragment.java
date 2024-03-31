package com.example.recipeapp;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.recipeapp.model.Ingredient;
import com.example.recipeapp.viewmodels.PantryViewModel;
import com.example.recipeapp.views.IngredientList;

import java.util.ArrayList;

public class IngredientFragment extends Fragment {
    private PantryViewModel pantryViewModel;

    public IngredientFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_ingredient_list, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Button button = view.findViewById(R.id.ingredientInputButton);

        pantryViewModel = new PantryViewModel();
        ArrayList<String> ingredientList = new ArrayList<>();
        pantryViewModel.readIngredients();
        pantryViewModel.readIngredientQuantities();

        LinearLayout ingredientListLayout = view.findViewById(R.id.ingredients_layout);
        LayoutInflater inflater = LayoutInflater.from(this);

        pantryViewModel.getIngredientData().observe(getViewLifecycleOwner(), ingredients -> {
            ingredientListLayout.removeAllViews();

            for (Ingredient ingredient : pantryViewModel.getIngredientData().getValue()) {
                // Inflate the ingredient card layout
                System.out.println("Ingredient: " + ingredient);
                View cardView = inflater.inflate(R.layout.ingredient_card, null);
                TextView name = cardView.findViewById(R.id.ingredient_name_textview);
                TextView ingredientQuantity = cardView.findViewById(R.id.ingredient_quantity_textview);
                // Set the name of the ingredient
                name.setText(ingredient.getName());
                System.out.println("Quantity: " + ingredient.getQuantity());
                ingredientQuantity.setText(ingredient.getQuantity().toString() + "g");
                // Add the card view to the layout
                ingredientListLayout.addView(cardView);
                // Add a spacer
                View spacer = new View(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 16); // Adjust height as needed
                spacer.setLayoutParams(layoutParams);
                ingredientListLayout.addView(spacer);
            }
        });
//        ingredientName = view.findViewById(R.id.ingredientName);
//        ingredientQuantity = view.findViewById(R.id.ingredientQuantity);
//        caloriesPerServing = view.findViewById(R.id.caloriesPerServing);
//        expirationDate = view.findViewById(R.id.expirationDate);
//
//        PantryViewModel vm = new PantryViewModel();
//
//        expirationDate.setOnClickListener(v ->
//                vm.showDatePickerDialog(requireContext(), expirationDate));
//
//
//        ArrayList<String> ingData = new ArrayList<>();
//        vm.getIngredientData().observe(getViewLifecycleOwner(), info -> {
//            for (Ingredient ingredient : info) {
//                ingData.add(ingredient.getName());
//            }
//        });
//        // ArrayList<String> ingredientList = new ArrayList<>();
//        vm.readIngredients();
//        Button ingInput = view.findViewById(R.id.inputIngButton);
//        ingInput.setOnClickListener(v -> {
//            // ArrayList<String> ingList = new ArrayList<>();
//            // vm.readIngredients();
//            String ingredientName1 = ingredientName.getText().toString();
//            String ingredientQuantityStr = ingredientQuantity.getText().toString();
//            String calServingStr = caloriesPerServing.getText().toString();
//            if (!ingredientQuantityStr.isEmpty() && !calServingStr.isEmpty()) {
//                int ingredientQuantity1 = Integer.parseInt(ingredientQuantityStr);
//                int calServing = Integer.parseInt(calServingStr);
//                if (!ingredientName1.isEmpty() && ingredientQuantity1 > 0 && calServing >= 0) {
//                    if (!ingData.contains(ingredientName1)) {
//                        vm.inputIngredient(requireContext(), ingredientName, ingredientQuantity,
//                                caloriesPerServing, expirationDate);
//                    } else {
//                        ingredientName.setError("Cannot accept duplicate ingredients!");
//                    }
//                } else {
//                    if (ingredientName1.isEmpty()) {
//                        ingredientName.setError("Please enter an ingredient name");
//                    }
//                    if (ingredientQuantity1 <= 0) {
//                        ingredientQuantity.setError("Please enter a valid quantity!");
//                    }
//                    if (calServing < 0) {
//                        caloriesPerServing.setError("Please enter a valid calories per serving.");
//                    }
//                }
//            } else {
//                if (ingredientQuantityStr.isEmpty()) {
//                    ingredientQuantity.setError("Please enter an ingredient quantity.");
//                }
//                if (calServingStr.isEmpty()) {
//                    caloriesPerServing.setError("Please enter calories per serving.");
//                }
//            }
//        });
//
//        Button viewIngredientListButton = view.findViewById(R.id.viewIngredientListButton);
//        viewIngredientListButton.setOnClickListener(v -> {
//            Intent intent = new Intent(requireContext(), IngredientList.class);
//            startActivity(intent);
//        });
        super.onViewCreated(view, savedInstanceState);
        super.onCreate(savedInstanceState);
    }
}
