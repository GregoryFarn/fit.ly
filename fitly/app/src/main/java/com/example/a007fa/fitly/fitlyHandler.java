package com.example.a007fa.fitly;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;
import android.support.v4.content.LocalBroadcastManager;

import java.util.Calendar;

import android.os.IBinder;

public class fitlyHandler extends Service implements SensorEventListener {
    private SensorManager sManager;
    private Sensor stepSensor;
    private float stepsFirst;
    private float steps;
    private boolean first;
    private AlarmManager alarm;
    private PendingIntent alarmIntent;
    static final String ACTION_FITLY = "com.fitly.action.FITLY";

    public void onCreate() {
        first = true;
        sManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        stepSensor = sManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        steps = 0;
        sManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_NORMAL);
       /* alarm = (AlarmManager)getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), Dashboard.class);
        intent.setAction(ACTION_FITLY);
        alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 24);

        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, alarmIntent);*/
        sendMessage();
    }

    public IBinder onBind(Intent intent) {

        return null;
    }


    protected void sendMessage() {
        Intent intent = new Intent(getApplicationContext(), Dashboard.class);
        intent.setAction(ACTION_FITLY);
        intent.putExtra("stepCount", steps);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    public void onSensorChanged(SensorEvent event) {
        if (first) {
            stepsFirst = event.values[0];
            first = false;
        } else {
            steps = event.values[0] - stepsFirst;
            sendMessage();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
