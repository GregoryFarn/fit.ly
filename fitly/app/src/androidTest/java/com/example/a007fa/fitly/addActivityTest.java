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

import java.util.regex.Pattern;

import static android.service.autofill.Validators.not;
import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

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

    @After
    public void tearDown() throws Exception {
        aActivity = null;
    }
}
