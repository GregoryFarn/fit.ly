package com.example.a007fa.fitly;

import android.app.Activity;

import java.util.ArrayList;

public class Schedule extends Activity {
    ArrayList<Workout> workouts;

    public Schedule() {
        this.workouts = new ArrayList<Workout>();
    }


    public Schedule(ArrayList<Workout> workouts) {
        this.workouts = workouts;
    }

    public void addWorkout(Workout w) {
        this.workouts.add(w);
    }

    public void removeWorkout(Workout w) {
        this.workouts.remove(w);
    }

    public void initTest() {
        Workout w1 = new Workout("10/19/2018 12:30", "10/19/2018 1:30", "Test workout 1");
        Workout w2 = new Workout("10/19/2018 1:30", "10/19/2018 2:30", "Test workout 2");
        Workout w3 = new Workout("10/19/2018 2:30", "10/19/2018 3:30", "Test workout 3");

        addWorkout(w1);
        addWorkout(w2);
        addWorkout(w3);
    }

    public String stringToDisplay() {
        initTest();

        String str = "";

        for (Workout w : workouts)
            str += ("Workout " + w.getWorkoutName() + " from " + w.getStartTime() + " to " + w.getEndTime());

        return str;
    }

    public ArrayList<String> getStringArray() {
        ArrayList<String> ret = new ArrayList<String>();
        for (Workout w : workouts) {
            ret.add(w.getWorkoutName());
        }

        return ret;
    }
}
