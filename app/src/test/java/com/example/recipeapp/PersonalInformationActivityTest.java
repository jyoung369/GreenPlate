package com.example.recipeapp;

import org.junit.Test;
import static org.junit.Assert.*;

import com.example.recipeapp.viewmodels.PersonalInformationViewModel;

public class PersonalInformationActivityTest {

    @Test
    public void validateInputs_withEmptyHeight_returnsFalse() {
        Util activity = new Util();
        assertFalse(activity.validateHeight(""));
    }

    @Test
    public void validateInputs_valid() {
        Util activity = new Util();
        assertTrue(activity.validateWeight("124"));
    }
}
