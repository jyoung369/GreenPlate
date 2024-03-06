package com.example.recipeapp.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.recipeapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class PersonalInformationActivity extends AppCompatActivity {
    private EditText editTextHeight;
    private EditText editTextWeight;
    private Spinner spinnerGender;
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_personal_information);

        editTextHeight = findViewById(R.id.editTextHeight);
        editTextWeight = findViewById(R.id.editTextWeight);
        spinnerGender = findViewById(R.id.spinnerGender);
        Button buttonSave = findViewById(R.id.buttonSave);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);

        buttonSave.setOnClickListener(v -> savePersonalInformation());
    }
    private void savePersonalInformation() {
        String height = editTextHeight.getText().toString().trim();
        String weight = editTextWeight.getText().toString().trim();
        String gender = spinnerGender.getSelectedItem().toString();
        if (height.isEmpty()) {
            editTextHeight.setError("Please enter your height");
            editTextHeight.requestFocus();
            return;
        }
        if (weight.isEmpty()) {
            editTextWeight.setError("Please enter your weight");
            editTextWeight.requestFocus();
            return;
        }
        Map<String, Object> userData = new HashMap<>();
        userData.put("height", height);
        userData.put("weight", weight);
        userData.put("gender", gender);
        String uid = user.getUid();

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://recipeapp-1fba1-default-rtdb.firebaseio.com/");
        DatabaseReference db = database.getReference();
        db.child("users").child(uid).setValue(userData)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(PersonalInformationActivity.this,
                            "Information saved", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(PersonalInformationActivity.this,
                            "Failed to save information", Toast.LENGTH_SHORT).show();
                });
    }
}