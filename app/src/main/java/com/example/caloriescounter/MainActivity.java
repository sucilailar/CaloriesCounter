package com.example.caloriescounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Data.DatabaseHandler;
import Model.Food;

public class MainActivity extends AppCompatActivity {

    private EditText foodName, foodCals;
    private Button submitButton;
    private DatabaseHandler dba;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dba = new DatabaseHandler(MainActivity.this);

        foodName = (EditText) findViewById(R.id.foodEditText);
        foodCals = (EditText) findViewById(R.id.caloriesEditText);
        submitButton = (Button) findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDatatoDB();
            }
        });
    }

    private void saveDatatoDB() {
        Food food = new Food();
        String name = foodName.getText().toString().trim();
        String calsString = foodCals.getText().toString().trim();



        if (name.equals("") || calsString.equals("")){
            Toast.makeText(getApplicationContext(), "No empty files allowed", Toast.LENGTH_LONG).show();
        }else{
            int cals = Integer.parseInt(calsString);
            food.setFoodName(name);
            food.setCalories(cals);

            dba.addFood(food);
            dba.close();

            //clear the form

            foodName.setText("");
            foodCals.setText("");
//running
            //take usert to next screen (display all entered items
                startActivity(new Intent(MainActivity.this, DisplayFoodItem.class));
        }
    }



}