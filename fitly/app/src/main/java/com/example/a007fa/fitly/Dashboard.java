package com.example.a007fa.fitly;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class Dashboard extends AppCompatActivity {

    static final String ACTION_FITLY = "com.fitly.action.FITLY";
    static final String ACTION_ENDDAY = "com.fitly.action.ENDDAY";
    static final String ACTION_BADGE = "com.fitly.action.BADGE";
    static final String ACTION_BIGBADGE = "com.fitly.action.BIGBADGE";
    static final String ACTION_BADGELIST = "com.fitly.action.BADGELIST";
    static final String ACTION_BADGEPAGE = "com.fitly.action.BADGEPAGE";

    float steps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        //test for notification
        new Alarm().setAlarm(getApplicationContext(), 0 ,"10/21/2018 20:10");
        new Alarm().setAlarm(getApplicationContext(), 1 ,"10/21/2018 20:11");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Schedule sched = new Schedule();
        sched.initTest();

        ListView scheduleDisplay = findViewById(R.id.scheduleDisplay);

        DisplayScheduleAdapter adapter = new DisplayScheduleAdapter(this,
                R.layout.adapter_view_layout,
                sched.getWorkouts());

        scheduleDisplay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Dashboard.this, DisplayWorkoutDetailsActivity.class);
                intent.putExtra("Name", sched.getWorkouts().get(i).getWorkoutName());
                intent.putExtra("Location", sched.getWorkouts().get(i).getLocation());

                Log.d("name", sched.getWorkouts().get(i).getWorkoutName() );
                Log.d("location", sched.getWorkouts().get(i).getLocation());
                startActivity(intent);
            }
        });

        scheduleDisplay.setAdapter(adapter);

        bManager = LocalBroadcastManager.getInstance(getApplicationContext());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_FITLY);
        intentFilter.addAction(ACTION_ENDDAY);
        intentFilter.addAction(ACTION_BADGE);
        intentFilter.addAction(ACTION_BADGEPAGE);
        bManager.registerReceiver(bReceiver, intentFilter);
        serviceStart();
    }

    protected void serviceStart() {
        if (!isMyServiceRunning(fitlyHandler.class)) {
            Intent intent = new Intent(getApplicationContext(), fitlyHandler.class);
            startService(intent);
        }
    }

    public void goToBadges(View view) {
        Intent intent = new Intent(getApplicationContext(), fitlyHandler.class);
        intent.setAction(ACTION_BADGELIST);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    public void goToSchedule(View view) {
        Intent intent = new Intent(getApplicationContext(), DisplayScheduleActivity.class);
        startActivity(intent);
    }

    private BroadcastReceiver bReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ACTION_FITLY)) {
                Bundle b = intent.getExtras();
                ((TextView) findViewById(R.id.StepCountText)).setText(Math.round(b.getFloat("stepCount")) + "/10,00s0 steps");
                steps = b.getFloat("stepCount");
            }
            else if (intent.getAction().equals(ACTION_ENDDAY)) {
                ((TextView) findViewById(R.id.StepCountText)).setText("12");
            }
            else if(intent.getAction().equals(ACTION_BADGE)){
                ((TextView) findViewById(R.id.badgeCompleted)).setText("Badge Completed");
            }
            else if(intent.getAction().equals(ACTION_BIGBADGE)){
                ((TextView) findViewById(R.id.badgeCompleted)).setText("Big Badge Completed");
            }
            else if(intent.getAction().equals(ACTION_BADGEPAGE)){
                Intent intent1 = new Intent(getApplicationContext(), DisplayBadges.class);
                intent1.putExtra("badgeList",intent.getSerializableExtra("badgeList"));
                startActivity(intent1);
            }
        }
    };

    LocalBroadcastManager bManager;

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
