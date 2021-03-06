package com.example.a007fa.fitly;

import android.os.Parcel;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Workout implements Serializable {
    private String workoutName;
    private String start;
    private String end;
    private String location;
    private String description = "";
    private boolean isCompletedWorkout = false;

    public Workout() { }

    public Workout(String workoutName) {
        this.workoutName = workoutName;
        this.start = "";
        this.end = "";
        this.location = "";
        this.description = "";
    }

    public Workout(String workoutName, Calendar start, Calendar end) {
        this.workoutName = workoutName;
        this.start = start.getTime().toString();
        this.end = end.getTime().toString();
        this.location = "";
        this.description = "";
    }

    public Workout(String workoutName, Calendar start, Calendar end, String location) {
        this.workoutName = workoutName;
        this.start = start.getTime().toString();
        this.end = end.getTime().toString();
        this.location = location;
        this.description = "";
    }

    public Workout(String workoutName, Calendar start, Calendar end, String location, String description) {
        this.workoutName = workoutName;
        this.start = start.getTime().toString();
        this.end = end.getTime().toString();
        this.location = location;
        this.description = description;
    }


    public Workout(String workoutName, String start, String end, String location, String description) {
        this.workoutName = workoutName;
        this.start = start;
        this.end = end;
        this.location = location;
        this.description = description;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("workoutName", workoutName);
        result.put("start", start);
        result.put("end", end);
        result.put("location", location);
        result.put("description", description);

        return result;
    }

    static public ArrayList<Map<String, Object>> listToMap(ArrayList<Workout> list) {
        ArrayList<Map<String, Object>> result = new ArrayList<>();
        for(Workout x : list) {
            result.add(x.toMap());
        }
        return result;
    }

    // Alternative constructor
    public void add(String workoutName, Calendar start, Calendar end) {
        this.workoutName = workoutName;
        this.start = start.getTime().toString();
        this.end = end.getTime().toString();
    }

    public boolean equals(Workout o) {
        if(o.getWorkoutName().equals(workoutName))
        {
            return true;
        }
        else{
            return false;
        }
    }

    protected Workout(Parcel in) {
        this.workoutName = in.readString();
        this.location = in.readString();
        this.description = in.readString();
    }

    public String getStartDate() {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        return format.format(stringToCalendar(this.start).getTime());
    }

    public String getEndDate() {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        return format.format(stringToCalendar(this.end).getTime());
    }

    public String getStartTime() {
        SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
        return format.format(stringToCalendar(this.start).getTime());
    }

    public String getEndTime() {
        SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
        return format.format(stringToCalendar(this.end).getTime());
    }

    public String getStart() { return this.start; }

    public void  setStart(String start) { this.start = start; }

    public String getEnd() { return this.end; }

    public void  setEnd(String end) { this.end = end; }

    @Exclude
    public Calendar getStartCalendar() { return stringToCalendar(this.start); }

    @Exclude
    public Calendar getEndCalendar() { return stringToCalendar(this.end); }

    public String getWorkoutName() { return this.workoutName; }

    public void  setWorkoutName(String workoutName) { this.workoutName = workoutName; }

    public String getLocation() { return this.location; }

    public void  setLocation(String location) { this.location = location; }

    public String getDescription() { return this.description; }

    public void  setDescription(String description) { this.description = description; }

    public boolean isCompletedWorkout() {
        return isCompletedWorkout;
    }

    public void setCompletedWorkout(boolean completedWorkout) {
        isCompletedWorkout = completedWorkout;
    }

    @Exclude
    public Calendar stringToCalendar(String date){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        try {
            cal.setTime(sdf.parse(date));// all done
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cal;
    }
}

