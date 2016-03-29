package com.example.testpageparser;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AlarmManagerReceiver extends BroadcastReceiver {

    NotificationManager manager;
    Notification myNotication;

    @Override
    public void onReceive(Context context, Intent intent) {
        new UpdatingThread(context.getContentResolver()).execute();

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
        wl.acquire();
        createNotification(context);
        wl.release();
    }

    public void createNotification(Context context) {
        Intent intent = new Intent(context, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent, 0);

        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification.Builder builder = new Notification.Builder(context);

        builder.setAutoCancel(true);
        builder.setTicker("this is ticker text");
        builder.setContentTitle("News Aggregator notification");
        builder.setContentText("Your content has been updated");
        builder.setSmallIcon(R.drawable.logo);
        builder.setContentIntent(pendingIntent);
        builder.setOngoing(true);
        builder.setSubText("Click here for move to application");   //API level 16
        builder.setNumber(100);

        myNotication = builder.build();
        manager.notify(11, myNotication);
    }

    public void setAlarm(Context context, int hours, int minutes)
    {
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, 0);

        DateFormat formatter = new SimpleDateFormat("kk:mm:ss:SSS");
        Date date = new Date(System.currentTimeMillis());

        Toast.makeText(context, "Current Android Time: " + formatter.format(date) + "\n"
               + "Set notification time: " + formatter.format(calendar.getTimeInMillis()), Toast.LENGTH_LONG).show();

        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);
    }

    public void cancelAlarm(Context context)
    {
        Intent intent = new Intent(context, AlarmManagerReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

    public void setOnetimeTimer(Context context){
        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pi);
    }
}