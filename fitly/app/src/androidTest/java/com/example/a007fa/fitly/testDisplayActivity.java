package com.example.a007fa.fitly;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.test.espresso.AmbiguousViewMatcherException;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.Fragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import static android.support.test.espresso.matcher.ViewMatchers.hasFocus;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class testDisplayActivity {

    @Rule
    public ActivityTestRule da= new ActivityTestRule(DisplayWorkoutDetailsActivity.class, false, false);

    @Test
    public void testActivityDisplay() {

        Schedule test = new Schedule();
        test.initTest();
        Intent it = new Intent();
        it.putExtra("Name", test.getWorkouts().get(0).getWorkoutName());
        it.putExtra("Location", test.getWorkouts().get(0).getLocation());
        da.launchActivity(it);

        //...
    }

}
