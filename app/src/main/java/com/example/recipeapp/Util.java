package com.example.recipeapp;

import java.util.ArrayList;
import java.util.List;

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

    public boolean containsNumber(String str) {
        return str.matches(".*\\d+.*");
    }
}
