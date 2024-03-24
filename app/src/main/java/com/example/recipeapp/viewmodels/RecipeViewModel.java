package com.example.recipeapp.viewmodels;

import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.recipeapp.model.Recipe;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeViewModel {
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private MutableLiveData<List<Recipe>> recipeList = new MutableLiveData<>(new ArrayList<>());

    public void readRecipes(){
        FirebaseDatabase database = FirebaseDatabase
                .getInstance("https://recipeapp-1fba1-default-rtdb.firebaseio.com/");
        DatabaseReference recipesRef = database.getReference().child("recipes");

        List<Recipe> allRecipes = new ArrayList<>();
        recipesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot recipeSnapshot: snapshot.getChildren()){
                    String name = recipeSnapshot.child("name").getValue(String.class);
                    int calories = recipeSnapshot.child("calories").getValue(Integer.class);
                    String instructions = recipeSnapshot.child("instructions").getValue(String.class);
                    //Log.d("FirebaseData","Recipe: " + name + ", Calories: " + calories + ", Instructions: " + instructions);

                    List<String> ingredients = new ArrayList<>();
                    List<Integer> ingredientQuantities = new ArrayList<>();
                    DataSnapshot ingredientsSnapshot = recipeSnapshot.child("ingredients");

                    for (DataSnapshot ingredientSnapshot: ingredientsSnapshot.getChildren()){
                        String ingredientName = ingredientSnapshot.child("name").getValue(String.class);
                        Integer ingredientQuantity = ingredientSnapshot.child("quantity").getValue(Integer.class);
                        ingredients.add(ingredientName);
                        ingredientQuantities.add(ingredientQuantity);
                    }
                    Recipe recipe = new Recipe(name, calories, instructions, ingredients, ingredientQuantities);
                    allRecipes.add(recipe);
                }
                recipeList.setValue(allRecipes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Error reading data from Firebase: " + error.getMessage());
            }
        });
    }

    public LiveData<List<Recipe>> getRecipeLiveData(){
        return recipeList;
    }

}
