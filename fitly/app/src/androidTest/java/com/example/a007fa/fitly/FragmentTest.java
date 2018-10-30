package com.example.a007fa.fitly;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.test.espresso.AmbiguousViewMatcherException;
import android.support.test.espresso.action.ViewActions;
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
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)

public class FragmentTest {
    public static Matcher<View> withIndex(final Matcher<View> matcher, final int index) {
        return new TypeSafeMatcher<View>() {
            int currentIndex = 0;

            @Override
            public void describeTo(Description description) {
                description.appendText("with index: ");
                description.appendValue(index);
                matcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                return matcher.matches(view) && currentIndex++ == index;
            }
        };
    }

    @Rule
    public ActivityTestRule mActivityRule = new ActivityTestRule(MainActivity.class, false, true);





    @Before
    public void init(){
        //mActivityRule.getActivity().recreate();
        mActivityRule.getActivity()
                .getFragmentManager().beginTransaction();

    }

    @Test
    public void testBadgeDisplay()
    {
            //on success bages page will appear on the device
            onView(withIndex(withId(R.id.navigation_badges),1)).perform(click());


    }

    @Test
    public void testProfileDisplay()
    {
        //on success bages page will appear on the device
        onView(withIndex(withId(R.id.navigation_profile),1)).perform(click());

    }



    @Test
    public void testDashboardDisplay()
    {
        //on success bages page will appear on the device
        onView(withIndex(withId(R.id.navigation_dashboard),1)).perform(click());

    }












}
