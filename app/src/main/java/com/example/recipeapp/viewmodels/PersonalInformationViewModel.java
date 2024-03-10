package com.example.recipeapp.viewmodels;

import androidx.lifecycle.ViewModel;

public class PersonalInformationViewModel extends ViewModel {
    public boolean validateHeight(String height) {
        if (height.isEmpty()) {
            return false;
        }
        return true;
    }
    public boolean validateWeight(String weight) {
        if (weight.isEmpty()) {
            return false;
        }
        return true;
    }
}