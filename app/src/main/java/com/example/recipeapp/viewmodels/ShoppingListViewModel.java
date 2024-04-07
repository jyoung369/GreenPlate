package com.example.recipeapp.viewmodels;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.recipeapp.model.Ingredient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShoppingListViewModel extends ViewModel {
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private MutableLiveData<Boolean> isSaveSuccessful = new MutableLiveData<>();

    private List<Ingredient> allIngredients = new ArrayList<>();

    private MutableLiveData<List<Ingredient>> shoppingList = new MutableLiveData<>();
    public LiveData<List<Ingredient>> getShoppingList(){
        return shoppingList;
    }

    public ShoppingListViewModel() {
        // Initialize the shoppingList if needed
    }

    public void addItem(Context context, String name, Integer quantity, Integer caloriesPerServing, String expirationDate){
        FirebaseDatabase shoppinglistDB = FirebaseDatabase
                .getInstance("https://recipeapp-1fba1-default-rtdb.firebaseio.com/");
        DatabaseReference shoppinglistRef = shoppinglistDB.getReference().child("shoppinglist/"
                + user.getUid());
        shoppinglistRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean existsInList = false;
                boolean shouldRemove = false;
                String duplicateIngredientId = "";
                Ingredient ingredient = new Ingredient(name, quantity, caloriesPerServing, expirationDate);
                Ingredient duplicateIngredient = ingredient;
                allIngredients = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String ingredientId = dataSnapshot.getKey();
                    Ingredient currIngredient = dataSnapshot.getValue(Ingredient.class);
                    allIngredients.add(currIngredient);

                    if (currIngredient.getName().equals(name) && quantity > 0) {
                        // modify
                        existsInList = true;
                        shouldRemove = false;
                        duplicateIngredientId = ingredientId;
                        duplicateIngredient = currIngredient;

                    } else if (currIngredient.getName().equals(name) && quantity <= 0) {
                        // remove
                        existsInList = true;
                        shouldRemove = true;
                        duplicateIngredientId = ingredientId;
                        duplicateIngredient = currIngredient;
                    }
                }
                shoppingList.setValue(allIngredients);

                if (existsInList && !shouldRemove) {
                    shoppinglistRef.child(duplicateIngredientId).setValue(ingredient)
                            .addOnSuccessListener(
                                    aVoid -> isSaveSuccessful.postValue(true))
                            .addOnFailureListener(
                                    e -> isSaveSuccessful.postValue(false));
                    List<Ingredient> currList = shoppingList.getValue();
                    System.out.println("modify: previous ingredient: "+ duplicateIngredient);
                    System.out.println("modify: new ingredient" + ingredient);
                    currList.remove(duplicateIngredient); // remove previous
                    currList.add(ingredient); // add new
                    shoppingList.setValue(currList);
                }
                if (existsInList && shouldRemove) {
                    shoppinglistRef.child(duplicateIngredientId).removeValue()
                            .addOnSuccessListener(
                                    aVoid -> isSaveSuccessful.postValue(true))
                            .addOnFailureListener(
                                    e -> isSaveSuccessful.postValue(false));
                    List<Ingredient> currList = shoppingList.getValue();
                    currList.remove(duplicateIngredient);
                    System.out.println("modify: previous ingredient: "+ duplicateIngredient);
                    shoppingList.setValue(currList);
                }
                if (!existsInList && quantity > 0) {
                    // add to database
                    shoppinglistRef.push().setValue(ingredient)
                            .addOnSuccessListener(success -> {
                                Toast.makeText(context,
                                        "Ingredient inputted successfully!", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(failure -> {
                                Toast.makeText(context,
                                        "Could not input ingredient", Toast.LENGTH_SHORT).show();
                            });
                } else if (!existsInList && quantity <= 0) {
                    Toast.makeText(context, "Cannot add negative or zero quantity of ingredient to list", Toast.LENGTH_SHORT).show();
                }
            }
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Error reading data from Firebase: " + error.getMessage());
            }
        });
    }

    public void readShoppingList(){
        FirebaseDatabase shoppinglistDB = FirebaseDatabase
                .getInstance("https://recipeapp-1fba1-default-rtdb.firebaseio.com/");
        DatabaseReference shoppinglistRef = shoppinglistDB.getReference().child("shoppinglist/"
                + user.getUid());
        shoppinglistRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String ingredientName = dataSnapshot.child("name").getValue(String.class);
                    Integer ingredientQuantity = dataSnapshot.child("quantity")
                            .getValue(Integer.class);
                    Integer calories = dataSnapshot.child("caloriesPerServing")
                            .getValue(Integer.class);
                    String expirationDate = dataSnapshot.child("expirationDate")
                            .getValue(String.class);
                    if (ingredientQuantity > 0) {
                        // ingredientList.add(ingredientName);
                        allIngredients.add(new Ingredient(ingredientName,
                                ingredientQuantity, calories, expirationDate));
                    }
                    Log.d("FirebaseData",
                            "Ingredient Name: " + ingredientName + ", Quantity: "
                                    + ingredientQuantity + ", Calories: "
                                    + calories + ", expirationDate: " + expirationDate);
                }
                shoppingList.setValue(allIngredients);
            }
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Error reading data from Firebase: " + error.getMessage());
            }
        });
    }
}
