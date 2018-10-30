package com.example.a007fa.fitly;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Workout implements Serializable {
    private String workoutName;
    private Calendar startTime;
    private Calendar endTime;
    private String location;
    private String description = "";

    public Workout() { }

    public Workout(String workoutName) { this.workoutName = workoutName; }

    public Workout(String workoutName, Calendar startTime, Calendar endTime) {
        this.workoutName = workoutName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Workout(String workoutName, Calendar startTime, Calendar endTime, String location) {
        this.workoutName = workoutName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
    }

    public Workout(String workoutName, Calendar startTime, Calendar endTime, String location, String description) {
        this.workoutName = workoutName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.description = description;
    }


    public Workout(String workoutName, String startTime, String endTime, String location, String description) {
        this.workoutName = workoutName;
//        this.startTime = startTime;
//        this.endTime = endTime; // convert to string
        this.location = location;
        this.description = description;
    }

    // Alternative constructor
    public void add(String workoutName, Calendar startTime, Calendar endTime) {
        this.workoutName = workoutName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    protected Workout(Parcel in) {
        this.workoutName = in.readString();
        this.location = in.readString();
        this.description = in.readString();
    }


    public String getStartDate() {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        return format.format(this.startTime.getTime());
    }

    public String getEndDate() {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        return format.format(this.endTime.getTime());
    }

    public String getStartTime() {
        SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
        return format.format(this.startTime.getTime());
    }

    public String getEndTime() {
        SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
        return format.format(this.endTime.getTime());
    }

    public Calendar getStartCalendar() { return this.startTime; }

    public Calendar getEndCalendar() { return this.endTime; }

    public String getWorkoutName() { return this.workoutName; }

    public String getLocation() { return this.location; }

    public String getDescription() { return this.description; }


}

