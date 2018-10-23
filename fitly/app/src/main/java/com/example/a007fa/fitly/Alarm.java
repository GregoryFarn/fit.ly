package com.example.a007fa.fitly;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.ALARM_SERVICE;

public class Alarm {

//    public Alarm(Context context, String time, int requestCode) {
//        this.context=context;
//        this.requestCode = requestCode;
//        this.time = time;
//    }
/*
    private long static calculateTime(){

        String myDate = this.time;
        Date date = new Date();
      //String myDate = "10/18/2018 19:42";
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");

        try{
            date = sdf.parse(myDate);
        }catch(ParseException e){
            e.printStackTrace();
        }
        long second = ((date.getTime()/1000) % 60) * 1000;
        //  scheduled time - 3hrs in millisecon
     //   return date.getTime() - 3600*3000;
    }*/

    public static void setAlarm(Context context, int requestCode, Calendar startTime) {
/*
        String myDate = time;
        Date date = new Date();
        //String myDate = "10/18/2018 19:42";
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");

        try{
            date = sdf.parse(myDate);
        }catch(ParseException e){
            e.printStackTrace();
        }
        long second = ((date.getTime()/1000) % 60) * 1000;
        //  scheduled time - 3hrs in millisecon
        //   return date.getTime() - 3600*3000;
*/
        AlarmManager am =  (AlarmManager)context.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent( context, Notifications.class);
        Bundle bundle = new Bundle();
        bundle.putInt("requestKey", requestCode);
        intent.putExtras(bundle);
        PendingIntent sender = PendingIntent.getBroadcast(context,requestCode, intent, 0);

        Calendar notiTime = Calendar.getInstance();
        notiTime.setTimeInMillis(startTime.getTimeInMillis()- 3600*3000);
       if (notiTime.getTimeInMillis() > System.currentTimeMillis())
        am.setExact(AlarmManager.RTC_WAKEUP, notiTime.getTimeInMillis(), sender);
    }

}
