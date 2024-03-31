package com.example.recipeapp.viewmodels;
import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.recipeapp.model.Ingredient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class PantryViewModel {
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private Calendar calendar;
    private MutableLiveData<ArrayList<String>> ingList = new MutableLiveData<>(new ArrayList<>());
    public LiveData<ArrayList<String>> getData() {
        return ingList;
    }
    public void inputIngredient(Context context, EditText ingredientName,
                                EditText quantity, EditText caloriesPerServing,
                                Button expirationDate) {
        String ingredientName1 = ingredientName.getText().toString();
        String strIngredientQuantity = quantity.getText().toString();
        String strIngredientCalories = caloriesPerServing.getText().toString();
        String expDate = expirationDate.getText().toString();
        int ingredientQuantity = Integer.parseInt(quantity.getText().toString());
        int ingredientCalories = Integer.parseInt(caloriesPerServing.getText().toString());
        if (ingredientName1.isEmpty()) {
            ingredientName.setError("Please enter the name of your ingredient!");
        } else if (strIngredientQuantity.isEmpty()) {
            quantity.setError("Please enter the quantity of your ingredient!");
        } else if (ingredientQuantity <= 0) {
            quantity.setError("Please enter a valid quantity!");
        } else if (strIngredientCalories.isEmpty()) {
            caloriesPerServing.setError(
                    "Please enter the calories per serving for this ingredient!");
        } else {
            ingredientName.setError(null);
            quantity.setError(null);
            caloriesPerServing.setError(null);
            expirationDate.setError(null);
            Ingredient newIngredient = new Ingredient(ingredientName1, ingredientQuantity,
                    ingredientCalories, expDate);
            FirebaseDatabase database = FirebaseDatabase
                    .getInstance("https://recipeapp-1fba1-default-rtdb.firebaseio.com/");
            DatabaseReference pantryDB = database.getReference().child("pantry/"
                    + user.getUid());

            pantryDB.push().setValue(newIngredient)
                    .addOnSuccessListener(success -> {
                        Toast.makeText(context,
                                "Ingredient inputted successfully!", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(failure -> {
                        Toast.makeText(context,
                                "Could not input ingredient", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    public void readIngredients(ArrayList<String> ingredientList) {
        FirebaseDatabase ingDB = FirebaseDatabase
                .getInstance("https://recipeapp-1fba1-default-rtdb.firebaseio.com/");
        DatabaseReference pantryDB = ingDB.getReference().child("pantry/"
                + user.getUid());
        pantryDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String ingredientName = dataSnapshot.child("name").getValue(String.class);
                    Integer ingredientQuantity = dataSnapshot.child("quantity")
                            .getValue(Integer.class);
                    if (ingredientQuantity > 0) {
                        ingredientList.add(ingredientName);
                    }
                    Log.d("FirebaseData",
                            "Ingredient Name: " + ingredientName + ", Quantity: "
                                    + ingredientQuantity);
                }
                ingList.setValue(ingredientList);
            }
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Error reading data from Firebase: " + error.getMessage());
            }
        });
    }

    public void showDatePickerDialog(Context context, Button expDate) {
        calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        System.out.println("mello");
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,
                (view, year1, monthOfYear, day1) -> {
                    calendar.set(year1, monthOfYear, day1);
                    SimpleDateFormat dateFormat = new SimpleDateFormat(
                            "dd-MM-yyyy", Locale.getDefault());
                    String formattedDate = dateFormat.format(calendar.getTime());
                    expDate.setText(formattedDate);
                },
                year,
                month,
                day
        );
        System.out.println("hello");
        datePickerDialog.show();
    }

}