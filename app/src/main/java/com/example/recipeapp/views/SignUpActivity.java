package com.example.recipeapp.views;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.recipeapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText signupEmail;
    private EditText signupPassword;
    private Button signupButton;
    // private TextView loginRedirectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();
        signupEmail = findViewById(R.id.enterUsername);
        signupPassword = findViewById(R.id.enterPassword);
        signupButton = findViewById(R.id.createAccount);
        // loginRedirectText = findViewById(R.id.)

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = signupEmail.getText().toString().trim();
                String pass = signupPassword.getText().toString().trim();

                if (user.isEmpty()) {
                    signupEmail.setError("Email cannot be empty");
                }
                if (pass.isEmpty()) {
                    signupPassword.setError("Password cannot be empty.");
                } else {
                    OnCompleteListener<AuthResult> completeListener = task -> {
                        Log.d(TAG, "debug");
                        if (task.isSuccessful()) {
                            FirebaseUser loggedInUser = FirebaseAuth.getInstance().getCurrentUser();
                            Toast.makeText(SignUpActivity.this,
                                    "Sign Up Successful", Toast.LENGTH_SHORT).show();
                            Map<String, Object> userData = new HashMap<>();
                            userData.put("email", user);
                            userData.put("height", "");
                            userData.put("weight", "");
                            userData.put("gender", "");
                            FirebaseDatabase database = FirebaseDatabase.getInstance("https://recipeapp-1fba1-default-rtdb.firebaseio.com/");
                            DatabaseReference db = database.getReference();
                            assert loggedInUser != null;
                            db.child("users").child(loggedInUser.getUid())
                                    .setValue(userData);
                            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                        } else {
                            Toast.makeText(SignUpActivity.this,
                                    "Sign Up Failed"
                                            + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    };

                    auth.createUserWithEmailAndPassword(user, pass)
                            .addOnCompleteListener(completeListener);
                }
            }
        });
    }
}
