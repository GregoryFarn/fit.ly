package com.example.a007fa.fitly;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.content.LocalBroadcastManager;
import android.os.SystemClock;
import android.os.IBinder;

import java.util.Calendar;
import java.util.ArrayList;

public class fitlyHandler extends Service implements SensorEventListener {
    private SensorManager sManager;
    private Sensor stepSensor;
    private float stepsFirst;
    private float steps;
    private boolean first;
    private Schedule userSchedule;
    private AlarmManager alarm;
    private PendingIntent alarmIntent;
    static final String ACTION_FITLY = "com.fitly.action.FITLY";
    static final String ACTION_ENDDAY = "com.fitly.action.ENDDAY";
    static final String ACTION_BADGE = "com.fitly.action.BADGE";
    static final String ACTION_BIGBADGE = "com.fitly.action.BIGBADGE";
    static final String ACTION_BADGELIST = "com.fitly.action.BADGELIST";
    static final String ACTION_BADGEPAGE = "com.fitly.action.BADGEPAGE";
    static final String ACTION_SCHEDULELIST = "com.fitly.action.SCHEDULELIST";
    static final String ACTION_SCHEDULEPAGE = "com.fitly.action.SCHEDULEPAGE";
    static final String ACTION_WORKOUT = "com.fitly.action.WORKOUT";
    private ArrayList<Badge> badges;
    private Schedule sched;

    public void onCreate() {
        bManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_BADGELIST);
        intentFilter.addAction(ACTION_BADGEPAGE);
        intentFilter.addAction(ACTION_SCHEDULELIST);
        intentFilter.addAction(ACTION_WORKOUT);
        intentFilter.addAction(ACTION_ENDDAY);
        bManager.registerReceiver(bReceiver, intentFilter);

        badges = new ArrayList<>();
        sched = new Schedule();
        populateBadges();
        populateSched();

        Intent intent1 = new Intent(getApplicationContext(), DashboardFragment.class);
        intent1.setAction(ACTION_SCHEDULEPAGE);
        intent1.putExtra("sched",sched);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent1);

        startSmallBadge();
        sendStepMessage();

        first = true;
        sManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        stepSensor = sManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        steps = 0;
        sManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_NORMAL);

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());

       // Alarm.setAlarmEndDay(getApplicationContext(), 104);
    }

    public void populateBadges(){
        for(int i=0; i<1; i++)
        {
            Badge badgeTest= new Badge("small", false);
            if(i%7==0)
                badgeTest.setTypeOfBadge("big");
            badgeTest.setCompleted(true);
            badges.add(badgeTest);
        }
        Badge badgesTest=new Badge("small",false);
        badges.add(badgesTest);

    }
    public void populateSched(){
        sched.initTest();
    }

    public void startSmallBadge(){
        badges.add(new Badge("small",false));
    }
    public IBinder onBind(Intent intent) {
        return null;
    }


    protected void sendStepMessage() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setAction(ACTION_FITLY);
        intent.putExtra("stepCount", steps);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    protected void sendBadgeMessage() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setAction(ACTION_BADGE);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    protected void sendBigBadgeMessage() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setAction(ACTION_BIGBADGE);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    protected void sendBadgeListMessage() {
        Intent intent1 = new Intent(getApplicationContext(), BadgeFragment.class);
        intent1.setAction(ACTION_BADGEPAGE);
        badgeWrapper b = new badgeWrapper(badges);
        //Bundle bun = new Bundle();
        // bun.putSerializable("badgeList",b);
        intent1.putExtra("badgeList",b);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent1);
    }

    protected void sendSchedMessage() {
        Intent intent1 = new Intent(getApplicationContext(), DashboardFragment.class);
        intent1.setAction(ACTION_SCHEDULEPAGE);
        intent1.putExtra("sched",sched);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent1);
    }

    public void onSensorChanged(SensorEvent event) {
        if (first) {
            stepsFirst = event.values[0];
            first = false;
        } else {
            steps = event.values[0] - stepsFirst;
            sendStepMessage();
        }

        if(steps>= 10000 && !badges.get(badges.size()-1).completed){
            badges.get(badges.size()-1).setCompleted(true);
            int count =0;
            for(int i = badges.size()-1; i>=badges.size()-6; i--){
                if(badges.get(i).completed){
                    count++;
                }
            }
            sendBadgeMessage();
            if(count ==7){
                badges.add(new Badge("big",true));
                sendBigBadgeMessage();
            }
            sendBadgeListMessage();

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    private BroadcastReceiver bReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ACTION_BADGELIST)) {
                sendBadgeListMessage();
            }
            else if (intent.getAction().equals(ACTION_SCHEDULELIST)) {
                sendSchedMessage();
            }
            else if (intent.getAction().equals(ACTION_WORKOUT)) {
                sched.addWorkout((Workout)intent.getSerializableExtra("workout"));
                sendSchedMessage();
            }
            else if (intent.getAction().equals(ACTION_ENDDAY)) {
                startSmallBadge();
                sendBadgeListMessage();
               // Alarm.setAlarmEndDay(getApplicationContext(), 104);
            }
        }
    };
    LocalBroadcastManager bManager;

    public void onDestroy() {
        bManager.unregisterReceiver(bReceiver);
        super.onDestroy();
    }
}
