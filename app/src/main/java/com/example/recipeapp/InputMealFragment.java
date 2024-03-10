package com.example.recipeapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.recipeapp.views.InputMealActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


//imports necessary to use AnyChart
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
import com.anychart.core.cartesian.series.Column;

import java.util.ArrayList;
import java.util.List;


public class InputMealFragment extends Fragment {

    public InputMealFragment() {
        // Required empty public constructor
    }

    private EditText editTextHeight;
    private EditText editTextWeight;
    private Spinner spinnerGender;

    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input_meal, container, false);

        /**
        AnyChartView dataVisual1 = view.findViewById(R.id.any_chart_view);

        dataVisual1.setProgressBar(view.findViewById(R.id.progress_bar));

        Cartesian cartesian = AnyChart.column();

        List<DataEntry> data = new ArrayList<>();
        //example of column chart (avg caloric intake per month)
        data.add(new ValueDataEntry("Rouge", 80540));
        data.add(new ValueDataEntry("Foundation", 94190));
        data.add(new ValueDataEntry("Mascara", 102610));
        data.add(new ValueDataEntry("Lip gloss", 110430));
        data.add(new ValueDataEntry("Lipstick", 128000));
        data.add(new ValueDataEntry("Nail polish", 143760));
        data.add(new ValueDataEntry("Eyebrow pencil", 170670));
        data.add(new ValueDataEntry("Eyeliner", 213210));
        data.add(new ValueDataEntry("Eyeshadows", 249980));

        Column column = cartesian.column(data);
        */
        return view;

    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        EditText mealName = view.findViewById(R.id.mealName);
        EditText calories = view.findViewById(R.id.calorieCount);
        super.onViewCreated(view, savedInstanceState);
        super.onCreate(savedInstanceState);
        Button input = view.findViewById(R.id.inputButton);
        input.setOnClickListener(v -> inputMeal(mealName, calories));
    }

    class Meal {

        private String name;
        private String calories;
        public Meal (String name, String calories) {
            this.name = name;
            this.calories = calories;
        }
        public String getMealName() {
            return name;
        }
        public String getCalories() {
            return calories;
        }
    }
    private void inputMeal(EditText mealName, EditText calories) {
        String nameOfMeal = mealName.getText().toString();
        String cals = calories.getText().toString();
        if (nameOfMeal.isEmpty()) {
            mealName.setError("Please enter the name of your meal!");
        } else if (cals.isEmpty()) {
            calories.setError("Please enter the amount of calories in your meal!");
        } else {
            Meal myMeal = new Meal(nameOfMeal, cals);

            FirebaseDatabase database = FirebaseDatabase
                    .getInstance("https://recipeapp-1fba1-default-rtdb.firebaseio.com/");
            DatabaseReference mealsref = database.getReference().child("meals/"
                    + user.getUid());

            mealsref.push().setValue(myMeal)
                    .addOnSuccessListener(success -> {
                        Toast.makeText(requireContext(),
                                "Meal inputted successfully!", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(failure -> {
                        Toast.makeText(requireContext(),
                                "Could not input meal", Toast.LENGTH_SHORT).show();
                    });
        }
    }

}
