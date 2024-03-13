package com.example.recipeapp;

import org.junit.Test;

import static org.junit.Assert.*;
public class InputMealTest {
    @Test
    public void checkEquation() {
        InputMealFragment fragment = new InputMealFragment();
        int calories = fragment.calorieEquation(170, 63, "Female");
        assertEquals(calories, 1337);
    }
}
