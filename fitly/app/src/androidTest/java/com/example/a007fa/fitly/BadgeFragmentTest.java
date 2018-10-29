package com.example.a007fa.fitly;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.test.espresso.AmbiguousViewMatcherException;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.Fragment;
import android.support.v4.app.Fragment;
import android.util.Log;

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

import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class BadgeFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);




    @Before
    public void init(){
        mActivityRule.getActivity()
                .getSupportFragmentManager().beginTransaction();
    }

    @Test
    public void testBadgeDisplay()
    {
        try {
            onView(withId(R.id.navigation_badges)).perform(click());
            FragmentManager fragmentManager = mActivityRule.getActivity().getFragmentManager();
            if(fragmentManager.findFragmentById(R.id.navigation_badges)!=null)
            {
                Log.d("found", "FoundtestBadgeDisplay: ");
            }
            else
            {
                Log.d("found", "DidNotFindtestBadgeDisplay: ");

            }

        }
        catch(AmbiguousViewMatcherException ave)
        {
            Log.e("ave error ", "testBadgeDisplay: ");
        }
    }

    /*@Test
    public void testBadgePage()
    {
        ArrayList<Badge> badges= new ArrayList<Badge>();
        for(int i=0; i<10; i++)
        {
            Badge badgeTest= new Badge("small", false);
            if(i%7==0)
                badgeTest.setTypeOfBadge("big");
            badgeTest.setCompleted(true);
            badges.add(badgeTest);
        }
        Badge badgesTest=new Badge("small",false);
        badges.add(badgesTest);
        badgeWrapper bw= new badgeWrapper(badges);
        Intent intent1 = new Intent();
        intent1.putExtra("badgeList",bw);
        mActivityRule.launchActivity(intent1);
    }*/

}
