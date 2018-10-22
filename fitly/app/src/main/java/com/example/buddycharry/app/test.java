package com.example.a007fa.fitly;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class test extends AppCompatActivity {
    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        Workout workout = (Workout)this.getIntent().getExtras().get("workout");

        //String string = getIntent().getStringExtra("workout");

        TextView workoutNameView = findViewById(R.id.workoutNameView);
        String string = workout.getWorkoutName();
        workoutNameView.setText(string);
    }
}
