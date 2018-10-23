package com.example.buddycharry.app

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
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
    TextView workoutNameView, startTimeView, endTimeView;
    EditText textData;

    int day, month, year, hour, minute;
    int dayFinal, monthFinal, yearFinal, hourFinal, minuteFinal;
    int counter = 0;

    String time = "", startData = "", endData = "";

    Calendar startCalendar = Calendar.getInstance(), endCalendar = Calendar.getInstance();
    Calendar c = Calendar.getInstance();
    View view;

    Workout workout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addactivity);

        s_pick = (Button) findViewById(R.id.s_pick);
        e_pick = (Button) findViewById(R.id.e_pick);
        show = (Button) findViewById(R.id.show);

        textData = (EditText) findViewById(R.id.workoutName);

        workoutNameView = (TextView) findViewById(R.id.workoutNameView);
        endTimeView = (TextView) findViewById(R.id.endTimeView) ;
        startTimeView = (TextView) findViewById(R.id.startTimeView);

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
//                String startTime = time.substring(0, time.indexOf('M'));
//                String endTime = time.substring(time.indexOf('M'), time.length());
//                startTime = startTime + "M";
//                endTime = endTime.substring(1, endTime.length());
//
//                workoutNameView.setText(workoutName);
//                Date date = startCalendar.getTime();
//                String time = Long.toString(date.getTime());
//                startTimeView.setText(time);
//                date = endCalendar.getTime();
//                time = Long.toString(date.getTime());
//                endTimeView.setText(time);
                    workout = new Workout(workoutName);
                    workout.addCalendars(startCalendar, endCalendar);
                    Intent intent = new Intent(addActivity.this, test.class);
                    intent.putExtra("workout", workout);
                    startActivity(intent);
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        yearFinal = year;
        monthFinal = month + 1;
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

//        if(counter == 0){
//            startData = monthFinal + "/" + dayFinal + "/" + yearFinal + " " + hourFinal;
//            counter += 1;
//            startCalendar.set(yearFinal, monthFinal, dayFinal, hourFinal, minuteFinal);
//        } else if (counter == 1){
//            endData = monthFinal + "/" + dayFinal + "/" + yearFinal + " " + hourFinal;
//            counter += 1;
//            endCalendar.set(yearFinal, monthFinal, dayFinal, hourFinal, minuteFinal);
//        } else {
//            counter = 0;
//            startData = "";
//            endData = "";
//        }
    }
}

