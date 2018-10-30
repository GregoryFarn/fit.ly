package com.example.a007fa.fitly;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

public class AlarmTest {
    @Test
    public void testGetNotificationTime(){
        Calendar scheduledTime = Calendar.getInstance();
        scheduledTime.set(2018, 10, 26, 16, 00);
        Calendar expectedTime = Calendar.getInstance();
        expectedTime.set(2018,10,26,13,00);

        Alarm t1 = new Alarm(getInstrumentation().getTargetContext(),1, scheduledTime);
        scheduledTime.setTimeInMillis(t1.calculateTime());

        assertEquals("Expected hour :  13 (1:00pm) (Notification should appear 3 hrs before scheulded time",  expectedTime.HOUR, scheduledTime.HOUR);
        assertEquals("Expected minute: 00",expectedTime.MINUTE, scheduledTime.MINUTE);
    }
    @Test
    public void testForDateChanged(){

        Calendar scheduledTime2 = Calendar.getInstance();
        scheduledTime2.set(2018, 10, 26, 01, 00);
        Calendar expectedTime2 = Calendar.getInstance();
        expectedTime2.set(2018,10,25,13,00);

        Alarm t2 = new Alarm(getInstrumentation().getTargetContext(),1, scheduledTime2);
        scheduledTime2.setTimeInMillis(t2.calculateTime());
        assertEquals("Expected date : 25th of November ",  expectedTime2.DATE, scheduledTime2.DATE);

    }
    @Test
    public void testAlarmSet1(){
        Calendar expectedTime = Calendar.getInstance();
        expectedTime.set(2018,10,25,13,00);
        Alarm t3 = new Alarm(getInstrumentation().getTargetContext(),1, expectedTime);
        t3.setAlarm();
        assertTrue("Return true, if notification time(scheduled time - 3hrs) has not passed yet.",t3.isAlarmSet());
    }
    @Test
    public void testAlarmSet2(){
        Calendar expectedTime = Calendar.getInstance();
        expectedTime.set(2018,9,25,13,00);
        Alarm t4 = new Alarm(getInstrumentation().getTargetContext(),1, expectedTime);
        t4.setAlarm();
        assertFalse("Return false, if notification time(scheduled time - 3hrs) has passed.",t4.isAlarmSet());
    }

}