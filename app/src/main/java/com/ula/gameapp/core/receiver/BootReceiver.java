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

import com.ula.gameapp.BuildConfig;
import com.ula.gameapp.core.logger.CatLogger;
import com.ula.gameapp.core.service.SensorListener;
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

        try {
            context.startService(new Intent(context, SensorListener.class));
        } catch (Exception ex) {
            // we can't run the service
        }
    }
}
