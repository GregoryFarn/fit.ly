package com.example.a007fa.fitly;

public class Workout {
    private long startTime;
    private long endTime;
    private String workoutName;
    private String location;
    private String description = "";

    public Workout(long startTime, long endTime, String workoutName) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.workoutName = workoutName;
    }

    public Workout(long startTime, long endTime, String workoutName, String location) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.workoutName = workoutName;
        this.location = location;
    }

    public Workout(long startTime, long endTime, String workoutName, String location, String description) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.workoutName = workoutName;
        this.location = location;
        this.description = description;
    }

    public long getStartTime() { return this.startTime; }

    public long getEndTime() { return this.endTime; }

    public String getWorkoutName() { return this.workoutName; }

    public String getLocation() { return this.location; }

    public String getDescription() { return this.description; }
}
