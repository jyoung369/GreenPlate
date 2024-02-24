package com.example.recipeapp.views;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.recipeapp.HomeFragment;
import com.example.recipeapp.R;
import com.example.recipeapp.ShoppingListFragment;
import com.example.recipeapp.databinding.ActivityMainBinding;
import com.example.recipeapp.model.ShoppingList;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity{

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.splash_screen);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen); // Set the layout for your splash screen

        // Use a handler to navigate to the LoginActivity after a certain delay
        new android.os.Handler().postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Finish the SplashActivity to prevent going back to it
        }, 2000); // Set the delay time in milliseconds (e.g., 2000ms or 2 seconds)
    }
}