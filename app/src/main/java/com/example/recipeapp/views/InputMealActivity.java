package com.example.recipeapp.views;


import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.example.recipeapp.viewmodels.MealViewModel;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.recipeapp.R;
import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputMealActivity extends AppCompatActivity {


    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private EditText mealName = findViewById(R.id.mealName);
    private EditText calories = findViewById(R.id.calorieCount);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("CREATINGGGGGGGGG");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_meal);
//        Calendar calendar = Calendar.getInstance();
//        AnyChartView dataVisual1 = (AnyChartView) findViewById(R.id.data_visual_1);
//        MealViewModel vm1 = new MealViewModel();
//
//        Cartesian cartesian = AnyChart.column();
//        Column column = cartesian.column(new ArrayList<>());
//
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
//        vm1.getData().observe(this, info -> {
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
//
//        HashMap<String, Integer> data = new HashMap<>();
//        vm1.readMeals(data);
    }
//    private void inputMeal() {
//        String nameOfMeal = mealName.getText().toString();
//        String cals = calories.getText().toString();
//        if (nameOfMeal.isEmpty()) {
//            mealName.setError("Please enter the name of your meal!");
//        } else if (cals.isEmpty()) {
//            calories.setError("Please enter the amount of calories in your meal!");
//        } else {
//            Map<String, Object> mealData = new HashMap<>();
//            mealData.put("name", nameOfMeal);
//            mealData.put("calorie count", cals);
//            mealData.put("userid", user.getUid());
//
//            FirebaseDatabase database = FirebaseDatabase.getInstance("https://recipeapp-1fba1-default-rtdb.firebaseio.com/");
//            DatabaseReference mealsref = database.getReference().child("meals");
//
//            mealsref.child(user.getUid())
//                    .setValue(mealData)
//                    .addOnSuccessListener(success -> {
//                        Toast.makeText(InputMealActivity.this,
//                                "Meal inputted successfully!", Toast.LENGTH_SHORT).show();
//                    })
//                    .addOnFailureListener(failure -> {
//                        Toast.makeText(InputMealActivity.this,
//                                "Could not input meal", Toast.LENGTH_SHORT).show();
//                    });
//        }
}