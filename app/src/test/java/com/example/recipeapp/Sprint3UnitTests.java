package com.example.recipeapp;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.recipeapp.model.Ingredient;
import com.example.recipeapp.model.Recipe;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
    public void checkRecipeInstructionsValid() {
        Util u = new Util();
        String instructions = "Do this. Then do this. Then finish doing this.";
        assertTrue(u.validateInstructions(instructions));
    }

    public void checkValidRecipe() {
        Util u = new Util();
        Recipe r = new Recipe("Recipe", 199, "instructions",
                new ArrayList<String>(), new ArrayList<Integer>());
        assertFalse(u.validateIngredients(r.getIngredients(), r.getQuantities()));
    }

    // Amritha Pramod
    @Test
    public void checkForDuplicateIngredients() {
        Util u = new Util();
        ArrayList<String> ings = new ArrayList<>();
        ings.add("carrots");
        ings.add("broccoli");
        assertTrue(u.duplicateIngredients(ings, "broccoli"));
    }

    // Amritha Pramod
    @Test
    public void checkValidQuantities() {
        Util u = new Util();
        ArrayList<Integer> validIngs = new ArrayList<>();
        validIngs.add(3);
        validIngs.add(-1);
        assertFalse(u.validateQuantities(validIngs));
    }

}
