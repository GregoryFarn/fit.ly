package com.example.a007fa.fitly;

import java.util.ArrayList;

public class ActivityRecord {
    private String date;
    private Integer stepCount;
    private Boolean badgeAchieved;
    private ArrayList<Workout> completedWorkouts = null;
    private ArrayList<Workout> incompleteWorkouts = null;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getStepCount() {
        return stepCount;
    }

    public void setStepCount(Integer stepCount) {
        this.stepCount = stepCount;
    }

    public ArrayList<Workout> getCompletedWorkouts() {
        return completedWorkouts;
    }

    public void setCompletedWorkouts(ArrayList<Workout> completedWorkouts) {
        this.completedWorkouts = completedWorkouts;
    }

    public ArrayList<Workout> getIncompleteWorkouts() {
        return incompleteWorkouts;
    }

    public void setIncompleteWorkouts(ArrayList<Workout> incompleteWorkouts) {
        this.incompleteWorkouts = incompleteWorkouts;
    }
}





