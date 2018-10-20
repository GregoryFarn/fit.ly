package com.example.a007fa.fitly;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DisplayScheduleActivity extends AppCompatActivity {

    private ListView scheduleDisplay;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    scheduleDisplay.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
//                    scheduleDisplay.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
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

        Schedule sched = new Schedule();
        sched.initTest();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DisplayScheduleActivity.this,
                android.R.layout.simple_list_item_1,
                sched.getStringArray());

        scheduleDisplay = findViewById(R.id.listView);
        scheduleDisplay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(DisplayScheduleActivity.this, DisplayWorkoutDetailsActivity.class);
                intent.putExtra("Workout", scheduleDisplay.getItemAtPosition(i).toString());
//                Log.d("tag", scheduleDisplay.getItemAtPosition(i).toString());
                startActivity(intent);
            }
        });

        scheduleDisplay.setAdapter(adapter);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
}
