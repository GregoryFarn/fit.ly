package com.example.a007fa.fitly;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.app.Service;

public class saveAlarm extends Service {

    static final String ACTION_FITLY = "com.fitly.action.FITLY";
    static final String ACTION_ENDDAY = "com.fitly.action.ENDDAY";

    public void onCreate() {
        Intent intent1 = new Intent(getApplicationContext(), Dashboard.class);
        intent1.setAction(ACTION_ENDDAY);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent1);
    }
    public IBinder onBind(Intent intent) {
        return null;
    }
}
