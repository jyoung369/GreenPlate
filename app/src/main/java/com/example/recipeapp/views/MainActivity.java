package com.example.recipeapp.views;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.recipeapp.HomeFragment;
import com.example.recipeapp.R;
import com.example.recipeapp.ShoppingListFragment;
import com.example.recipeapp.databinding.ActivityMainBinding;
import com.example.recipeapp.model.ShoppingList;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity implements BottomNavigationView
        .OnNavigationItemSelectedListener{
    HomeFragment homeFragment = new HomeFragment();
    ShoppingListFragment shoppingListFragment = new ShoppingListFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navbar = findViewById(R.id.bottomNavigationView);
        navbar.setOnNavigationItemSelectedListener(this);
        navbar.setSelectedItemId(R.id.home);

    }

    @Override
    public boolean
    onNavigationItemSelected(MenuItem item)
    {
        int viewId = item.getItemId();
        if (viewId == R.id.home){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, homeFragment)
                    .commit();
            return true;

        } else if (viewId == R.id.shopping_list){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, shoppingListFragment)
                    .commit();
            return true;

        } else {
            return false;
        }
    }


}