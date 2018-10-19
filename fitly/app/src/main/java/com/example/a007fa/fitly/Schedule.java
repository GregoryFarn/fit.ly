package com.example.a007fa.fitly;

import java.util.ArrayList;

public class Schedule {
    ArrayList<Workout> workouts;

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
        Workout w1 = new Workout(System.currentTimeMillis(), System.currentTimeMillis() + 60000, "Test workout 1");
        Workout w2 = new Workout(System.currentTimeMillis(), System.currentTimeMillis() + 60000, "Test workout 2");
        Workout w3 = new Workout(System.currentTimeMillis(), System.currentTimeMillis() + 60000, "Test workout 3");

        addWorkout(w1);
        addWorkout(w2);
        addWorkout(w3);
    }

    public void display() {
        initTest();

        for (Workout w : workouts)
            System.out.println("Workout " + w.getWorkoutName() + " from " + w.getStartTime() + " to " + w.getEndTime());
    }
}
