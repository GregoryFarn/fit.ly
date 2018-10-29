package com.example.a007fa.fitly;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.UiController;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import com.google.gson.annotations.Until;

import org.hamcrest.core.AllOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

public class NotificationsTest {

//    @Rule
//    public ActivityTestRule mActivityRule = new ActivityTestRule(Notifications.class);

    UiDevice mDevice;
    private Intent intent = new Intent(getInstrumentation().getTargetContext(), Notifications.class);
    private Notifications test = new Notifications();

    @Before
    public void setUp(){
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        Bundle bundle = new Bundle();

        bundle.putInt("requestKey", 2);
        this.intent.putExtras(bundle);
        test.onReceive(getInstrumentation().getTargetContext(), this.intent);
    }

    @Test
    public void testNotification(){
        mDevice.openNotification();
    }
//    @Test
//    public void testNotification(){
//        Notifications t1 = new Notifications();
//      //  t1.onReceive(getInstrumentation().getTargetContext(), this.intent);
//    }


    @Test
    public void getBundleTest(){

      //  Intent intent = new Intent(getInstrumentation().getTargetContext(), Notifications.class);

        assertEquals("Expected request int value 2", test.getRequestCode(intent),2 );


    }

}