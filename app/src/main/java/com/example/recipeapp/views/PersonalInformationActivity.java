package com.example.recipeapp.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.recipeapp.R;

public class PersonalInformationActivity extends AppCompatActivity {
    private EditText editTextHeight;
    private EditText editTextWeight;
    private Spinner spinnerGender;
    private Button buttonSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_personal_information);

        editTextHeight = findViewById(R.id.editTextHeight);
        editTextWeight = findViewById(R.id.editTextWeight);
        spinnerGender = findViewById(R.id.spinnerGender);
        buttonSave = findViewById(R.id.buttonSave);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);

        // Set click listener for the save button
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePersonalInformation();
            }
        });
    }
    private void savePersonalInformation() {
        // Get the user input from EditTexts and Spinner
        String height = editTextHeight.getText().toString();
        String weight = editTextWeight.getText().toString();
        String gender = spinnerGender.getSelectedItem().toString();

    }
}