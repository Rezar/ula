package com.ula.wear.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.ula.wear.activitytracker.ActivityTracker;

public class StartupService extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent serviceIntent = new Intent(context, ServiceMonitor.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        final AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.setRepeating(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis(),
                600000L,
                pendingIntent);


        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {

            Log.d("booy_complete", "yeahh finally");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                 ContextCompat.startForegroundService(context,new Intent(context, ActivityTracker.class));

            } else {
                context.startService(new Intent(context, ActivityTracker.class));
            }




        }


    }



}
