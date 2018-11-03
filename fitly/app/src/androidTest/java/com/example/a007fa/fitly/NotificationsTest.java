package com.example.a007fa.fitly;


import android.app.Activity;
import android.app.Instrumentation;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;

import android.support.test.rule.ActivityTestRule;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.AllOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import androidx.test.uiautomator.UiDevice;


import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;
import static android.support.test.InstrumentationRegistry.getContext;
import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.hasFocus;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.v4.content.ContextCompat.getSystemService;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.*;

public class NotificationsTest extends Fragment {

    @Rule
    public ActivityTestRule<addActivity> aActivityTestRule = new ActivityTestRule<addActivity>(addActivity.class);

    private addActivity aActivity = null;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(addActivity.class.getName(), null, false);

    @Before
    public void setUp() throws Exception {
    
        aActivity = aActivityTestRule.getActivity();

    }

    @Test
    public void testNotification(){
        //mActivityRule.getActivity();

        aActivity.findViewById(R.id.s_pick).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                NotificationCompat.Builder mBuilder= new NotificationCompat.Builder(InstrumentationRegistry.getTargetContext())
                        .setContentText("notification")
                        .setContentTitle("fitly")
                        .setDefaults(Notification.DEFAULT_VIBRATE)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(true);
                NotificationManager mNotificationManager = (NotificationManager) InstrumentationRegistry.getTargetContext().getSystemService(NOTIFICATION_SERVICE);
                mNotificationManager.notify(0,mBuilder.build());
            }
        });
        onView(withId(R.id.s_pick)).perform(click());
        Activity addActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);

       // assertNotNull(addActivity);

        addActivity.finish();
        //onView(allOf(withId(R.id.show), hasFocus())).perform(click());

    }



    @Test
    public void getBundleTest(){
        Notifications test = new Notifications();
        Intent intent = new Intent(InstrumentationRegistry.getTargetContext(), MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("requestKey", 2);
        intent.putExtras(bundle);

        assertEquals("Expected request int value 2", test.getRequestCode(intent),2 );


    }

}