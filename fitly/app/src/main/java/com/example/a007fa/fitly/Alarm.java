package com.example.a007fa.fitly;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.content.LocalBroadcastManager;

import java.util.Calendar;


import static android.content.Context.ALARM_SERVICE;

public class Alarm {

    private Calendar starTime = Calendar.getInstance();
    private Context context;
    private boolean isAlarmSet = false;
    private int requestCode;
    public Alarm(Context context, int requestCode, Calendar starTime) {
        this.context=context;
        this.requestCode = requestCode;
        this.starTime = starTime;
    }

    public long calculateTime(){
        Calendar notiTime = Calendar.getInstance();
        notiTime.setTimeInMillis(this.starTime.getTimeInMillis()- 3600*3000);

        return notiTime.getTimeInMillis();
    }

    static final String ACTION_FITLY = "com.fitly.action.FITLY";
    static final String ACTION_RESET = "com.fitly.action.RESET";
    static final String ACTION_SAVE = "com.fitly.action.SAVE";
    static final String ACTION_ENDDAY = "com.fitly.action.ACTION_ENDDAY";

    public void setAlarm() {
        AlarmManager am =  (AlarmManager)context.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent( context, Notifications.class);
        Bundle bundle = new Bundle();
        bundle.putInt("requestKey", requestCode);
        intent.putExtras(bundle);
        PendingIntent sender = PendingIntent.getBroadcast(context,requestCode, intent, 0);

        if (this.calculateTime() > System.currentTimeMillis()) {
            am.setExact(AlarmManager.RTC_WAKEUP, this.calculateTime(), sender);
            this.isAlarmSet = true;
        }
        else
            this.isAlarmSet = false;

    }

    public boolean isAlarmSet(){
        return isAlarmSet;
    }

    public void setAlarmEndDay() {

        AlarmManager am =  (AlarmManager)context.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent( context, saveAlarm.class);
        Bundle bundle = new Bundle();
        bundle.putInt("requestKey", requestCode);
        intent.putExtras(bundle);
        PendingIntent sender = PendingIntent.getBroadcast(context,requestCode, intent, 0);

        Calendar notiTime = Calendar.getInstance();

        notiTime.setTimeInMillis(System.currentTimeMillis()+5000);
        /*notiTime.set(Calendar.HOUR_OF_DAY,23);
        notiTime.set(Calendar.MINUTE,59);*/
        if (notiTime.getTimeInMillis() > System.currentTimeMillis())
            am.setRepeating(AlarmManager.RTC_WAKEUP, notiTime.getTimeInMillis(),AlarmManager.INTERVAL_DAY, sender);


    }

}