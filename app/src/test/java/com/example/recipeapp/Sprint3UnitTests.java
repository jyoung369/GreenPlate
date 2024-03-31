package com.example.recipeapp;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.recipeapp.model.Cookbook;
import com.example.recipeapp.model.Recipe;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

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

    // Reese Wang
    @Test
    public void checkRecipeInstructionsValid() {
        Util u = new Util();
        String instructions = "Do this. Then do this. Then finish doing this.";
        assertTrue(u.validateInstructions(instructions));
    }

    @Test
    public void checkValidRecipe() {
        Util u = new Util();
        Recipe r = new Recipe("Recipe", 199, "instructions",
                new ArrayList<String>(), new ArrayList<Integer>());
        assertFalse(u.validateIngredients(r.getIngredients(), r.getQuantities()));
    }




    //Julie Young
    @Test
    public void checkMissingIngredient() {
        Cookbook book = new Cookbook();
        ArrayList<String> ingredients = new ArrayList<>();
        ingredients.add("Milk");
        ArrayList<Integer> qty = new ArrayList<>();
        qty.add(10);
        Recipe r = new Recipe(
                "Recipe", 200, "instructions", ingredients, qty);
        HashMap<String, Integer> pantry = new HashMap<>();
        assertFalse(book.sufficientIngredients(pantry, r));
    }
    @Test
    public void checkInsufficientQty() {
        Cookbook book = new Cookbook();
        ArrayList<String> ingredients = new ArrayList<>();
        ingredients.add("Milk");
        ArrayList<Integer> qty = new ArrayList<>();
        qty.add(200);
        Recipe r = new Recipe(
                "Recipe", 200, "instructions", ingredients, qty);
        HashMap<String, Integer> pantry = new HashMap<>();
        pantry.put("Milk", 100);
        assertFalse(book.sufficientIngredients(pantry, r));
    }
    @Test
    public void checkSufficient() {
        Cookbook book = new Cookbook();
        ArrayList<String> ingredients = new ArrayList<>();
        ingredients.add("Milk");
        ingredients.add("Eggs");
        ArrayList<Integer> qty = new ArrayList<>();
        qty.add(200);
        qty.add(10);
        Recipe r = new Recipe(
                "Recipe", 200, "instructions", ingredients, qty);
        HashMap<String, Integer> pantry = new HashMap<>();
        pantry.put("Milk", 300);
        pantry.put("Eggs", 40);
        assertTrue(book.sufficientIngredients(pantry, r));
    }

}
