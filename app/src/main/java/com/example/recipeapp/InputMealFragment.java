package com.example.recipeapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.example.recipeapp.viewmodels.MealViewModel;
import com.example.recipeapp.viewmodels.PersonalInformationViewModel;


//imports necessary to use AnyChart
import com.anychart.AnyChartView;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
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
        PersonalInformationViewModel userVM = new PersonalInformationViewModel();
        TextView defaultText = view.findViewById(R.id.defaultText);
        TextView heightText = view.findViewById(R.id.heightText);
        TextView weightText = view.findViewById(R.id.weightText);
        TextView genderText = view.findViewById(R.id.genderText);
        TextView goalText = view.findViewById(R.id.calorieGoalText);
        TextView dailyCalorieText = view.findViewById(R.id.dailyCalorieText);

        //Height Observer
        userVM.getHeight().observe(getViewLifecycleOwner(), height -> {
            if (height != null && !(height.isEmpty())) {
                defaultText.setVisibility(View.GONE);
                heightText.setVisibility(View.VISIBLE);
                String heightMessage = "Height: " + height + " cm";
                heightText.setText(heightMessage);
                calorieCalc(heightText, weightText, genderText, goalText);
            }
        });

        //Weight Observer
        userVM.getWeight().observe(getViewLifecycleOwner(), weight -> {
            if (weight != null && !(weight.isEmpty())) {
                defaultText.setVisibility(View.GONE);
                weightText.setVisibility(View.VISIBLE);
                String weightMessage = "Weight: " + weight + " kg";
                weightText.setText(weightMessage);
                calorieCalc(heightText, weightText, genderText, goalText);
            }
        });

        //Gender Observer
        userVM.getGender().observe(getViewLifecycleOwner(), gender -> {
            if (gender != null && !(gender.isEmpty())) {
                defaultText.setVisibility(View.GONE);
                genderText.setVisibility(View.VISIBLE);
                String genderMessage = "Gender: " + gender;
                genderText.setText(genderMessage);
                calorieCalc(heightText, weightText, genderText, goalText);
            }
        });

        //Daily Calorie Count Observer
        vm.getDailyCount().observe(getViewLifecycleOwner(), count -> {
            dailyCalorieText.setText("Calories Inputted Today: " + count);
        });

        userVM.readUserInfo();
        vm.readDailyMeals();
        mealDate.setOnClickListener(v -> showDatePickerDialog());

        super.onViewCreated(view, savedInstanceState);
        super.onCreate(savedInstanceState);

        Button input = view.findViewById(R.id.inputButton);
        input.setOnClickListener(v -> vm.inputMeal(requireContext(), mealName, calories, mealDate));

        //Find references to the buttons in the inflated layout
        Button button1 = view.findViewById(R.id.button1);
        Button button2 = view.findViewById(R.id.button2);

//        Cartesian cartesian = AnyChart.column();
//        Column column = cartesian.column(new ArrayList<>());

//        column.tooltip()
//                .titleFormat("{%X}")
//                .position(Position.CENTER_BOTTOM)
//                .anchor(Anchor.CENTER_BOTTOM)
//                .offsetX(0d)
//                .offsetY(5d)
//                .format("${%Value}{groupsSeparator: }");
//
//        cartesian.animation(true);
//
//        //make current month into string
//        int currentMonth = calendar.get(Calendar.MONTH) + 1;
//        String currMonthName = new DateFormatSymbols().getMonths()[currentMonth - 1];
//        cartesian.title("Daily Caloric Intake for " + currMonthName);
//
//        cartesian.yScale().minimum(0d);
//
//        cartesian.yAxis(0).labels().format("${%Value}{groupsSeparator: }");
//
//        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
//        cartesian.interactivity().hoverMode(HoverMode.BY_X);
//
//        cartesian.xAxis(0).title("Days");
//        cartesian.yAxis(0).title("Calories per day");
//
//        vm.getData().observe(getViewLifecycleOwner(), info -> {
//            System.out.println("bye");
//            List<DataEntry> dataList = new ArrayList<>();
//
//            for (HashMap.Entry<String, Integer> element : info.entrySet()) {
//                dataList.add(new ValueDataEntry(element.getKey(), element.getValue()));
//            }
//            column.data(dataList);
//            System.out.println("chart set?");
//            System.out.println(dataVisual1 == null); //not null tested
//
//            dataVisual1.setVisibility(View.VISIBLE);
//            dataVisual1.setChart(cartesian);
//            cartesian.draw(true);
//            System.out.println("AnyChartView visibility: " + dataVisual1.getVisibility());
//            System.out.println("yo");
//        });
        ArrayList<String> dataKeys = new ArrayList<>();
        ArrayList<Integer> dataValues = new ArrayList<>();
        vm.getData().observe(getViewLifecycleOwner(), info -> {
            for (HashMap.Entry<String, Integer> element : info.entrySet()) {
                dataKeys.add(element.getKey());
                dataValues.add(element.getValue());
            }
        });

        HashMap<String, Integer> data = new HashMap<>();
        vm.readMeals(data);

        // Set click listener for Button 1 for data visual 1
        button1.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(getActivity(), InputMealActivity.class);
                intent.putStringArrayListExtra("dataKeys", dataKeys);
                intent.putIntegerArrayListExtra("dataValues", dataValues);
                startActivity(intent);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
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
    private void calorieCalc(TextView heightText, TextView weightText, TextView genderText, TextView goalText) {
        //The equation that we will be using to calculate calorie goal is the Mifflin-St Jeor
        //formula: https://www.calculator.net/calorie-calculator.html
        //The equation for men is BMR = 10W + 6.25H - 5A + 5 and BMR = 10W + 6.25H - 5A - 161 for women
        // where W represents weight, H represents height,and A represents age. Since we are not storing age,
        // we will use the average age in the
        // US in 2022 of 38.9 years. This statistic was reported by the U.S. Census Bureau:
        // https://www.census.gov/newsroom/press-releases/2023/population-estimates-characteristics.html
        // The number of calories will be for a person at rest to maintain their current weight. Note: Most people will not be at rest the entire day.
        // The calculator states than the result is multiplied by a value between 1.2 and 1.95
        // to represent a person's activity level. For this calculation, we will just multiply
        // by 1.5 which falls in the range.
        // Since we are storing calories as ints, we will be rounded the final calculation up to
        // the nearest int.
        if (heightText.getVisibility() == View.VISIBLE && weightText.getVisibility() == View.VISIBLE && genderText.getVisibility() == View.VISIBLE) {
            String heightTotal = (String) heightText.getText();
            String[] parsedHeight = heightTotal.split(" ");
            double height = Double.parseDouble(parsedHeight[1]);
            String weightTotal = (String) weightText.getText();
            String[] parsedWeight = weightTotal.split(" ");
            double weight = Double.parseDouble(parsedWeight[1]);
            String genderTotal = (String) genderText.getText();
            String[] parsedGender = genderTotal.split(" ");
            String gender = parsedGender[1];
            System.out.println(weight);
            System.out.println(height);
            System.out.println(gender);
            double calories = (10 * weight) + (6.25 * height) - (5 * 38.9);
            if (gender.equals("Male")) {
                calories = calories + 5;
            } else if (gender.equals("Female")) {
                calories = calories - 161;
            }
            int finalCalories = (int) Math.ceil(calories);
            goalText.setVisibility(View.VISIBLE);
            goalText.setText("Min Daily Calorie Goal: " + finalCalories);
        }
    }

}
