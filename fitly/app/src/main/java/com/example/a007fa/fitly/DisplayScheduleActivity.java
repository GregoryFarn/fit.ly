package com.example.a007fa.fitly;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class DisplayScheduleActivity extends AppCompatActivity {

    private ListView scheduleDisplay;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_badges:
//                    scheduleDisplay.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
//                    scheduleDisplay.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_profile:
//                    scheduleDisplay.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_schedule);
        Log.d("tag", "in oncreate for displayscheduleactivity");

        final Schedule sched = new Schedule();
        sched.initTest();

        scheduleDisplay = findViewById(R.id.scheduleDisplay);

        DisplayScheduleAdapter adapter = new DisplayScheduleAdapter(this,
                R.layout.adapter_view_layout,
                sched.getWorkouts());

        scheduleDisplay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(DisplayScheduleActivity.this, DisplayWorkoutDetailsActivity.class);
                intent.putExtra("Name", sched.getWorkouts().get(i).getWorkoutName());
                intent.putExtra("Location", sched.getWorkouts().get(i).getLocation());

                Log.d("name", sched.getWorkouts().get(i).getWorkoutName() );
                Log.d("location", sched.getWorkouts().get(i).getLocation());
                startActivity(intent);
            }
        });

        scheduleDisplay.setAdapter(adapter);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
}
