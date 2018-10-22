package com.example.a007fa.fitly;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

public class saveAlarm extends BroadcastReceiver {

    static final String ACTION_FITLY = "com.fitly.action.FITLY";
    static final String ACTION_ENDDAY = "com.fitly.action.ENDDAY";

    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context, Dashboard.class);
        intent1.setAction(ACTION_ENDDAY);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
