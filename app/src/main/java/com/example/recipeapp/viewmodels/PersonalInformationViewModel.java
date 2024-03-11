package com.example.recipeapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class PersonalInformationViewModel extends ViewModel {
    private MutableLiveData<Boolean> isSaveSuccessful = new MutableLiveData<>();
    public boolean validateHeight(String height) {
        return !height.isEmpty();
    }
    public boolean validateWeight(String weight) {
        return !weight.isEmpty();
    }
    public void savePersonalInformation(String email, String height, String weight, String gender) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("email", email);
        userData.put("height", height);
        userData.put("weight", weight);
        userData.put("gender", gender);

        DatabaseReference db = FirebaseDatabase.getInstance("https://recipeapp-1fba1-default-rtdb.firebaseio.com/")
                .getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            db.child("users").child(user.getUid()).setValue(userData)
                    .addOnSuccessListener(aVoid -> isSaveSuccessful.postValue(true))
                    .addOnFailureListener(e -> isSaveSuccessful.postValue(false));
        }
    }

    public LiveData<Boolean> getIsSaveSuccessful() {
        return isSaveSuccessful;
    }
}