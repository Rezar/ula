/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 1/10/19 3:41 PM
 */

package com.ula.gameapp.core.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import com.google.firebase.BuildConfig;
import com.ula.gameapp.core.logger.CatLogger;
import com.ula.gameapp.core.service.ActivityTracker;
import com.ula.gameapp.item.Step;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {
        CatLogger.get().log("booted");

//        StepDao stepDao = DatabaseClient.getInstance(context).getAppDatabase().stepDao();
        SharedPreferences prefs = context.getSharedPreferences("pedometer", Context.MODE_PRIVATE);

        if (!prefs.getBoolean("correctShutdown", false)) {
            if (BuildConfig.DEBUG)
                CatLogger.get().log("Incorrect shutdown");

            // can we at least recover some steps?
            int steps = Math.max(0, Step.getCurrentSteps(context));
            CatLogger.get().log("Trying to recover " + steps + " steps");
//            stepDao.addToLastSteps(steps);
        }

        // last entry might still have a negative step value, so remove that
        // row if that's the case
//        stepDao.deleteNegativeSteps();
        Step.saveCurrentSteps(context, 0);
        prefs.edit().remove("correctShutdown").apply();

        Intent intent2 = new Intent(context,ActivityTracker.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent2);
        } else {
            context.startService(intent2);
        }
        Log.i("Autostart", "started");


        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {

//
//            Intent intent1 = new Intent(context, ResetSensorReceiver.class);
//            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent1, 0);
//            AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
//
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTimeInMillis(System.currentTimeMillis());
//            calendar.set(Calendar.HOUR_OF_DAY, 11);
//            calendar.set(Calendar.MINUTE, 59);
//            calendar.set(Calendar.SECOND, 0);
//            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//                    AlarmManager.INTERVAL_DAY, pendingIntent);


            Intent serviceIntent = new Intent(context, ActivityTracker.class);
            context.startService(serviceIntent);

        }

    }
}
