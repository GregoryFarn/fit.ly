package com.example.a007fa.fitly;

public class IncompleteWorkout {
    private String description;
    private String end;
    private String start;
    private String location;
    private String workoutName;

    public IncompleteWorkout(String description, String end, String location, String start, String workoutName){
        this.description = description;
        this.end = end;
        this.start = start;
        this.location = location;
        this.workoutName = workoutName;
    }

    public String getDescription() {
        return description;
    }

    public String getEnd() {
        return end;
    }

    public String getStart() {
        return start;
    }

    public String getLocation() {
        return location;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    @Override
    public String toString() {
        return "IncompleteWorkout{" +
                "description='" + description + '\'' +
                ", end='" + end + '\'' +
                ", start='" + start + '\'' +
                ", location='" + location + '\'' +
                ", workoutName='" + workoutName + '\'' +
                '}';
    }
}
