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

        expirationDate.setOnClickListener(v -> vm.showDatePickerDialog(requireContext(), expirationDate));

        ArrayList<String> ingData = new ArrayList<>();
        vm.getData().observe(getViewLifecycleOwner(), info -> {
            for (String name : info) {
                ingData.add(name);
            }
        });

        ArrayList<String> ingredientList = new ArrayList<>();
        vm.readIngredients(ingredientList);

        Button ingInput = view.findViewById(R.id.inputIngButton);
        ingInput.setOnClickListener(v -> {
            String ingredient_name = ingredientName.getText().toString();
            int ingredient_quantity = Integer.parseInt(ingredientQuantity.getText().toString());
            if (!ingData.contains(ingredient_name)) {
                vm.inputIngredient(requireContext(), ingredientName, ingredientQuantity, caloriesPerServing, expirationDate);
            } else if (ingredient_quantity <= 0) {
                ingredientQuantity.setError("Please enter a valid quantity!");
                ingredientName.setError("Cannot accept duplicate ingredients!");
            } else {
                ingredientName.setError("Cannot accept duplicate ingredients!");
            }
        });

        super.onViewCreated(view, savedInstanceState);
        super.onCreate(savedInstanceState);
    }
}
