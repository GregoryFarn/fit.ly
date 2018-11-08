package com.example.a007fa.fitly;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User implements Serializable {
    private String displayName;
    private String password;
    private String email;
    private Integer age;
    private Integer numConsecutiveDays;

    private Schedule schedule;
    private ArrayList<ActivityRecord> activityRecords;
    User(String name, String useremail)
    {
        displayName=name;
        email=useremail;
        schedule=new Schedule();
        activityRecords= new ArrayList<ActivityRecord>();
        numConsecutiveDays=0;
        age=0;
    }
    public String getdisplayName() {
        return displayName;
    }

    public void setdisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getNumConsecutiveDays() {
        return numConsecutiveDays;
    }

    public void setNumConsecutiveDays(Integer numConsecutiveDays) {
        this.numConsecutiveDays = numConsecutiveDays;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public ArrayList<ActivityRecord> getActivityRecords() {
        return activityRecords;
    }

    public void setActivityRecords(ArrayList<ActivityRecord> activityRecords) {
        this.activityRecords = activityRecords;
    }


}
