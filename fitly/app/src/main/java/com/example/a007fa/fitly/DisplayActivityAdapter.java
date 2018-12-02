package com.example.a007fa.fitly;

import android.app.Activity;
import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;


public class DisplayActivityAdapter extends BaseExpandableListAdapter {

    private static final int PARENT_ROW = R.layout.listrow_group_activity;
    private static final int CHILD_ROW = R.layout.listrow_details_activity;
    private Context context;
    private ArrayList<ActRec> data;
    private LayoutInflater inflater = null;

//
//    public LayoutInflater inflater;
//    public Activity activity;

    public DisplayActivityAdapter(Context context, ArrayList<ActRec> data){
        this.data = data;
        this.context = context;
        this.inflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public View getGroupView(int groupPosition,
                             boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(PARENT_ROW, parent, false);
        }
        TextView date = (TextView) convertView.findViewById(R.id.date);
        TextView caloriesConsumed = (TextView) convertView.findViewById(R.id.calories_consumed);
        TextView stepCount = (TextView) convertView.findViewById(R.id.step_count);
        TextView badgeAchieved = (TextView) convertView.findViewById(R.id.badge_achieved);
        date.setText("Date: " + data.get(groupPosition).getDate());
        caloriesConsumed.setText("Calories Consumed: " + data.get(groupPosition).getTotalCaloriesConsumed());
        stepCount.setText("Step: " + data.get(groupPosition).getStepCount());
        if(data.get(groupPosition).isBadgeAchieved()){
            badgeAchieved.setText("Badge: YES" );
        }else{
            badgeAchieved.setText("Badge: NO" );
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(CHILD_ROW, parent, false);
        }
        TextView description = (TextView) convertView.findViewById(R.id.description);
        TextView location = (TextView) convertView.findViewById(R.id.location);
        TextView start = (TextView) convertView.findViewById(R.id.start_time);
        TextView end = (TextView) convertView.findViewById(R.id.end_time);
        TextView workoutName = (TextView) convertView.findViewById(R.id.workout_name);

        description.setText("Description: " + data.get(groupPosition).getIncomplete().get(childPosition).getDescription());
        location.setText("Location: " + data.get(groupPosition).getIncomplete().get(childPosition).getLocation());
        start.setText("Start Date: " + data.get(groupPosition).getIncomplete().get(childPosition).getStart());
        end.setText("End Date: "  + data.get(groupPosition).getIncomplete().get(childPosition).getEnd());
        workoutName.setText("Workout Name: " + data.get(groupPosition).getIncomplete().get(childPosition).getWorkoutName());
        return convertView;
    }
    @Override
    public int getGroupCount() {
        return data.size();
    }
    @Override
    public int getChildrenCount(int groupPosition) {
        return data.get(groupPosition).getIncomplete().size();
    }
    @Override
    public Object getGroup(int groupPosition) {
        return data.get(groupPosition);
    }
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return data.get(groupPosition).getIncomplete().get(childPosition);
    }
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
    @Override
    public boolean hasStableIds() {
        return true;

    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
