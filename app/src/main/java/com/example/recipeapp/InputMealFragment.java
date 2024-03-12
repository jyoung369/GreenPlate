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

import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.example.recipeapp.model.Meal;
import com.example.recipeapp.viewmodels.MealViewModel;
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

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class InputMealFragment extends Fragment {
    public InputMealFragment newInstance() {
        return new InputMealFragment();
    }

    private EditText editTextHeight;
    private EditText editTextWeight;
    private Spinner spinnerGender;
    private EditText mealDate;
    private EditText calories;
    private EditText mealName;
    private AnyChartView dataVisual1;
    private Calendar calendar;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input_meal, container, false);

        return view;

    }
  
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mealName = view.findViewById(R.id.mealName);
        calories = view.findViewById(R.id.calorieCount);
        mealDate = view.findViewById(R.id.mealDate);
        //dataVisual1 (displaying user's avg daily calorie intake for current month)
        dataVisual1 = (AnyChartView) view.findViewById(R.id.data_visual_1);
        calendar = Calendar.getInstance();
        MealViewModel vm = new MealViewModel();

        mealDate.setOnClickListener(v -> showDatePickerDialog());

        super.onViewCreated(view, savedInstanceState);
        super.onCreate(savedInstanceState);

        Button input = view.findViewById(R.id.inputButton);
        input.setOnClickListener(v -> vm.inputMeal(requireContext(), mealName, calories, mealDate));

        //Find references to the buttons in the inflated layout
        Button button1 = view.findViewById(R.id.button1);
        Button button2 = view.findViewById(R.id.button2);

        Cartesian cartesian = AnyChart.column();
        Column column = cartesian.column(new ArrayList<>());

        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("${%Value}{groupsSeparator: }");

        cartesian.animation(true);

        //make current month into string
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        String currMonthName = new DateFormatSymbols().getMonths()[currentMonth - 1];
        cartesian.title("Daily Caloric Intake for " + currMonthName);

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("${%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        cartesian.xAxis(0).title("Days");
        cartesian.yAxis(0).title("Calories per day");

        vm.getData().observe(getViewLifecycleOwner(), info -> {
            System.out.println("bye");
            List<DataEntry> dataList = new ArrayList<>();

            for (HashMap.Entry<String, Integer> element : info.entrySet()) {
                dataList.add(new ValueDataEntry(element.getKey(), element.getValue()));
            }
            column.data(dataList);
            System.out.println("chart set?");
            System.out.println(dataVisual1 == null); //not null tested

            dataVisual1.setVisibility(View.VISIBLE);
            dataVisual1.setChart(cartesian);
            cartesian.draw(true);
            System.out.println("AnyChartView visibility: " + dataVisual1.getVisibility());
            System.out.println("yo");
        });

        // Set click listener for Button 1 for data visual 1
        button1.setOnClickListener(v -> {
            HashMap<String, Integer> data = new HashMap<>();
            vm.readMeals(data);
        });
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
}