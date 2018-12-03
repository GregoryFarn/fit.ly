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
    private ArrayList<Map<String,Object>> completedWorkouts;
    private ArrayList<Map<String,Object>> incompleteWorkouts;

    public  ActivityRecord()
    {

    }
    public ActivityRecord(Calendar date) {
        this.date = date.getTime().toString();
        this.stepCount = 0;
        this.badgeAchieved = false;
        this.totalCaloriesConsumed = 0;
        completedWorkouts = new ArrayList<>();
        incompleteWorkouts = new ArrayList<>();
        completedWorkouts.add((new Workout("ye")).toMap());
        incompleteWorkouts.add((new Workout("ye")).toMap());
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("date", date);
        result.put("stepCount", stepCount);
        result.put("badgeAchieved", badgeAchieved);
        result.put("totalCaloriesConsumed", totalCaloriesConsumed);
        result.put("completedWorkouts", completedWorkouts);
        result.put("incompleteWorkouts", incompleteWorkouts);
        return result;
    }

    public void fromMap(Map<String, Object> putValue)
    {
        //this.stepCount = (Integer) putValue.get("stepCount");
        this.badgeAchieved= (Boolean)  putValue.get("badgeAchieved");

    }

    public void addCalories(int calories) {
        this.totalCaloriesConsumed += calories;
    }
    
    public Integer getTotalCalories() {
        return this.totalCaloriesConsumed;
    }

    public void setTotalCalories(int totalCaloriesConsumed) {
         this.totalCaloriesConsumed = totalCaloriesConsumed;
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
        this.date = date.getTime().toString();
    }

    public Integer getStepCount() {
        return stepCount;
    }

    public void setStepCount(Integer stepCount) {
        this.stepCount = stepCount;
    }

    public ArrayList<Map<String,Object>> getCompletedWorkouts() {
        return completedWorkouts;
    }

    public void setCompletedWorkouts(ArrayList<Map<String,Object>> completedWorkouts) {
        this.completedWorkouts = completedWorkouts;
    }

    public ArrayList<Map<String,Object>> getIncompleteWorkouts() {
        return incompleteWorkouts;
    }

    public void setIncompleteWorkouts(ArrayList<Map<String,Object>> incompleteWorkouts) {
        this.incompleteWorkouts = incompleteWorkouts;
    }

    public void setBadgeAcheived(boolean badge) {
        this.badgeAchieved =badge;
    }


}





