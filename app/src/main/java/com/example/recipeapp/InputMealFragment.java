package com.example.recipeapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.DatePicker;

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

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class InputMealFragment extends Fragment {

    public InputMealFragment() {
        // Required empty public constructor
    }

    private EditText editTextHeight;
    private EditText editTextWeight;
    private Spinner spinnerGender;
    private EditText mealDate;
    private EditText calories;
    private EditText mealName;
    private Calendar calendar;

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
        mealName = view.findViewById(R.id.mealName);
        calories = view.findViewById(R.id.calorieCount);
        mealDate = view.findViewById(R.id.mealDate);
        calendar = Calendar.getInstance();

        mealDate.setOnClickListener(v -> showDatePickerDialog());

        super.onViewCreated(view, savedInstanceState);
        super.onCreate(savedInstanceState);
        Button input = view.findViewById(R.id.inputButton);
        input.setOnClickListener(v -> inputMeal(mealName, calories, mealDate));
    }

    //opens up a calendar and allows user to select which date to input meal for
    private void showDatePickerDialog() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, year1, monthOfYear, day1) -> {
                    calendar.set(year1, monthOfYear, day1);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    String formattedDate = dateFormat.format(calendar.getTime());
                    mealDate.setText(formattedDate);
                },
                year,
                month,
                day
        );

        datePickerDialog.show();
    }

    class Meal {

        private String name;
        private String calories;

        private String date;

        public Meal (String name, String calories, String date) {
            this.name = name;
            this.calories = calories;
            this.date = date;
        }
        public String getMealName() {
            return name;
        }
        public String getCalories() {
            return calories;
        }

        public String getDate() {
            return date;
        }
    }
    private void inputMeal(EditText mealName, EditText calories, EditText mealDate) {
        String nameOfMeal = mealName.getText().toString();
        String cals = calories.getText().toString();
        String date = mealDate.getText().toString();

        if (nameOfMeal.isEmpty()) {
            mealName.setError("Please enter the name of your meal!");
        } else if (cals.isEmpty()) {
            calories.setError("Please enter the amount of calories in your meal!");
        } else if (date.isEmpty()) {
            mealDate.setError("Please enter when you had your meal!");
        }
        else {
            Meal myMeal = new Meal(nameOfMeal, cals, date);
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
