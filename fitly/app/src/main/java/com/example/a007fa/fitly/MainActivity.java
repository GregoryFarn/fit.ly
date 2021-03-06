package com.example.a007fa.fitly;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

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

    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Authentication
        // This should be moved to our login/signup activity
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mUserRef = FirebaseDatabase.getInstance().getReference("users").child(mUser.getUid());
        String key = "10282018"; // replace with a way to get today's date

        mUserRef.child("schedule").child(key).removeValue();
        mUserRef.child("schedule").child(key).push().setValue(new Workout("yes"));


        String email = "wenm@usc.edu";
        String password = "password";
        serviceStart();
        // To sign out, do FirebaseAuth.getInstance().signOut();

        // To set user data, do
        // FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        // DatabaseReference mUserRef = FirebaseDatabase.getInstance().getReference("users").child(mUser.getUid());
        // mUserRef.child("displayName").setValue("Mel Mel");

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
//                  ((TextView) findViewById(R.id.StepCountText)).setText("12");
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
}
