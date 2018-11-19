package com.example.a007fa.fitly;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class DisplayScheduleAdapter extends ArrayAdapter<Workout> {

    private Context mContext;
    private DashboardFragment dashboardFragment;
    private int mResource;
    static final String ACTION_DONE = "com.fitly.action.DONE";
    //private ArrayList<Workout> = new ArrayAdapter<Workout>;

    public DisplayScheduleAdapter(Context context, int resource, ArrayList<Workout> objects) {

        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get all workout details
        String workoutName = getItem(position).getWorkoutName();
        String startTime = getItem(position).getStartTime();
        String endTime = getItem(position).getEndTime();
        String location= getItem(position).getLocation();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        final TextView tvName = (TextView) convertView.findViewById(R.id.name);
        TextView tvStart = (TextView) convertView.findViewById(R.id.start);
        TextView tvEnd = (TextView) convertView.findViewById(R.id.end);
        TextView tvLocation = (TextView) convertView.findViewById(R.id.location);
        final CheckBox isComplete = (CheckBox) convertView.findViewById(R.id.isWorkoutComplete) ;
        tvName.setText(workoutName);
        tvStart.setText(startTime);
        tvEnd.setText(endTime);
        tvLocation.setText(location);



        isComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    //tvName.setText("fdf");
                    getItem(position).setCompletedWorkout(true);
                    isComplete.setChecked(true);
                    Log.d("isCompleted","getItem " + String.valueOf(getItem(position).isCompletedWorkout()));
                    Log.d("isCompleted", "r.id " +String.valueOf(isComplete.isChecked()));

                    Intent intent1 = new Intent(mContext, fitlyHandler.class);
                    intent1.setAction(ACTION_DONE);
                    intent1.putExtra("workout",getItem(position));
                    LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent1);
                    Log.d("used", "yea");
                }
                else{
                    getItem(position).setCompletedWorkout(false);
                    isComplete.setChecked(false);
                    Log.d("isCompleted","getItem " + String.valueOf(getItem(position).isCompletedWorkout()));
                    Log.d("isCompleted", "r.id " +String.valueOf(isComplete.isChecked()));
                }
            }
        });

        return convertView;
    }

}
