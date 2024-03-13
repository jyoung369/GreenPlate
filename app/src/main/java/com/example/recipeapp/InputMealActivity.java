package com.example.recipeapp;


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
import com.example.recipeapp.views.WelcomeActivity;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.content.Intent;
import android.os.Bundle;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class InputMealActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_meal);

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(back -> {
            Intent intent = new Intent(InputMealActivity.this, WelcomeActivity.class);
            startActivity(intent);
        });

        Calendar calendar = Calendar.getInstance();
        AnyChartView dataVisual1 = (AnyChartView) findViewById(R.id.data_visual_1);
        dataVisual1.setProgressBar(findViewById(R.id.progress_bar));

        ArrayList<String> dataKeys = getIntent().getStringArrayListExtra("dataKeys");
        ArrayList<Integer> dataValues = getIntent().getIntegerArrayListExtra("dataValues");

        List<DataEntry> dataList = new ArrayList<>();

        for (int i = 0; i < dataKeys.size(); i++) {
            dataList.add(new ValueDataEntry(dataKeys.get(i), dataValues.get(i)));
        }

        Cartesian cartesian = AnyChart.column();
        Column column = cartesian.column(dataList);

        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("${%Value}{groupsSeparator: }");

        cartesian.animation(true);
        cartesian.title("Top 10 cosmetic products");

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

        dataVisual1.setChart(cartesian);
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
    //          }
}