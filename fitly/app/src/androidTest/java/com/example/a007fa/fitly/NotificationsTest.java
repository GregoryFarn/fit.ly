package com.example.a007fa.fitly;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;

import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

public class NotificationsTest {


    @Test
    public void getBundleTest(){
        Intent intent = new Intent(getInstrumentation().getTargetContext(), Notifications.class);
        Bundle bundle = new Bundle();
        bundle.putInt("requestKey", 2);
        intent.putExtras(bundle);

        Notifications t1 = new Notifications();
        t1.onReceive(getInstrumentation().getTargetContext(), intent);

        assertEquals(t1.getRequestCode(intent),2 );

    }

}