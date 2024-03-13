package com.example.recipeapp;

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
    public boolean validCalCount(Integer calories) {
        return calories != null;
    }
}
