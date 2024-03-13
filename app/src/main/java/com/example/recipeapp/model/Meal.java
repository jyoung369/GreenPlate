package com.example.recipeapp.model;

public class Meal {

    private String name;
    private int calories;

    private String date;

    public Meal(String name, int calories, String date) {
        this.name = name;
        this.calories = calories;
        this.date = date;
    }
    public String getMealName() {
        return name;
    }
    public int getCalories() {
        return calories;
    }

    public String getDate() {
        return date;
    }
}
