package com.example.recipeapp.views;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recipeapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity{

    private FirebaseAuth auth;
    private EditText loginEmail, loginPassword;
    private TextView signupRedirectText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        EditText username = findViewById(R.id.emailUsername);
        EditText password = findViewById(R.id.editTextTextPassword);

        Button signinButton = findViewById(R.id.signinButton);
        signinButton.setOnClickListener(view -> {
            String email = username.getText().toString();
            String pass = password.getText().toString();
            if (username.getText().toString().isEmpty() || password.getText().toString().isEmpty() || username == null || password == null) {
                // don't do anything
                Log.d(TAG, "enter your credentials");
            } else {
                // go to firebase: attempt to log in user
                auth.signInWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                });
                Log.d(TAG, "Try to log in user");
            }

        });
        Button createAnAccount = findViewById(R.id.accountCreation);
        createAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

        Button exitButton = findViewById(R.id.exitApp);
        exitButton.setOnClickListener(view -> {
            finishAffinity();
        });
    }
}
