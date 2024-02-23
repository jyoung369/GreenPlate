package com.example.recipeapp.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.example.recipeapp.HomeFragment;
import com.example.recipeapp.InputMealFragment;
import com.example.recipeapp.R;
import com.example.recipeapp.RecipeFragment;
import com.example.recipeapp.ShoppingListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class WelcomeActivity extends AppCompatActivity implements BottomNavigationView
        .OnNavigationItemSelectedListener{

    HomeFragment homeFragment = new HomeFragment();
    InputMealFragment inputMealFragment = new InputMealFragment();
    ShoppingListFragment shoppingListFragment = new ShoppingListFragment();
    RecipeFragment recipeFragment = new RecipeFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        BottomNavigationView navbar = findViewById(R.id.bottomNavigationView);
        navbar.setOnNavigationItemSelectedListener(this);
        navbar.setSelectedItemId(R.id.homeNavbarIcon);

    }

    @Override
    public boolean
    onNavigationItemSelected(MenuItem item)
    {
        int viewId = item.getItemId();
        if (viewId == R.id.homeNavbarIcon){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, homeFragment)
                    .commit();
            return true;

        } else if (viewId == R.id.shopping_listNavbarIcon){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, shoppingListFragment)
                    .commit();
            return true;

        } else if (viewId == R.id.inputMealNavbarIcon){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, inputMealFragment)
                    .commit();
            return true;
        } else if (viewId == R.id.recipeNavbarIcon){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, recipeFragment)
                    .commit();
            return true;
        } else {
            return false;
        }
    }
}