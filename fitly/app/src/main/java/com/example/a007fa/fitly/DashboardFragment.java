package com.example.a007fa.fitly;


import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {


    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        final Schedule sched = new Schedule();
        sched.initTest();

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

//        bManager = LocalBroadcastManager.getInstance(getApplicationContext());
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(ACTION_FITLY);
//        intentFilter.addAction(ACTION_ENDDAY);
//        intentFilter.addAction(ACTION_BADGE);
//        bManager.registerReceiver(bReceiver, intentFilter);
//        serviceStart();

        return view;
    }

}
