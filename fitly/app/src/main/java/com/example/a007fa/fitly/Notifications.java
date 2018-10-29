package com.example.a007fa.fitly;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

public class Notifications extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String channelId = "channel";
        String channelName = "Channel Name";

        Bundle extras = intent.getExtras();

       // Log.d("JJJJJ" , "MainActivity -> intent key_string-> " + extras.getInt("requestKey"));
        //int requestKey = extras.getInt("requestKey");
       int requestKey = getRequestCode(intent);

        NotificationManager notiManager
                = (NotificationManager) context.getSystemService  (context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notiManager.createNotificationChannel(mChannel);
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                requestKey,
                new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mNotification = new NotificationCompat.Builder(context, channelId)
                .setContentIntent(pendingIntent)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("fit.ly")
                .setContentText("You've workout scheduled in 3 hours.")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setAutoCancel(true)
                .setDefaults(android.app.Notification.DEFAULT_VIBRATE)
                .setChannelId(channelId);
       // (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE)
        notiManager.notify(requestKey,
                mNotification.build());
    }

    public int getRequestCode(Intent  intent){

        Bundle extras = intent.getExtras();

        // Log.d("JJJJJ" , "MainActivity -> intent key_string-> " + extras.getInt("requestKey"));
        int requestKey = extras.getInt("requestKey");

        return requestKey;
    }

}
