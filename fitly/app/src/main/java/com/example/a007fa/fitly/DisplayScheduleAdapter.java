package com.example.a007fa.fitly;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class DisplayScheduleAdapter extends ArrayAdapter<Workout> {

    private Context mContext;
    private int mResource;

    public DisplayScheduleAdapter(Context context, int resource, ArrayList<Workout> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get all workout details
        String workoutName = getItem(position).getWorkoutName();
        String startTime = getItem(position).getStartTime() + " pm";
        String endTime = getItem(position).getEndTime() + " pm";
        String location= getItem(position).getLocation();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvName = (TextView) convertView.findViewById(R.id.name);
        TextView tvStart = (TextView) convertView.findViewById(R.id.start);
        TextView tvEnd = (TextView) convertView.findViewById(R.id.end);
        TextView tvLocation = (TextView) convertView.findViewById(R.id.location);

        tvName.setText(workoutName);
        tvStart.setText(startTime);
        tvEnd.setText(endTime);
        tvLocation.setText(location);

        return convertView;
    }
}
