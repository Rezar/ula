/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 2/9/19 10:21 PM
 */

package com.ula.gameapp.core.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.ula.gameapp.core.Annotation.PedometerDef;
import com.ula.gameapp.core.service.ActivityTracker;
import com.ula.gameapp.core.service.SensorListener;

import static com.ula.gameapp.core.Annotation.PEDOMETER_GOOGLE_FIT;
import static com.ula.gameapp.core.Annotation.PEDOMETER_SENSOR;
import static com.ula.gameapp.core.Annotation.PEDOMETER_UNKNOWN;

public class PedometerManager {
    private static @PedometerDef
    int pedometerType = PEDOMETER_UNKNOWN;

    public static @PedometerDef
    int getPedometerType(Context context) {
        if (pedometerType == PEDOMETER_UNKNOWN)
            loadPedometerType(context);

        return pedometerType;
    }

    public static void loadPedometerType(Context context) {
        SharedPreferences pref = context.getSharedPreferences("UlaSettings", Context.MODE_PRIVATE);
        pedometerType = pref.getInt("pedometer_type", 0);
    }

    public static void setPedometerType(Context context, @PedometerDef int type) {
        SharedPreferences pref = context.getSharedPreferences("UlaSettings", Context.MODE_PRIVATE);
        pref.edit().putInt("pedometer_type", type).commit();
    }

    public static void initializePedometer(Activity activity) {
        switch (getPedometerType(activity)) {
            case PEDOMETER_SENSOR:
                startPedometerService(activity);
                break;

            case PEDOMETER_GOOGLE_FIT:
                if (GoogleFit.isFitInstalled(activity))
                    GoogleFit.initializeGoogleFit(activity);
                else
                    startPedometerService(activity);
                break;
        }
    }

    private static void startPedometerService(Activity activity) {
        // start pedometer service
//        Intent i = new Intent(activity, SensorListener.class);
        Intent i = new Intent(activity, ActivityTracker.class);
        activity.startService(i);

    }
}
