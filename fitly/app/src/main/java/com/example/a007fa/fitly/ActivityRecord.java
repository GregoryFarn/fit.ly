package com.example.a007fa.fitly;

import java.util.ArrayList;

public class ActivityRecord {
    private Calendar date;
    private Integer stepCount;
    private Boolean badgeAchieved;
    private Integer totalCaloriesConsumed;
    private ArrayList<Workout> completedWorkouts = null;
    private ArrayList<Workout> incompleteWorkouts = null;
    
    public ActivityRecord(Calendar date) {
        this.date = date;
        this.stepCount = 0;
        this.badgeAchieved = false;
        this.totalCaloriesConsumed = false;
    }

    public void addCalories(int calories) {
        this.totalCaloriesConsumed += calories;
    }
    
    public Integer getTotalCalories() {
        return this.totalCaloriesConsumed;
    }
    
    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
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





