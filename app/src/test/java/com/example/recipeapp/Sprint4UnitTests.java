package com.example.recipeapp;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.recipeapp.model.Ingredient;

import org.junit.Test;

import java.util.HashMap;

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

    //Amritha Pramod
    @Test
    public void checkCook() {
        Util u = new Util();
        HashMap<String, Integer> ings = new HashMap<>();
        HashMap<String, Integer> recipe = new HashMap<>();

        ings.put("carrots", 80);
        recipe.put("carrots", 50);
        assertTrue(u.canCook(ings, recipe));

        ings.put("lettuce", 67);
        ings.put("cabbage", 90);
        recipe.put("lettuce", 67);
        recipe.put("radish", 20);
        assertFalse(u.canCook(ings, recipe));
    }

    //Amritha Pramod
    @Test
    public void checkDailyCals() {
        Util u = new Util();
        HashMap<String, Integer> meals = new HashMap<>();

        meals.put("cake", 902);
        meals.put("pie", 503);
        meals.put("noodles", 670);

        assertFalse(u.updateDailyCalories(2075, meals));
        assertTrue(u.updateDailyCalories(1405, meals));
    }

    //Simona Ivanov
    @Test
    public void checkDeletionIfQuantity0() {
        Util u = new Util();
        HashMap<String, Integer> ingredients = new HashMap<>();
        ingredients.put("milk", 20);
        ingredients.put("cereal", 30);
        HashMap<String, Integer> recipeStuff = new HashMap<>();
        recipeStuff.put("milk", 20);
        recipeStuff.put("cereal", 15);
        assertTrue(u.checkIngredientDeletion(ingredients, recipeStuff));
    }

    @Test
    public void checkQuantitiesDeducted() {
        Util u = new Util();
        HashMap<String, Integer> ingredients = new HashMap<>();
        ingredients.put("milk", 20);
        ingredients.put("cereal", 30);
        ingredients.put("broccoli", 500);
        HashMap<String, Integer> recipeStuff = new HashMap<>();
        recipeStuff.put("milk", 20);
        recipeStuff.put("cereal", 15);
        assertTrue(u.checkForDeduction(ingredients, recipeStuff));
    }
}
