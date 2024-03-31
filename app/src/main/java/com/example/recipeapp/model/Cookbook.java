package com.example.recipeapp.model;

import java.util.HashMap;

public class Cookbook {
    public boolean sufficientIngredients(HashMap<String, Integer> pantry, Recipe recipe) {
        for (int i = 0; i < recipe.getIngredients().size(); i++) {
            String ing = recipe.getIngredients().get(i);
            if (!(pantry.containsKey(ing))) {
                return (false);
            } else {
                int qty = pantry.get(recipe.getIngredients().get(i));
                if (qty < recipe.getQuantities().get(i)) {
                    return (false);
                }
            }
        }
        return (true);
    }
}
