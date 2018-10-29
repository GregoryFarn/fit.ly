package com.example.a007fa.fitly;


import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


public class DashboardFragment extends Fragment {

    static final String ACTION_FITLY = "com.fitly.action.FITLY";
    static final String ACTION_ENDDAY = "com.fitly.action.ENDDAY";
    static final String ACTION_SCHEDULELIST = "com.fitly.action.SCHEDULELIST";
    static final String ACTION_SCHEDULEPAGE = "com.fitly.action.SCHEDULEPAGE";
    View view;
    public DashboardFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dashboard, container, false);


        LocalBroadcastManager bManager = LocalBroadcastManager.getInstance(getActivity().getApplicationContext());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_FITLY);
        intentFilter.addAction(ACTION_SCHEDULEPAGE);
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

        Intent intent = new Intent(getActivity(), fitlyHandler.class);
        intent.setAction(ACTION_SCHEDULELIST);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK) {
                Workout workout = (Workout)getActivity().getIntent().getExtras().get("workout");
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                getActivity().finish();
            }
        }
    }

    private BroadcastReceiver bReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION_FITLY)) {
            Bundle b = intent.getExtras();
            ((TextView) getActivity().findViewById(R.id.StepCountText)).setText(Math.round(b.getFloat("stepCount")) + "/10,0000");
        }
        else if (intent.getAction().equals(ACTION_SCHEDULEPAGE)) {

            if (getArguments() != null) {
                Log.d("steps", Float.toString(getArguments().getFloat("stepCount")));
                ((TextView) getActivity().findViewById(R.id.StepCountText)).setText(Math.round(getArguments().getFloat("stepCount")) + "/10,000 steps");
            }

            final Schedule sched = (Schedule)intent.getSerializableExtra("sched");

            ListView scheduleDisplay = (ListView) view.findViewById(R.id.scheduleDisplay);

            DisplayScheduleAdapter adapter = new DisplayScheduleAdapter(getActivity(),
                    R.layout.adapter_view_layout,
                    sched.getWorkouts());

            scheduleDisplay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getActivity(), DisplayWorkoutDetailsActivity.class);
                    intent.putExtra("Name", sched.getWorkouts().get(i).getWorkoutName());
                    intent.putExtra("Location", sched.getWorkouts().get(i).getLocation());

                    Log.d("name", sched.getWorkouts().get(i).getWorkoutName() );
                    Log.d("location", sched.getWorkouts().get(i).getLocation());
                    startActivity(intent);
                }
            });

            scheduleDisplay.setAdapter(adapter);
        }

        }
    };

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
