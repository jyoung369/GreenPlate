package com.example.recipeapp.model;

public class Ingredient {
    private String name;
    private Integer quantity;
    private int caloriesPerServing;

    private String expirationDate;

    public Ingredient(String name, Integer quantity,
                      int caloriesPerServing, String expirationDate) {
        this.name = name;
        this.quantity = quantity;
        this.caloriesPerServing = caloriesPerServing;
        if (expirationDate != null) {
            this.expirationDate = expirationDate;
        } else {
            this.expirationDate = "N/A";
        }
    }
    public Ingredient(String name, Integer quantity) {
        this.name = name;
        this.quantity = quantity;
    }
    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public int getCaloriesPerServing() {
        return caloriesPerServing;
    }

    public String getExpirationDate() {
        return expirationDate;
    }
}
