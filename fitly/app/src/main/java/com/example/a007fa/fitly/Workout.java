package com.example.a007fa.fitly;

import android.os.Parcel;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Workout implements Serializable {
    private String workoutName;
    private String startTime;
    private String endTime;
    private String location;
    private String description = "";
    private boolean isCompletedWorkout = false;

    public Workout() { }

    public Workout(String workoutName) { this.workoutName = workoutName;
        this.startTime = "";
        this.endTime = "";
        this.location = "";
        this.description = "";
    }

    public Workout(String workoutName, Calendar startTime, Calendar endTime) {
        this.workoutName = workoutName;
        this.startTime = startTime.toString();
        this.endTime = endTime.toString();
        this.location = "";
        this.description = "";
    }

    public Workout(String workoutName, Calendar startTime, Calendar endTime, String location) {
        this.workoutName = workoutName;
        this.startTime = startTime.toString();
        this.endTime = endTime.toString();
        this.location = location;
        this.description = "";
    }

    public Workout(String workoutName, Calendar startTime, Calendar endTime, String location, String description) {
        this.workoutName = workoutName;
        this.startTime = startTime.toString();
        this.endTime = endTime.toString();
        this.location = location;
        this.description = description;
    }


    public Workout(String workoutName, String startTime, String endTime, String location, String description) {
        this.workoutName = workoutName;
        this.startTime = "";
        this.endTime = ""; // convert to string
        this.location = location;
        this.description = description;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("workoutName", workoutName);
        result.put("startTime", startTime);
        result.put("endTime", endTime);
        result.put("location", location);
        result.put("description", description);

        return result;
    }

    // Alternative constructor
    public void add(String workoutName, Calendar startTime, Calendar endTime) {
        this.workoutName = workoutName;
        this.startTime = startTime.toString();
        this.endTime = endTime.toString();
    }

    protected Workout(Parcel in) {
        this.workoutName = in.readString();
        this.location = in.readString();
        this.description = in.readString();
    }


    public String getStartDate() {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        return format.format(stringToCalendar(this.startTime).getTime());
    }

    public String getEndDate() {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        return format.format(stringToCalendar(this.endTime).getTime());
    }

    public String getStartTime() {
        SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
        return format.format(stringToCalendar(this.startTime).getTime());
    }

    public String getEndTime() {
        SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
        return format.format(stringToCalendar(this.endTime).getTime());
    }

    public Calendar getStartCalendar() { return stringToCalendar(this.startTime); }

    public Calendar getEndCalendar() { return stringToCalendar(this.endTime); }

    public String getWorkoutName() { return this.workoutName; }

    public String getLocation() { return this.location; }

    public String getDescription() { return this.description; }

    public boolean isCompletedWorkout() {
        return isCompletedWorkout;
    }

    public void setCompletedWorkout(boolean completedWorkout) {
        isCompletedWorkout = completedWorkout;
    }

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

