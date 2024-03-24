package com.example.recipeapp.model;

import java.util.List;

public class Recipe {
    public String name;
    public int calories;
    public String instructions;
    List<String> ingredients;
    List<Integer> quantities;

    public Recipe(String name, int calories, String instructions, List<String> ingredients, List<Integer> quantities){
        this.name = name;
        this.calories = calories;
        this.instructions = instructions;
        this.ingredients = ingredients;
        this.quantities = quantities;
    }

}
