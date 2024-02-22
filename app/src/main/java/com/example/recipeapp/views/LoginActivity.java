package com.example.recipeapp.views;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.recipeapp.R;

public class LoginActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button signinButton = findViewById(R.id.signinButton);
        signinButton.setOnClickListener(view -> {
            EditText username = findViewById(R.id.emailUsername);
            EditText password = findViewById(R.id.editTextTextPassword);
            if (username.getText().toString().isEmpty() || password.getText().toString().isEmpty() || username == null || password == null) {
                // don't do anything

            } else {
                // go to firebase: attempt to log in user

            }
        });
        Button createAnAccount = findViewById(R.id.accountCreation);
        createAnAccount.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        Button exitButton = findViewById(R.id.exitApp);
        exitButton.setOnClickListener(view -> {
            finishAffinity();
        });
    }
}
