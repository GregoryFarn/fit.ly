package com.example.a007fa.fitly;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

import com.example.a007fa.fitly.R;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mainNav;
    private FrameLayout mainFrame;

    private DashboardFragment dashboardFragment;
    private BadgeFragment badgeFragment;
    private ProfileFragment profileFragment;

    private LocalBroadcastManager bManager;

    static final String ACTION_FITLY = "com.fitly.action.FITLY";
    static final String ACTION_ENDDAY = "com.fitly.action.ENDDAY";
    static final String ACTION_BADGE = "com.fitly.action.BADGE";
    static final String ACTION_BIGBADGE = "com.fitly.action.BIGBADGE";
    static final String ACTION_BADGELIST = "com.fitly.action.BADGELIST";
    static final String ACTION_BADGEPAGE = "com.fitly.action.BADGEPAGE";
    static final String ACTION_SCHEDULE= "com.fitly.action.SCHEDULE";

    private float steps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Move this class to login/signup later
        // For now, load the entire roster here and search for user

//        String json = inputStreamToString(getApplicationContext().getResources().openRawResource(R.raw.users));
//        Log.d("json: ", json);
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        Users users = gson.fromJson(json, Users.class);

//        Log.d("users size: ", Integer.toString(users.size()));

        //test for notification
//        Calendar start1 = Calendar.getInstance();
//        start1.set(2018, 9, 22, 23, 57);
//        Calendar start2 = Calendar.getInstance();
//        start2.set(2018, 9, 22, 23, 58);
//        new Alarm().setAlarm(getApplicationContext(),(int) (new Date().getTime()/ 1000L) ,start1);
//        new Alarm().setAlarm(getApplicationContext(), (int) ((start2.getTimeInMillis() / 1000L) % Integer.MAX_VALUE) ,start2);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mainFrame = (FrameLayout) findViewById(R.id.mainFrame);
        mainNav = (BottomNavigationView) findViewById(R.id.navigation);

        dashboardFragment = new DashboardFragment();
        badgeFragment = new BadgeFragment();
        profileFragment = new ProfileFragment();

        setFragment(dashboardFragment);
        mainNav.setSelectedItemId(R.id.navigation_dashboard);

        mainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            TextView title = (TextView) findViewById(R.id.title);
            switch(menuItem.getItemId()) {
                case R.id.navigation_badges:
                    title.setText("Badges");
                    setFragment(badgeFragment);
                    return true;

                case R.id.navigation_dashboard:
                    title.setText("Dashboard");
                    setFragment(dashboardFragment);
                    return true;

                case R.id.navigation_profile:
                    title.setText("Profile");
                    setFragment(profileFragment);
                    return true;

                default:
                    return false;

            }
            }
        });


    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainFrame, fragment);
        fragmentTransaction.commit();
    }

    protected void serviceStart() {
        if (!isMyServiceRunning(fitlyHandler.class)) {
            Intent intent = new Intent(getApplicationContext(), fitlyHandler.class);
            startService(intent);
        }
    }

    private BroadcastReceiver bReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ACTION_FITLY)) {
                Bundle b = intent.getExtras();
                dashboardFragment.setArguments(b);
//
//                ((TextView) findViewById(R.id.StepCountText)).setText(Math.round(b.getFloat("stepCount")) + "/10,0000 steps");
//                steps = b.getFloat("stepCount");
//                Log.d("steps", Float.toString(b.getFloat("stepCount")));
            }
            else if (intent.getAction().equals(ACTION_ENDDAY)) {
                ((TextView) findViewById(R.id.StepCountText)).setText("12");
            }
            else if(intent.getAction().equals(ACTION_BADGE)){
//                ((TextView) findViewById(R.id.badgeCompleted)).setText("Badge Completed");
            }
            else if(intent.getAction().equals(ACTION_BIGBADGE)){
//                ((TextView) findViewById(R.id.badgeCompleted)).setText("Big Badge Completed");
            }
            else if(intent.getAction().equals(ACTION_BADGELIST)){
//                ((TextView) findViewById(R.id.badgeCompleted)).setText("Big Badge Completed");
            }
            else if(intent.getAction().equals(ACTION_BADGEPAGE)){
                Intent intent1 = new Intent(getApplicationContext(), BadgeFragment.class);
                intent1.putExtra("badgeList",intent.getSerializableExtra("badgeList"));
                startActivity(intent1);
            }
            else if(intent.getAction().equals(ACTION_SCHEDULE))
            {
                Schedule userSchedule = (Schedule) intent.getSerializableExtra("Schedule");
                userSchedule.initTest();
                Bundle schBundle= new Bundle();
                schBundle.putString("test", "testing string");
                //schBundle.putSerializable("sc", userSchedule);
                dashboardFragment.setArguments(schBundle);

                //Intent intent1 = new Intent(getApplicationContext(), DashboardFragment.class);
                //intent1.putExtra("Schedule",intent.getSerializableExtra("Schedule"));
                //startActivity(intent1);

            }
        }
    };



    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public String inputStreamToString(InputStream inputStream) {
        try {
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, bytes.length);
            String json = new String(bytes);
            return json;
        } catch (IOException e) {
            return null;
        }
    }
}
