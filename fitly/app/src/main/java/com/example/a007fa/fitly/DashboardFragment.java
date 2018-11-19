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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {
    private FirebaseUser mUser;
    private DatabaseReference mUserRef;

    private ArrayList<Workout> workouts;
    private DisplayScheduleAdapter adapter;

    static final String ACTION_FITLY = "com.fitly.action.FITLY";
    static final String ACTION_ENDDAY = "com.fitly.action.ENDDAY";
    static final String ACTION_SCHEDULELIST = "com.fitly.action.SCHEDULELIST";
    static final String ACTION_SCHEDULEPAGE = "com.fitly.action.SCHEDULEPAGE";
    static final String ACTION_CALCOUNT = "com.fitly.action.CALCOUNT";
    static final String ACTION_EAT = "com.fitly.action.EAT";

    private View view;
    private final String TAG = "Dashboard fragment";

    float steps;
    float calories;
    float caloriesConsumed;
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
        intentFilter.addAction(ACTION_EAT);
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

        Intent intent = new Intent(getActivity(), fitlyHandler.class);
        intent.setAction(ACTION_SCHEDULELIST);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);

        workouts = new ArrayList<Workout>();
        displaySchedule();

        steps=0;
        calories=0;
        caloriesConsumed =0;
        sendStepMessage();
        sendCalMessage();
        sendSchedList();

        return view;
    }

    public void setTotalCaloriesConsumedText(String text){
        TextView textView = (TextView) getView().findViewById(R.id.CalorieConsumedCountText);
        textView.setText(text);
    }


    public void displaySchedule() {
        ListView scheduleDisplay = (ListView) view.findViewById(R.id.scheduleDisplay);

        if(getActivity()!= null) {
            adapter = new DisplayScheduleAdapter(getActivity(),
                    R.layout.adapter_view_layout,
                    workouts);

            scheduleDisplay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getActivity(), DisplayWorkoutDetailsActivity.class);
                    intent.putExtra("Name", workouts.get(i).getWorkoutName());
                    intent.putExtra("Location", workouts.get(i).getLocation());

                    Log.d("name", workouts.get(i).getWorkoutName());
                    Log.d("location", workouts.get(i).getLocation());
                    startActivity(intent);
                }
            });

            scheduleDisplay.setAdapter(adapter);
        }
    }

    public void updateWorkoutsUI(Workout w) {
        workouts.add(w);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK) {
                Workout workout = (Workout)getActivity().getIntent().getExtras().get("workout");
                updateWorkoutsUI(workout);
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
                else if (intent.getAction().equals(ACTION_EAT)) {
                    Bundle b = intent.getExtras();
                    ((TextView) getActivity().findViewById(R.id.CalorieConsumedCountText)).setText(Math.round(b.getFloat("calCount"))+"");
                    caloriesConsumed = b.getInt("calCount");
                }
                else if (intent.getAction().equals(ACTION_SCHEDULEPAGE)) {

                    if (getArguments() != null) {
                        Log.d("steps", Float.toString(getArguments().getFloat("stepCount")));
                        ((TextView) getActivity().findViewById(R.id.StepCountText)).setText(Math.round(getArguments().getFloat("stepCount")) + "/10,000 steps");
                    }

                    // Initialize workouts array to display
                    String key = "10282018"; // replace with a way to get today's date

                    DatabaseReference workoutsRef = mUserRef.child("schedule").child(key);
                    workoutsRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            GenericTypeIndicator<List<Workout>> gti = new GenericTypeIndicator<List<Workout>>() {};
                            List<Workout> wm = dataSnapshot.getValue(gti);
                            Log.d(TAG, wm.toString());

                            if (wm == null) {
                                return;
                            }
                            if (wm != null && wm.size() != 0) {
                                Log.d("wm size", Integer.toString(wm.size()));
                                for (Workout entry : wm) {
                                    Workout w = new Workout(entry.getWorkoutName(), entry.getStart(), entry.getEnd(), entry.getLocation(), entry.getDescription());
                                    updateWorkoutsUI(w);
                                    Log.d("Entry " + w.getWorkoutName(), entry.getStart() + " to " + entry.getEnd());
                                    Log.d("Workout " + w.getWorkoutName(), w.getStart() + " to " + w.getEnd());
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            Log.w(TAG, "Failed to read value.", error.toException());
                        }
                    });
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
}
