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
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


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

        LocalBroadcastManager bManager = LocalBroadcastManager.getInstance(getActivity().getApplicationContext());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_FITLY);
        intentFilter.addAction(ACTION_SCHEDULEPAGE);
        intentFilter.addAction(ACTION_CALCOUNT);
        bManager.registerReceiver(bReceiver, intentFilter);
        serviceStart();

        FloatingActionButton activityButton = view.findViewById(R.id.addWorkoutButton);
        activityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), addActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton calorieButton = view.findViewById(R.id.AddCaloriesButton);
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


<<<<<<< HEAD
                    displaySchedule(sched);
=======
                    if (getActivity() != null) {
                        final DisplayScheduleAdapter adapter = new DisplayScheduleAdapter(getActivity(),
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

                             //   final CheckBox isComplete = ((CheckBox) view.findViewById(R.id.isWorkoutComplete));


                                startActivity(intent);


                            }


                        });
                        scheduleDisplay.setAdapter(adapter);

                    }
>>>>>>> f71eb930e8aabdf1a803aa64bec50580d957553f

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
<?xml version="1.0" encoding="utf-8"?>
<ScrollView xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical">

        <RelativeLayout
            android:id="@+id/header"
            android:layout_width="match_parent"
            android:layout_height="60dp"
            android:layout_alignParentStart="true"
            android:layout_alignParentTop="true"
            android:background="@drawable/grad"
            android:paddingBottom="15dp">

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_centerInParent="true"
                android:fontFamily="sans-serif-light"
                android:text="Dashboard"
                android:textColor="@color/colorWhite"
                android:textSize="20dp" />

        </RelativeLayout>

        <GridLayout
            android:id="@+id/gl1"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:alignmentMode="alignMargins"
            android:columnCount="1"
            android:columnOrderPreserved="false"
            android:rowCount="1">

            <android.support.v7.widget.CardView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_rowWeight="1"
                android:layout_columnWeight="1"
                android:layout_margin="12dp"
                app:cardCornerRadius="12dp"
                app:cardElevation="6dp">

                <LinearLayout
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"
                    android:layout_gravity="center"
                    android:orientation="vertical"
                    android:padding="16dp">

                    <TextView
                        android:id="@+id/StepCountText"
                        android:layout_width="match_parent"
                        android:layout_height="match_parent"
                        android:text="0/10,000"
                        android:textAlignment="center"
                        android:textSize="38dp"
                        app:layout_constraintBottom_toBottomOf="parent"
                        app:layout_constraintHorizontal_bias="0.501"
                        app:layout_constraintLeft_toLeftOf="parent"
                        app:layout_constraintRight_toRightOf="parent"
                        app:layout_constraintTop_toTopOf="parent"
                        app:layout_constraintVertical_bias="0.184" />

                    <TextView
                        android:id="@+id/stepsText"
                        tools:ignore="MissingConstraints"
                        android:layout_width="match_parent"
                        android:layout_height="match_parent"
                        android:text="steps walked today"
                        android:textAlignment="center"
                        android:textSize="15sp"
                        app:layout_constraintBottom_toBottomOf="parent"
                        app:layout_constraintEnd_toEndOf="parent"
                        app:layout_constraintStart_toStartOf="parent"
                        app:layout_constraintTop_toBottomOf="@+id/toolbar2" />

                </LinearLayout>

            </android.support.v7.widget.CardView>

        </GridLayout>

        <GridLayout
            android:id="@+id/gl2"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:columnOrderPreserved="false"
            android:alignmentMode="alignMargins"
            android:rowCount="1"
            android:columnCount="2">

            <android.support.v7.widget.CardView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_columnWeight="1"
                android:layout_rowWeight="1"
                app:cardElevation="6dp"
                app:cardCornerRadius="12dp"
                android:layout_margin="12dp">

                <LinearLayout
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"
                    android:layout_gravity="center"
                    android:padding="16dp"
                    android:orientation="vertical">

                    <TextView
                        android:id="@+id/CalorieConsumedCountText"
                        android:layout_width="match_parent"
                        android:layout_height="match_parent"
                        android:baselineAligned="false"
                        android:text="10"
                        android:textAlignment="center"
                        android:textSize="12sp"
                        app:layout_constraintHorizontal_bias="0.501"
                        app:layout_constraintLeft_toLeftOf="parent"
                        app:layout_constraintRight_toRightOf="parent"
                        app:layout_constraintTop_toBottomOf="@+id/stepsText" />

                    <TextView
                        android:id="@+id/CalorieConsumedText"
                        android:layout_width="match_parent"
                        android:layout_height="match_parent"
                        android:layout_marginTop="12dp"
                        android:text="calories consumed today"
                        android:textAlignment="center"
                        android:textSize="7sp"
                        app:layout_constraintEnd_toEndOf="parent"
                        app:layout_constraintStart_toStartOf="parent"
                        app:layout_constraintTop_toBottomOf="@+id/CalorieCountText" />

                </LinearLayout>

            </android.support.v7.widget.CardView>

            <android.support.v7.widget.CardView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_columnWeight="1"
                android:layout_rowWeight="1"
                app:cardElevation="6dp"
                app:cardCornerRadius="12dp"
                android:layout_margin="12dp">

                <LinearLayout
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"
                    android:layout_gravity="center"
                    android:padding="16dp"
                    android:orientation="vertical">

                    <TextView
                        android:id="@+id/CalorieCountText"
                        android:layout_width="match_parent"
                        android:layout_height="match_parent"

                        android:textAlignment="center"
                        android:textSize="12sp"
                        app:layout_constraintHorizontal_bias="0.501"
                        app:layout_constraintLeft_toLeftOf="parent"
                        app:layout_constraintRight_toRightOf="parent"
                        app:layout_constraintTop_toBottomOf="@+id/stepsText" />

                    <TextView
                        android:id="@+id/CalorieText"
                        android:layout_width="match_parent"
                        android:layout_height="match_parent"
                        android:text="calories burned today"
                        android:textAlignment="center"
                        android:textSize="7sp"
                        app:layout_constraintEnd_toEndOf="parent"
                        app:layout_constraintStart_toStartOf="parent"
                        app:layout_constraintTop_toBottomOf="@+id/CalorieCountText"
                        tools:ignore="MissingConstraints" />

                </LinearLayout>

            </android.support.v7.widget.CardView>

            <android.support.v7.widget.CardView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_columnWeight="1"
                android:layout_rowWeight="1"
                app:cardElevation="6dp"
                app:cardCornerRadius="12dp"
                android:layout_margin="12dp">
                
                    <Button
                        android:id="@+id/addWorkoutButton"
                        android:layout_width="match_parent"
                        android:layout_height="match_parent"
                        android:backgroundTint="@color/colorPrimary"
                        android:clickable="true"
                        android:text="Workout"
                        android:textColor="@color/colorWhite" />

            </android.support.v7.widget.CardView>
            <android.support.v7.widget.CardView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_columnWeight="1"
                android:layout_rowWeight="1"
                app:cardElevation="6dp"
                app:cardCornerRadius="12dp"
                android:layout_margin="12dp">

                <LinearLayout
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"
                    android:layout_gravity="center"
                    android:padding="16dp"
                    android:orientation="vertical">

                    <Button
                        android:id="@+id/AddCaloriesButton"
                        android:layout_width="match_parent"
                        android:layout_height="match_parent"
                        android:backgroundTint="@color/colorPrimary"
                        android:clickable="true"
                        android:text="Calories"
                        android:textColor="@color/colorWhite" />

                </LinearLayout>

            </android.support.v7.widget.CardView>

        </GridLayout>
        
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="vertical">
            
            
        </LinearLayout>

        <ListView
            android:id="@+id/scheduleDisplay"
            android:layout_width="347dp"
            android:layout_height="265dp"
            android:layout_margin="20dp"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintHorizontal_bias="0.0"
            app:layout_constraintLeft_toLeftOf="parent"
            app:layout_constraintRight_toRightOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/textView"
            app:layout_constraintVertical_bias="0.116" />

        <android.support.design.widget.BottomNavigationView
            android:id="@+id/navigation"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:background="?android:attr/windowBackground"
            app:itemBackground="@color/colorPrimary"
            app:itemIconTint="@color/nav_item_colors"
            app:itemTextColor="@color/nav_item_colors"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintLeft_toLeftOf="parent"
            app:layout_constraintRight_toRightOf="parent"
            app:menu="@menu/navigation">

        </android.support.design.widget.BottomNavigationView>

        <android.support.v7.widget.Toolbar
            android:id="@+id/toolbar2"
            android:layout_width="0dp"
            android:layout_height="0dp"
            android:layout_marginBottom="91dp"
            android:background="#77bed5"
            android:minHeight="?attr/actionBarSize"
            android:theme="?attr/actionBarTheme"
            app:layout_constraintBottom_toTopOf="@+id/rl1"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />

        <TextView
            android:id="@+id/textView"
            android:layout_width="142dp"
            android:layout_height="18dp"
            android:layout_marginStart="60dp"
            android:text="Today's schedule"
            android:textAppearance="@style/TextAppearance.AppCompat.Widget.Button.Borderless.Colored"
            android:textSize="15sp"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/buttonLayout"
            app:layout_constraintLeft_toLeftOf="parent"
            app:layout_constraintRight_toRightOf="parent" />
        
    </LinearLayout>

</ScrollView>
