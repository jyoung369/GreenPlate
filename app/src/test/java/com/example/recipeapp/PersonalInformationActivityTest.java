package com.example.recipeapp;

import org.junit.Test;
import static org.junit.Assert.*;

import com.example.recipeapp.viewmodels.PersonalInformationViewModel;

public class PersonalInformationActivityTest {

    @Test
    public void validateInputs_withEmptyHeight_returnsFalse() {
        PersonalInformationViewModel activity = new PersonalInformationViewModel();
        assertFalse(activity.validateHeight(""));
    }

    @Test
    public void validateInputs_valid() {
        PersonalInformationViewModel activity = new PersonalInformationViewModel();
        assertTrue(activity.validateWeight("124"));
    }
}
