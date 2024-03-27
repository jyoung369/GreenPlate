package com.example.recipeapp.model;

public class Ingredient {
    private String name;
    private Double quantity;
    public Ingredient(String name, Double quantity) {
        this.name = name;
        this.quantity = quantity;
    }
    public String getName() {
        return name;
    }
    public Double getQuantity() {
        return quantity;
    }
    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
}
