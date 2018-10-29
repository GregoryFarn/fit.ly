package com.example.a007fa.fitly;

import org.junit.Test;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Calendar;

public class TestSchedule {
    @Test
    public void testCreateSchedule() {
        Schedule schedule = new Schedule();
        Assert.assertEquals(schedule.getWorkouts().size(), 0);
    }

    @Test
    public void testGetWorkouts() {
        ArrayList<Workout> workouts = new ArrayList<Workout>();
        Schedule schedule = new Schedule(workouts);
        Assert.assertSame(schedule.getWorkouts(), workouts);
    }

    @Test
    public void testAddWorkout() {
        Schedule schedule = new Schedule();

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

        schedule.addWorkout(w1);
        schedule.addWorkout(w2);
        schedule.addWorkout(w3);

        Assert.assertEquals(schedule.getWorkouts().size(), 3);
    }

    @Test
    public void testRemoveWorkout() {
        Schedule schedule = new Schedule();

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

        schedule.addWorkout(w1);
        schedule.addWorkout(w2);
        schedule.addWorkout(w3);

        Assert.assertEquals(schedule.getWorkouts().size(), 3);

        schedule.removeWorkout(w1);
        Assert.assertEquals(schedule.getWorkouts().size(), 2);
    }
}
