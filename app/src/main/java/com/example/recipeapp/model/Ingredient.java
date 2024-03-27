package com.example.recipeapp.model;

public class Ingredient {
    private String name;
    private int quantity;
    private int caloriesPerServing;

    public Ingredient(String name, int quantity, int caloriesPerServing) {
        this.name = name;
        this.quantity = quantity;
        this.caloriesPerServing = caloriesPerServing;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getCaloriesPerServing() {
        return caloriesPerServing;
    }
}
