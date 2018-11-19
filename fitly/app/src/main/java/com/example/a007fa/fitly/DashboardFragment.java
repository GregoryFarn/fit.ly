package com.example.a007fa.fitly;


import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class DashboardFragment extends Fragment {
    private FirebaseUser mUser;
    private DatabaseReference mUserRef;

    static final String ACTION_FITLY = "com.fitly.action.FITLY";
    static final String ACTION_ENDDAY = "com.fitly.action.ENDDAY";
    static final String ACTION_SCHEDULELIST = "com.fitly.action.SCHEDULELIST";
    static final String ACTION_SCHEDULEPAGE = "com.fitly.action.SCHEDULEPAGE";
    static final String ACTION_CALCOUNT = "com.fitly.action.CALCOUNT";


    private View view;
    private final String TAG = "Dashboard fragment";

    float steps;
    float calories;
    public DashboardFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mUserRef = FirebaseDatabase.getInstance().getReference("users").child(mUser.getUid());
        Log.d(TAG, "mUser: " + mUser.getUid());




        DatabaseReference dbrTotalCaloriesConsumed = mUserRef.child("activityRecords").child("totalCaloriesConsumed");
        final int[] totalCaloriesConsumed = new int[1];
        dbrTotalCaloriesConsumed.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                setTotalCaloriesConsumedText(Integer.toString(dataSnapshot.getValue(int.class)));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        LocalBroadcastManager bManager = LocalBroadcastManager.getInstance(getActivity().getApplicationContext());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_FITLY);
        intentFilter.addAction(ACTION_SCHEDULEPAGE);
        intentFilter.addAction(ACTION_CALCOUNT);
        bManager.registerReceiver(bReceiver, intentFilter);
        serviceStart();

        Button activityButton = view.findViewById(R.id.addWorkoutButton);
        activityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), addActivity.class);
                startActivity(intent);
            }
        });

        Button calorieButton = view.findViewById(R.id.AddCaloriesButton);
        calorieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddCalories.class);
                startActivity(intent);
            }
        });

        steps=0;
        calories=0;
        sendStepMessage();
        sendCalMessage();
        sendSchedList();

        return view;
    }

    public void setTotalCaloriesConsumedText(String text){
        TextView textView = (TextView) getView().findViewById(R.id.CalorieConsumedCountText);
        textView.setText(text);
    }


    public void displaySchedule(final Schedule sched) {
        String key = "10282018"; // replace with a way to get today's date
//        ArrayList<Workout> workouts = mUserRef.child("schedule").child(key);

        ListView scheduleDisplay = (ListView) view.findViewById(R.id.scheduleDisplay);

        if(getActivity()!= null) {
            DisplayScheduleAdapter adapter = new DisplayScheduleAdapter(getActivity(),
                    R.layout.adapter_view_layout,
                    sched.getWorkouts());

            scheduleDisplay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getActivity(), DisplayWorkoutDetailsActivity.class);
                    intent.putExtra("Name", sched.getWorkouts().get(i).getWorkoutName());
                    intent.putExtra("Location", sched.getWorkouts().get(i).getLocation());

                    Log.d("name", sched.getWorkouts().get(i).getWorkoutName());
                    Log.d("location", sched.getWorkouts().get(i).getLocation());
                    startActivity(intent);
                }
            });

            scheduleDisplay.setAdapter(adapter);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK) {
                Workout workout = (Workout)getActivity().getIntent().getExtras().get("workout");
                addWorkout(workout);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                getActivity().finish();
            }
        }
    }

    protected void sendStepMessage() {
        Intent intent = new Intent(getActivity().getApplicationContext(), DashboardFragment.class);
        intent.setAction(ACTION_FITLY);
        intent.putExtra("stepCount", steps);
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext().getApplicationContext()).sendBroadcast(intent);
    }
    protected void sendSchedList() {
        Intent intent = new Intent(getActivity(), fitlyHandler.class);
        intent.setAction(ACTION_SCHEDULELIST);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);


    }


    protected void sendCalMessage() {
        Intent intent = new Intent(getActivity().getApplicationContext(), DashboardFragment.class);
        intent.setAction(ACTION_CALCOUNT);
        intent.putExtra("calCount", calories);
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext().getApplicationContext()).sendBroadcast(intent);
    }

    private BroadcastReceiver bReceiver;

    {
        bReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(ACTION_FITLY)) {
                    Bundle b = intent.getExtras();
                    ((TextView) getActivity().findViewById(R.id.StepCountText)).setText(Math.round(b.getFloat("stepCount")) + "/10,0000");
                    steps = b.getFloat("stepCount");
                }
                else if (intent.getAction().equals(ACTION_CALCOUNT)) {
                    Bundle b = intent.getExtras();
                    ((TextView) getActivity().findViewById(R.id.CalorieCountText)).setText(Math.round(b.getFloat("calCount"))+"");
                    calories = b.getFloat("calCount");
                }
                else if (intent.getAction().equals(ACTION_SCHEDULEPAGE)) {

                    if (getArguments() != null) {
                        Log.d("steps", Float.toString(getArguments().getFloat("stepCount")));
                        ((TextView) getActivity().findViewById(R.id.StepCountText)).setText(Math.round(getArguments().getFloat("stepCount")) + "/10,000 steps");
                    }

                    final Schedule sched = (Schedule) intent.getSerializableExtra("sched");


                    displaySchedule(sched);

                }
            }

        };
    }

    protected void serviceStart() {
        if (!isMyServiceRunning(fitlyHandler.class)) {
            Intent intent = new Intent(getActivity().getApplicationContext(), fitlyHandler.class);
            getActivity().startService(intent);
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

//    public Schedule getSchedule() {
//
//    }

    public void addWorkout(Workout workout) {

    }

}
