package com.example.a007fa.fitly;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;

public class saveAlarm extends BroadcastReceiver {

    static final String ACTION_ENDDAY = "com.fitly.action.ENDDAY";
    static final String ACTION_FITLY = "com.fitly.action.FITLY";

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent intent1 = new Intent(context, fitlyHandler.class);
        intent1.setAction(ACTION_ENDDAY);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent1);

    }

}