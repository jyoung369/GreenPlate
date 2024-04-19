package com.example.recipeapp;

import com.example.recipeapp.model.Ingredient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Util {
    public boolean validateHeight(String height) {
        return !height.isEmpty();
    }
    public boolean validateWeight(String weight) {
        return !weight.isEmpty();
    }

    public boolean validName(String mealName) {
        return !mealName.isEmpty();
    }
    public boolean validCalCount(String calories) {
        return calories != null;
    }
    public boolean validateName(String name) {
        return !name.isEmpty();
    }
    public boolean validateInstructions(String instr) {
        return !instr.isEmpty();
    }
    public boolean validateCalories(String calories) {
        return !calories.isEmpty();
    }

    public boolean validateIngredients(List<String> ingredients, List<Integer> quantities) {
        return ingredients != null && quantities != null && ingredients.size() > 0
                && quantities.size() > 0 && ingredients.size() == quantities.size();
    }

    public boolean validateQuantities(ArrayList<Integer> validIngs) {
        for (Integer quantity : validIngs) {
            if (quantity <= 0) {
                return false;
            }
        }
        return true;
    }

    public boolean duplicateIngredients(ArrayList<String> ings, String newIng) {
        for (String ing : ings) {
            if (newIng.equals(ing)) {
                return true;
            }
        }
        return false;
    }

    public boolean replaceSuccessful(Ingredient i, Ingredient j) {
        if (i.getName().equals(j.getName())) {
            j.setCaloriesPerServing(i.getCaloriesPerServing());
            j.setExpirationDate(i.getExpirationDate());
        }
        return (Objects.equals(i.getCaloriesPerServing(), j.getCaloriesPerServing()));
    }

    public boolean containsNumber(String str) {
        return str.matches(".*\\d+.*");
    }

    public boolean canCook(HashMap<String, Integer> ings, HashMap<String, Integer> recipe) {
        boolean cook = false;
        for (HashMap.Entry<String, Integer> e1 : recipe.entrySet()) {
            if (ings.containsKey(e1.getKey())) {
                if (ings.get(e1.getKey()) >= e1.getValue()) {
                    cook = true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return cook;
    }
}
