package com.example.recipeapp;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class Sprint3UnitTests {
    // Saniya Savla
    @Test
    public void checkRecipeCaloriesEmpty() {
        Util u = new Util();
        String calories = "";
        assertFalse(u.validateCalories(calories));
    }
    @Test
    public void checkRecipeNameValid() {
        Util u = new Util();
        String name = "Bacon and Eggs";
        assertTrue(u.validateName(name));
    }
}
