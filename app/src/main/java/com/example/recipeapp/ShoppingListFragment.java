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

import com.example.recipeapp.views.ShoppingInputActivity;

public class ShoppingListFragment extends Fragment {

    public ShoppingListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shopping_list, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // testing purposes: delete if you're implementing actual logic
        Button button = view.findViewById(R.id.shoppingInputButton);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), ShoppingInputActivity.class);
            startActivity(intent);
        });

        //        ShoppingListViewModel shoppingListViewModel = new
        //        ViewModelProvider(this).get(ShoppingListViewModel.class);
        //        shoppingListViewModel.addItem(getContext(), "abc", 6, 1200, "10-04-2024");
        //        shoppingListViewModel.getShoppingList().observe(getViewLifecycleOwner(), list -> {
        //            System.out.println("final shopping list: " +
        //            shoppingListViewModel.getShoppingList().getValue());
        //       });
    }
}