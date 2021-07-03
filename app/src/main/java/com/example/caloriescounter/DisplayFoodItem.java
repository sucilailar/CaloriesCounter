package com.example.caloriescounter;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import Data.CustomListViewAdapter;
import Data.DatabaseHandler;
import Model.Food;
import Util.Utils;

public class DisplayFoodItem extends AppCompatActivity {
    private DatabaseHandler dba;
    private ArrayList<Food> dbFoods = new ArrayList<>();
    private CustomListViewAdapter foodAdapter;
    private ListView listView;

    private Food myFood;
    private TextView totalCals, totalFoods;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_food);

        listView = (ListView) findViewById(R.id.list);
        totalCals = (TextView) findViewById(R.id.totalAmountTextView);
        totalFoods = (TextView) findViewById(R.id.totalItemsTextView);
    
        refreshData();
    }

    private void refreshData(){
        dbFoods.clear();
        dba = new DatabaseHandler(getApplicationContext());
        ArrayList<Food> foodsFromDB = dba.getFoods();
        int calsValue = dba.totalCalories();
        int totalItems = dba.getTotalItems();

        String formattedValue = Utils.formatNumber(calsValue);
        String formattedItems = Utils.formatNumber(totalItems);

        totalCals.setText("Total Calories: " + formattedValue);
        totalFoods.setText("Total Calories: " + formattedItems);

        for (int i = 0; i < foodsFromDB.size(); i++){
            String name = foodsFromDB.get(i).getFoodName();
            String dateText = foodsFromDB.get(i).getRecordDate();
            int cals = foodsFromDB.get(i).getCalories();
            int foodId = foodsFromDB.get(i).getFoodId();

            Log.v("FOOD IDS: ", String.valueOf(foodId));

            myFood = new Food();
            myFood.setFoodName(name);
            myFood.setRecordDate(dateText);
            myFood.setFoodId(foodId);
            myFood.setCalories(cals);

            dbFoods.add(myFood);

        }

        dba.close();

//setup adapter

        foodAdapter = new CustomListViewAdapter(DisplayFoodItem.this, R.layout.list_row, dbFoods);
        listView.setAdapter(foodAdapter);
        foodAdapter.notifyDataSetChanged();
    }


}


