package com.example.recipeapp;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.recipeapp.model.Ingredient;

import org.junit.Test;

public class Sprint4UnitTests {
    // Saniya Savla
    @Test
    public void replaceCalAndExpSuccess() {
        Util u = new Util();
        Ingredient i = new Ingredient("test", 32, 21, "6/25/2004");
        Ingredient j = new Ingredient("test", 32, 20, "");
        assertTrue(u.replaceSuccessful(i, j));
    }
    @Test
    public void replaceCalAndExpFailure() {
        Util u = new Util();
        Ingredient i = new Ingredient("test", 32, 21, "6/25/2004");
        Ingredient j = new Ingredient("nothing", 32, 20, "");
        assertFalse(u.replaceSuccessful(i, j));
    }
}
