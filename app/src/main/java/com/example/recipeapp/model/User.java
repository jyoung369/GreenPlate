package com.example.recipeapp.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String uid;
    private String email;
    private double height;
    private double weight;
    private String gender;
    private List<Meal> meals;

    // Constructor
    public User(String uid, String email, double height, double weight, String gender) {
        this.uid = uid;
        this.email = email;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.meals = new ArrayList<>();
    }

    // Getters and setters
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    public void addMeal(Meal meal) {
        if (this.meals == null) {
            this.meals = new ArrayList<>();
        }
        this.meals.add(meal);
    }
}
