package com.example.a007fa.fitly;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.BitSet;
import java.util.Calendar;
import java.util.Date;

public class addActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    Button s_pick, e_pick, show;
    EditText textData;

    int day, month, year, hour, minute;
    int dayFinal, monthFinal, yearFinal, hourFinal, minuteFinal;
    int counter = 0;

    String time = "";

    static final String ACTION_WORKOUT = "com.fitly.action.WORKOUT";

    Calendar calendarOne = Calendar.getInstance(), calendarTwo = Calendar.getInstance();
    Calendar c = Calendar.getInstance();
    View view;

    Workout workout = new Workout();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addactivity);

        s_pick = (Button) findViewById(R.id.s_pick);
        e_pick = (Button) findViewById(R.id.e_pick);
        show = (Button) findViewById(R.id.show);

        textData = (EditText) findViewById(R.id.workoutName);

        s_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(addActivity.this, addActivity.this, year, month, day);
                datePickerDialog.show();
            }
        });

        e_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(addActivity.this, addActivity.this, year, month, day);
                datePickerDialog.show();
            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String workoutName = textData.getText().toString();
                if(calendarOne.compareTo(calendarTwo) > 0) {
                    workout.add(workoutName ,calendarTwo, calendarOne); // calendarOne is after calendarTwo in time
                    new Alarm().setAlarm(getApplicationContext(), (int) ((calendarTwo.getTimeInMillis() / 1000L) % Integer.MAX_VALUE) ,calendarTwo);
                } else if (calendarOne.compareTo(calendarTwo) < 0) {
                    workout.add(workoutName, calendarOne, calendarTwo);
                    new Alarm().setAlarm(getApplicationContext(), (int) ((calendarOne.getTimeInMillis() / 1000L) % Integer.MAX_VALUE) ,calendarOne);
                }
                Intent intent = new Intent(getApplicationContext(), fitlyHandler.class);
                intent.setAction(ACTION_WORKOUT);
                intent.putExtra("workout", workout);
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                setResult(Activity.RESULT_OK);
                finish();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        yearFinal = year;
        monthFinal = month;
        dayFinal = dayOfMonth;


        //Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(addActivity.this, addActivity.this, hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        hourFinal = hourOfDay;
        minuteFinal = minute;
        if(counter % 2 == 0){
            calendarOne.set(yearFinal, monthFinal, dayFinal, hourFinal, minuteFinal);
            counter++;
        } else if (counter % 2 == 1){
            calendarTwo.set(yearFinal, monthFinal, dayFinal, hourFinal, minuteFinal);
            counter++;
        }
    }
}
