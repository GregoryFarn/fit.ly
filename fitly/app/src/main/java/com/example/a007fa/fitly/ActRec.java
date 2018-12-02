package com.example.a007fa.fitly;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;


public class ActRec {
    private String date;
    private int stepCount;
    private boolean badgeAchieved;
    private int totalCaloriesConsumed;
    private ArrayList<IncompleteWorkout> incompleteWorkouts;

    public ActRec(String date, int stepCount, boolean badgeAchieved, int totalCaloriesConsumed) {
        this.date =date;
        this.stepCount = stepCount;
        this.badgeAchieved = badgeAchieved;
        this.totalCaloriesConsumed = totalCaloriesConsumed;
        this.incompleteWorkouts = new ArrayList<>();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStepCount() {
        return stepCount;
    }

    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }

    public Boolean isBadgeAchieved() {
        return badgeAchieved;
    }

    public void setBadgeAchieved(boolean badgeAchieved) {
        this.badgeAchieved = badgeAchieved;
    }

    public int getTotalCaloriesConsumed() {
        return totalCaloriesConsumed;
    }

    public void setTotalCaloriesConsumed(int totalCaloriesConsumed) {
        this.totalCaloriesConsumed = totalCaloriesConsumed;
    }


    public ArrayList<IncompleteWorkout> getIncomplete() {
        return incompleteWorkouts;
    }

    public void setIncomplete(ArrayList<IncompleteWorkout> incomplete) {
        this.incompleteWorkouts = incomplete;
    }

    @Override
    public String toString() {
        return "ActRec{" +
                "date='" + date + '\'' +
                ", stepCount=" + stepCount +
                ", badgeAchieved=" + badgeAchieved +
                ", totalCaloriesConsumed=" + totalCaloriesConsumed +
                ", incompleteWorkouts=" + incompleteWorkouts +
                '}';
    }
}
