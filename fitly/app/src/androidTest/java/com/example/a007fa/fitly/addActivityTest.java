package com.example.a007fa.fitly;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;
import android.widget.EditText;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Calendar;
import java.util.regex.Pattern;

import static android.service.autofill.Validators.not;
import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;
//import static android.support.test.espresso.contrib.PickerActions;

public class addActivityTest {

    @Rule
    public ActivityTestRule<addActivity> aActivityTestRule = new ActivityTestRule<addActivity>(addActivity.class);

    private addActivity aActivity = null;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(addActivity.class.getName(), null, false);

    @Before
    public void setUp() throws Exception {

        aActivity = aActivityTestRule.getActivity();

    }

    @Test
    public void testWorkoutRetrieveName() {
        Calendar s = Calendar.getInstance();
        Calendar e = Calendar.getInstance();
        int year = s.get(Calendar.YEAR);
        int month = s.get(Calendar.MONTH);
        int day = s.get(Calendar.DAY_OF_MONTH);
        int hour = s.get(Calendar.HOUR_OF_DAY);
        int minute = s.get(Calendar.MINUTE);
        s.set(year, month, day, hour, minute);
        e.set(year, month, day, hour, minute);
        String workoutName = "Test";
        Workout test = new Workout(workoutName, s, e);
        assertEquals("Test", test.getWorkoutName());
    }

    @Test
    public void testWorkoutRetrieveCalendarStartDate(){
        Calendar s = Calendar.getInstance();
        Calendar e = Calendar.getInstance();
        int year = s.get(Calendar.YEAR);
        int month = s.get(Calendar.MONTH); // +1 is because function get starts at 0, but function getStartData starts at 1
        int day = s.get(Calendar.DAY_OF_MONTH);
        int hour = s.get(Calendar.HOUR_OF_DAY);
        int minute = s.get(Calendar.MINUTE);
        s.set(year, month, day, hour, minute);
        e.set(year, month, day, hour, minute);
        String workoutName = "Test";
        Workout test = new Workout(workoutName, s, e);
        assertEquals(((month+1) + "/" + day + "/" + year), test.getStartDate());
    }

    @Test
    public void testWorkoutRetrieveCalendarEndDate(){
        Calendar s = Calendar.getInstance();
        Calendar e = Calendar.getInstance();
        int year = s.get(Calendar.YEAR);
        int month = s.get(Calendar.MONTH); // +1 is because function get starts at 0, but function getStartData starts at 1
        int day = s.get(Calendar.DAY_OF_MONTH);
        int hour = s.get(Calendar.HOUR_OF_DAY);
        int minute = s.get(Calendar.MINUTE);
        s.set(year, month, day, hour, minute);
        e.set(year, month, day, hour, minute);
        String workoutName = "Test";
        Workout test = new Workout(workoutName, s, e);
        assertEquals(((month+1) + "/" + day + "/" + year), test.getEndDate());
    }

    @Test
    public void testWorkoutRetrieveStartCalendar() {
        Calendar s = Calendar.getInstance();
        Calendar e = Calendar.getInstance();
        int year = s.get(Calendar.YEAR);
        int month = s.get(Calendar.MONTH); // +1 is because function get starts at 0, but function getStartData starts at 1
        int day = s.get(Calendar.DAY_OF_MONTH);
        int hour = s.get(Calendar.HOUR_OF_DAY);
        int minute = s.get(Calendar.MINUTE);
        s.set(year, month, day, hour, minute);
        e.set(year, month, day, hour, minute);
        String workoutName = "Test";
        Workout test = new Workout(workoutName, s, e);
        Calendar testRetrieve = test.getStartCalendar();
        int yearTest = testRetrieve.get(Calendar.YEAR);
        int monthTest = testRetrieve.get(Calendar.MONTH);
        int dayTest = testRetrieve.get(Calendar.DAY_OF_MONTH);
        assertEquals(((month+1) + "/" + day + "/" + year),((monthTest+1) + "/" + dayTest + "/" + yearTest));
    }

    @Test
    public void testWorkoutRetrieveEndCalendar() {
        Calendar s = Calendar.getInstance();
        Calendar e = Calendar.getInstance();
        int year = s.get(Calendar.YEAR);
        int month = s.get(Calendar.MONTH); // +1 is because function get starts at 0, but function getStartData starts at 1
        int day = s.get(Calendar.DAY_OF_MONTH);
        int hour = s.get(Calendar.HOUR_OF_DAY);
        int minute = s.get(Calendar.MINUTE);
        s.set(year, month, day, hour, minute);
        e.set(year, month, day, hour, minute);
        String workoutName = "Test";
        Workout test = new Workout(workoutName, s, e);
        Calendar testRetrieve = test.getEndCalendar();
        int yearTest = testRetrieve.get(Calendar.YEAR);
        int monthTest = testRetrieve.get(Calendar.MONTH);
        int dayTest = testRetrieve.get(Calendar.DAY_OF_MONTH);
        assertEquals(((month+1) + "/" + day + "/" + year),((monthTest+1) + "/" + dayTest + "/" + yearTest));
    }

    @Test
    public void testLaunch() {
        assertNotNull(aActivity.findViewById(R.id.workoutName));
        assertNotNull(aActivity.findViewById(R.id.e_pick));
        assertNotNull(aActivity.findViewById(R.id.s_pick));
        assertNotNull(aActivity.findViewById(R.id.show));
    }

    @Test
    public void testLaunchOfStartTimeButtonOnClick() {
        onView(withId(R.id.s_pick)).perform(click());

        Activity addActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);

        assertNotNull(addActivity);

        addActivity.finish();
    }

    @Test
    public void testLaunchOfEndTimeButtonOnClick() {
        onView(withId(R.id.e_pick)).perform(click());

        Activity addActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);

        assertNotNull(addActivity);

        addActivity.finish();
    }


    @Test
    public void checkTextView() throws Exception {
        final EditText workoutName = aActivity.findViewById(R.id.workoutName);
        aActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
               workoutName.setText("123");
            }
        });

      final String entry = workoutName.getText().toString();

      assertEquals(entry, workoutName.getText().toString());
    }

    @Test
    public void testWorkOutNameNotEmpty()
    {
        onView(withId(R.id.show)).perform(click());
        onView(withText("Please enter activity name")).inRoot(new ToastMatcher())
                .check(matches(withText("Please enter activity name")));

    }

    @Test
    public void testSameDate()
    {
        onView(withId(R.id.workoutName)).perform(typeTextIntoFocusedView("Test Workout"));
        onView(withId(R.id.show)).perform(click());
        onView(withText("Activity cannot start and end at the same time")).inRoot(new ToastMatcher())
                .check(matches(withText("Activity cannot start and end at the same time")));


    }

   /*public void testForEarlierStartDate()
    {

        onView(withId(R.id.e_pick)).perform(PickerActions.setDate(2017, 6, 30));
    }*/


    @After
    public void tearDown() throws Exception {
        aActivity = null;
    }

}
