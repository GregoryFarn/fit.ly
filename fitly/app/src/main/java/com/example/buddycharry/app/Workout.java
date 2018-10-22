package com.example.a007fa.fitly;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Calendar;

public class Workout implements Serializable {
    private String workoutName;
    private String location;
    private String description = "";
    private Calendar startCalendar;
    private Calendar endCalendar;

    public Workout(String workoutName) {
        this.workoutName = workoutName;
    }

    public void addCalendars(Calendar startCalendar, Calendar endCalendar){
        this.startCalendar = startCalendar;
        this.endCalendar = endCalendar;
    }

    public Calendar getStartCalendar(){
        return this.startCalendar;
    }

    public Calendar getEndCalendar(){
        return this.endCalendar;
    }

    public String getWorkoutName() { return this.workoutName; }

    public String getLocation() { return this.location; }

    public String getDescription() { return this.description; }

}