package com.example.a007fa.fitly;

import java.util.ArrayList;
import java.util.Calendar;


import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasFocus;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.*;
import org.junit.Test;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.regex.Pattern;

import static android.service.autofill.Validators.not;
import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class LogInTest {
    private LogInActivity mActivity = null;

    @Rule
    public ActivityTestRule mActivityTestRule = new ActivityTestRule(LogInActivity.class);




    @Test
    public void testStepsText() {
        onView(withId(R.id.stepsText)).check(matches(withText("steps walked today")));
    }

    @Test
    public void testEmptyEmail() {

    }

}
