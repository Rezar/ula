/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 1/10/19 3:42 PM
 */

package com.ula.gameapp.core.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ula.gameapp.core.logger.CatLogger;
import com.ula.gameapp.core.service.ActivityTracker;

public class ShutdownReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {
        CatLogger.get().log("shutting down");

        try {
            Intent serviceIntent = new Intent(context, ActivityTracker.class);
            context.startService(serviceIntent);
        } catch (Exception ex) {
            // we can't run the service
        }

        // if the user used a root script for shutdown, the DEVICE_SHUTDOWN
        // broadcast might not be send. Therefore, the app will check this
        // setting on the next boot and displays an error message if it's not
        // set to true
        context.getSharedPreferences("pedometer", Context.MODE_PRIVATE).edit().putBoolean("correctShutdown", true).apply();

//        StepDao stepDao = DatabaseClient.getInstance(context).getAppDatabase().stepDao();

//        if (stepDao.hasStepByDate(CalendarUtil.getStartOfToday()) == 0) {
//            StepDaoUtils.insertNewDay(context, CalendarUtil.getStartOfToday(), Step.getCurrentSteps(context));
//        } else {
//            stepDao.addToLastSteps(Step.getCurrentSteps(context));
//        }
    }
}
