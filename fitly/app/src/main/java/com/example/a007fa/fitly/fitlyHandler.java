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
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class fitlyHandler extends Service implements SensorEventListener {
    private SensorManager sManager;
    private Sensor stepSensor;
    private float stepsFirst;
    private float steps;
    private float stepsTemp;
    private boolean first;
    private Schedule userSchedule;
    private AlarmManager alarm;
    private PendingIntent alarmIntent;
    private boolean isAccelerometerOn;
    static final String ACTION_FITLY = "com.fitly.action.FITLY";
    static final String ACTION_ENDDAY = "com.fitly.action.ENDDAY";
    static final String ACTION_BADGE = "com.fitly.action.BADGE";
    static final String ACTION_BIGBADGE = "com.fitly.action.BIGBADGE";
    static final String ACTION_BADGELIST = "com.fitly.action.BADGELIST";
    static final String ACTION_BADGEPAGE = "com.fitly.action.BADGEPAGE";
    static final String ACTION_SCHEDULELIST = "com.fitly.action.SCHEDULELIST";
    static final String ACTION_SCHEDULEPAGE = "com.fitly.action.SCHEDULEPAGE";
    static final String ACTION_WORKOUT = "com.fitly.action.WORKOUT";
    static final String ACTION_CALORIES = "com.fitly.action.CALORIES";
    static final String ACTION_CALCOUNT = "com.fitly.action.CALCOUNT";
    static final String ACTION_CONSUMED = "com.fitly.action.CONSUMED";
    static final String ACTION_PERMISSION= "com.fitly.action.PERMISSION";
    static final String ACTION_DONE = "com.fitly.action.DONE";
    static final String ACTION_EAT = "com.fitly.action.EAT";
    static final String ACTION_STOP = "com.fitly.action.STOP";
    private ArrayList<Badge> badges;
    private boolean badgeAcheived;
    private Schedule sched;
    private ArrayList<Workout> incomplete;
    private ArrayList<Workout> complete;
    private float caloriesBurned;
    private float caloriesBurnedSteps;
    private float calConsumed;
    private ActivityRecord currentRec;
    private FirebaseUser mUser;
    private DatabaseReference mUserRef;
    public  List<Workout> out;
    public void onCreate() {
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mUserRef = FirebaseDatabase.getInstance().getReference("users").child(mUser.getUid());
        Log.d(TAG, "mUser: " + mUser.getUid());

        bManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_BADGELIST);
        intentFilter.addAction(ACTION_BADGEPAGE);
        intentFilter.addAction(ACTION_SCHEDULELIST);
        intentFilter.addAction(ACTION_WORKOUT);
        intentFilter.addAction(ACTION_ENDDAY);
        intentFilter.addAction(ACTION_CALORIES);
        intentFilter.addAction(ACTION_CONSUMED);
        intentFilter.addAction(ACTION_PERMISSION);
        intentFilter.addAction(ACTION_DONE);
        intentFilter.addAction("ACTION_STOP");
        bManager.registerReceiver(bReceiver, intentFilter);

        badges = new ArrayList<>();
        incomplete = new ArrayList<>();
        complete = new ArrayList<>();
        sched = new Schedule();
        out= new ArrayList<>();
        populateBadges();
        populateSched();

        incomplete = new ArrayList<>(sched.getWorkouts());

        Intent intent1 = new Intent(getApplicationContext(), DashboardFragment.class);
        intent1.setAction(ACTION_SCHEDULEPAGE);
        intent1.putExtra("sched",sched);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent1);

        sManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        stepSensor = sManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        first = true;
        steps=0;
        caloriesBurned = 0;
        caloriesBurnedSteps = 0;
        calConsumed = 0;
        badgeAcheived = false;

        String key = "10282018"; // replace with a way to get today's date

        DatabaseReference stepsRef = mUserRef.child("steps").child(key);
        stepsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                stepsTemp = (float)(((Number)dataSnapshot.getValue()).doubleValue());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        DatabaseReference pedometerRef = mUserRef.child("pedometerOn");
        pedometerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    changeSensor(dataSnapshot.getValue(boolean.class));

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        startSmallBadge();
        sendStepMessage();
        sendCalMessage();

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());

        currentRec = new ActivityRecord(c);
        new Alarm(this, (int) ((c.getTimeInMillis() / 1000L) % Integer.MAX_VALUE), c).setAlarmEndDay();
    }

    public void populateBadges(){
        for(int i=0; i<2; i++)
        {
            Badge badgeTest= new Badge("small", true);
            if(i%7==0)
                badgeTest.setTypeOfBadge("big");
            badgeTest.setCompleted(true);
            badges.add(badgeTest);
        }

    }
    public void populateSched(){
/*
        // Initialize workouts array to display
        String key = "10282018"; // replace with a way to get today's date
        DatabaseReference workoutsRef = mUserRef.child("schedule").child(key);
        workoutsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<Workout>> gti = new GenericTypeIndicator<List<Workout>>() {};
                List<Workout> wm = dataSnapshot.getValue(gti);

                if (wm == null) {
                    return;
                }
                if (wm != null && wm.size() != 0) {
                    Log.d(TAG, wm.toString());
                    Log.d("wm size", Integer.toString(wm.size()));
                    for (Workout entry : wm) {
                        Workout w = new Workout(entry.getWorkoutName(), entry.getStart(), entry.getEnd(), entry.getLocation(), entry.getDescription());
                        sched.addWorkout(w);
                        Log.d("Entry " + w.getWorkoutName(), entry.getStart() + " to " + entry.getEnd());
                        Log.d("Workout " + w.getWorkoutName(), w.getStart() + " to " + w.getEnd());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });*/
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

    protected void sendCalMessage() {
        Intent intent = new Intent(getApplicationContext(), DashboardFragment.class);
        intent.setAction(ACTION_CALCOUNT);
        intent.putExtra("calCount", caloriesBurnedSteps +caloriesBurned);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    protected void sendEatMessage() {
        Intent intent = new Intent(getApplicationContext(), DashboardFragment.class);
        intent.setAction(ACTION_EAT);
        intent.putExtra("calCount", calConsumed);
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
        Bundle b = new Bundle();
        intent1.setAction(ACTION_SCHEDULEPAGE);
        b.putSerializable("sched",sched);
        intent1.putExtras(b);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent1);
    }

    protected void changeSensor(boolean perm){
        if(perm){
            sManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_NORMAL);
            Toast.makeText(getApplicationContext(), "Pedometer On", Toast.LENGTH_SHORT).show();
        }
        else{
            sManager.unregisterListener(this);
            Toast.makeText(getApplicationContext(), "Pedometer Off", Toast.LENGTH_SHORT).show();
        }
        mUserRef.child("pedometerOn").setValue(perm);
    }

    public void onSensorChanged(SensorEvent event) {
        if (first) {
            stepsFirst = event.values[0];
            first = false;
        } else {
            steps = event.values[0] - stepsFirst;
            caloriesBurnedSteps = Math.round(steps/20);


            //sendStepMessage();
            mUserRef.child("steps").child("10282018").setValue(stepsTemp+1);
            sendCalMessage();

        }

        if(steps>= 10000 && !badges.get(badges.size()-1).completed){
            badgeAcheived = true;
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

    public void updateDatabase(){
        currentRec.setStepCount(Math.round(steps));
        currentRec.setBadgeAcheived(badgeAcheived);
        currentRec.setTotalCalories(Math.round(calConsumed));
        currentRec.setCompletedWorkouts(Workout.listToMap(complete));
        currentRec.setIncompleteWorkouts(Workout.listToMap(incomplete));
        mUserRef.child("activityRecords").push().setValue(currentRec.toMap());

    }

    public void resetCurrentRec(){

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

                final Workout nw = (Workout)intent.getSerializableExtra("workout");
                final String key = "10282018"; // replace with a way to get today's date
                DatabaseReference workoutsRef = mUserRef.child("schedule").child(key);
                workoutsRef.push().setValue(nw);


            }
            else if (intent.getAction().equals(ACTION_ENDDAY)) {
                updateDatabase();
            }
            else if (intent.getAction().equals(ACTION_STOP)) {
                stopSelf();
            }
            else if (intent.getAction().equals(ACTION_CALORIES)) {
                caloriesBurned += intent.getIntExtra("calories",0);
            }
            else if (intent.getAction().equals(ACTION_CONSUMED)) {
                calConsumed += intent.getIntExtra("calories",0);
                sendEatMessage();
            }else if (intent.getAction().equals(ACTION_PERMISSION)){
                Bundle b = intent.getExtras();
                isAccelerometerOn =b.getBoolean("permission");

                Log.w("ABCDE: ",  Boolean.toString(isAccelerometerOn));

                changeSensor(isAccelerometerOn);
            }
            else if (intent.getAction().equals(ACTION_DONE)){
                Bundle b = intent.getExtras();
                Workout w =(Workout)b.getSerializable("workout");
                for(Workout x: incomplete) {
                    if(x.getStartTime().equals(w.getStartTime())){
                        complete.add(x);
                        incomplete.remove(x);
                        sched.removeWorkout(x);
                        break;
                    }
                }
                mUserRef.child("completed").child("102818").push().setValue(w);

                Intent intent1 = new Intent(getApplicationContext(), DashboardFragment.class);
                intent1.setAction(ACTION_SCHEDULEPAGE);
                intent1.putExtra("workout", w);
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent1);
            }

        }
    };
    LocalBroadcastManager bManager;

    public void onDestroy() {
        bManager.unregisterReceiver(bReceiver);
            super.onDestroy();
    }


}
