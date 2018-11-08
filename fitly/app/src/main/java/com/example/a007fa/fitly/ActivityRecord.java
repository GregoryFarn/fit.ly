package com.example.a007fa.fitly;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ActivityRecord  implements Serializable {
    private String date;
    private int stepCount;
    private Boolean badgeAchieved;
    private Integer totalCaloriesConsumed;
    private ArrayList<Workout> completedWorkouts;
    private ArrayList<Workout> incompleteWorkouts;
    
    public ActivityRecord(Calendar date) {
        this.date = date.getTime().toString();
        this.stepCount = 0;
        this.badgeAchieved = false;
        this.totalCaloriesConsumed = 0;
        completedWorkouts = new ArrayList<>();
        incompleteWorkouts = new ArrayList<>();
        completedWorkouts.add(new Workout("ye"));
        incompleteWorkouts.add(new Workout("ye"));
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("date", date);
        result.put("stepCount", stepCount);
        result.put("badgeAchieved", badgeAchieved);
        result.put("totalCaloriesConsumed", totalCaloriesConsumed);


        return result;
    }

    public void addCalories(int calories) {
        this.totalCaloriesConsumed += calories;
    }
    
    public Integer getTotalCalories() {
        return this.totalCaloriesConsumed;
    }
    
    public Calendar getDate() {
        return stringToCalendar();
    }

    public Calendar stringToCalendar(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        try {
            cal.setTime(sdf.parse(date));// all done
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cal;
    }

    public void setDate(Calendar date) {
        this.date = date.toString();
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

    public void setBadgeAcheived(boolean badge) {
        this.badgeAchieved =badge;
    }


}





