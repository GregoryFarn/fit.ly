package com.example.a007fa.fitly;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.a007fa.fitly.Dashboard;
import com.example.a007fa.fitly.R;

import java.util.Date;

public class AlarmReceive extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String channelId = "channel";
        String channelName = "Channel Name";
        NotificationManager notiManager
                = (NotificationManager) context.getSystemService  (Service.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notiManager.createNotificationChannel(mChannel);
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, Dashboard.class), PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mNotification = new NotificationCompat.Builder(context, channelId)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("New Message")
                .setContentText("You've received new messages.")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(pendingIntent)
                .setChannelId(channelId);
        notiManager.notify((int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE), mNotification.build());
    }

}
