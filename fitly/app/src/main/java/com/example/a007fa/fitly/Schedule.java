package com.example.a007fa.fitly;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class Schedule implements Serializable {
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
        Calendar start1 = Calendar.getInstance();
        Calendar end1 = Calendar.getInstance();
        start1.set(2018, 10, 23, 12, 00);
        end1.set(2018, 10, 23, 14, 00);
        Workout w1 = new Workout("Morning hike", start1, end1, "Hollywood Sign");

        Calendar start2 = Calendar.getInstance();
        Calendar end2 = Calendar.getInstance();
        start2.set(2018, 10, 23, 15, 00);
        end2.set(2018, 10, 23, 16, 00);
        Workout w2 = new Workout("Basketball", start2, end2, "Josh's house");

        Calendar start3 = Calendar.getInstance();
        Calendar end3 = Calendar.getInstance();
        start3.set(2018, 10, 23, 20, 00);
        end3.set(2018, 10, 23, 21, 00);
        Workout w3 = new Workout("Run", start3, end3, "Gym");

        addWorkout(w1);
        addWorkout(w2);
        addWorkout(w3);
    }

    public ArrayList<Workout> getWorkouts() {
        return workouts;
    }

    public ArrayList<String> getStringArray() {
        ArrayList<String> ret = new ArrayList<String>();
        for (Workout w : workouts) {
            ret.add(w.getWorkoutName());
        }

        return ret;
    }
}
