package com.example.a007fa.fitly;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User implements Serializable {
    private String username;
    private String password;
    private String email;
    private Integer numConsecutiveDays;

    private Schedule schedule;
    private ArrayList<ActivityRecord> activityRecords;
    User(String name, String useremail)
    {
        username=name;
        email=useremail;
        schedule=new Schedule();
        activityRecords= new ArrayList<ActivityRecord>();
        numConsecutiveDays=0;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
