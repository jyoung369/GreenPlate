package com.example.recipeapp.model;

import androidx.annotation.NonNull;

import java.util.List;

public class Recipe {
    private String name;
    private int calories;
    private String instructions;
    private List<String> ingredients;
    private List<Double> quantities;

    public Recipe(String name, int calories, String instructions,
                  List<String> ingredients, List<Double> quantities) {
        this.name = name;
        this.calories = calories;
        this.instructions = instructions;
        this.ingredients = ingredients;
        this.quantities = quantities;
    }

    @NonNull
    public String toString() {
        return "Recipe: " + this.name + " Calories: "
                + this.calories + " Instructions: " + instructions;
    }
    public int getCalories() {
        return this.calories;
    }
    public int getIngredientCount() {
        return this.ingredients.size();
    }
    public String getName() {
        return this.name;
    }
    public String getInstructions() {
        return instructions;
    }
    public List<String> getIngredients() {
        return ingredients;
    }
    public List<Double> getQuantities() {
        return quantities;
    }
}
