package com.example.a007fa.fitly;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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

public class MainActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    Button s_pick, e_pick, show;
    TextView workoutNameView, startTimeView, endTimeView;
    EditText textData;

    int day, month, year, hour, minute;
    int dayFinal, monthFinal, yearFinal, hourFinal, minuteFinal;
    int counter = 0;

    String time = "";

    Calendar c = Calendar.getInstance();
    View view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addactivity);

        //String myDate = this.time;
        //Date date = null;
        //String myDate = "10/18/2018 19:42";
/*        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");

        try{
            date = sdf.parse(myDate);
        }catch(ParseException e){
            e.printStackTrace();
        }*/



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

                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, MainActivity.this, year, month, day);
                datePickerDialog.show();
            }
        });

        e_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, MainActivity.this, year, month, day);
                datePickerDialog.show();
            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String workoutName = textData.getText().toString();
                String startTime = time.substring(0, time.indexOf('M'));
                String endTime = time.substring(time.indexOf('M'), time.length());
                startTime = startTime + "M";
                endTime = endTime.substring(1, endTime.length());
                workoutNameView.setText(workoutName);
                startTimeView.setText(startTime);
                endTimeView.setText(endTime);
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

        TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, MainActivity.this, hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        hourFinal = hourOfDay;
        minuteFinal = minute;
        String period = "N/A", sminute = "N/A", startTime = "N/A", endTime = "N/A";

        if(hourFinal == 0){
            hourFinal += 12;
            period = "AM";
        } else if (hourFinal > 12){
            hourFinal -= 12;
            period = "PM";
        } else if(hourFinal == 12) {
            period = "PM";
        } else {
            period = "AM";
        }

        if( minuteFinal == 1 || minuteFinal == 2
                || minuteFinal == 3 || minuteFinal == 4 || minuteFinal == 5 || minuteFinal == 6 || minuteFinal == 7 || minuteFinal == 8 || minuteFinal == 9){
            sminute = Integer.toString(minute);
            sminute = "0" + sminute;
        } else{
            sminute = Integer.toString(minute);
        }
        if(counter == 2){
            time = "";
            counter = 0;
        }
        time += monthFinal + "/" + dayFinal + "/" + yearFinal + " " + hourFinal + ":" + sminute + ":" + period;
        counter +=1;
    }
}
