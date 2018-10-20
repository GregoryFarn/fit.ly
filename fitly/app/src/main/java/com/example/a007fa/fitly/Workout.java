package com.example.a007fa.fitly;

public class Workout {
    private String startTime; // "MM/DD/YYYY HH:MM"
    private String endTime;
    private String workoutName;
    private String location;
    private String description = "";

    public Workout(String startTime, String endTime, String workoutName) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.workoutName = workoutName;
    }

    public Workout(String startTime, String endTime, String workoutName, String location) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.workoutName = workoutName;
        this.location = location;
    }

    public Workout(String startTime, String endTime, String workoutName, String location, String description) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.workoutName = workoutName;
        this.location = location;
        this.description = description;
    }

    public String getStartTime() { return this.startTime; }

    public String getEndTime() { return this.endTime; }

    public String getWorkoutName() { return this.workoutName; }

    public String getLocation() { return this.location; }

    public String getDescription() { return this.description; }
}
