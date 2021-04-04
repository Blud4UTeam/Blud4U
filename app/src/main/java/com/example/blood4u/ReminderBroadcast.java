package com.example.blood4u;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

public class ReminderBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //vv When click notification bar intent to InfoActivity
        Intent notificationIntent = new Intent(context, Info.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pending = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //vv create builder variable for build notification.
        @SuppressLint("WrongConstant") NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifyBud")
                .setSmallIcon(R.drawable.blooddrop128)
                .setColor(ContextCompat.getColor(context, R.color.red))
                .setContentTitle("Blood donation Reminder")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pending)
                .setAutoCancel(true);
        //vv create variable for counting the day on notification bar.
        SharedPreferences sharedPrefs = context.getApplicationContext().getSharedPreferences("PopActivity", Context.MODE_PRIVATE);
        int counter = sharedPrefs.getInt("KEY_NAME", 0);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        //vv NotificationManager for build notification.
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        //vv If order for check counting day on notification if day count to 0 stop alarm.
        if (counter % 7 == 0 && counter != 0) {
            builder.setContentText("You can donate your blood in " + String.valueOf(counter-1) + " Day");
            notificationManager.notify(200, builder.build());
            editor.putInt("KEY_NAME", counter-1);
            editor.apply();
        } else if (counter == 1) {
            builder.setContentText("You can donate your blood now. Don't forget to prepare yourself!");
            notificationManager.notify(200, builder.build());
            PopActivity.alarmManager.cancel(PopActivity.pendingIntent);
        } else {
            editor.putInt("KEY_NAME", counter-1);
            editor.apply();
        }
    }
}