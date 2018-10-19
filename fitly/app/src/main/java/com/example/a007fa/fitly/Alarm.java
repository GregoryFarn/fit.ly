package com.example.a007fa.fitly;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Alarm {
    private Context context;
    private long milliTime;
    private String time;
    private int requestCode;
    public Alarm(Context context, String time, int requestCode) {
        this.context=context;
        this.requestCode = requestCode;
        this.time = time;
    }

    private long calculateTime(){

        String myDate = this.time;
        Date date = null;
      //String myDate = "10/18/2018 19:42";
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");

        try{
            date = sdf.parse(myDate);
        }catch(ParseException e){
            e.printStackTrace();
        }
        // long milliTime = date.getTime();
        //       - 3600*3000;
        //  scheduled time - 3hrs in millisecond
        return date.getTime() - 3600*3000;
    }

    public void setAlarm() {
        AlarmManager am =  (AlarmManager)this.context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this.context, AlarmReceive.class);

        PendingIntent sender = PendingIntent.getBroadcast(context, this.requestCode, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(calculateTime());
        if (calendar.getTimeInMillis() > System.currentTimeMillis())
            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);

    }

}
