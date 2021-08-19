/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 1/10/19 3:41 PM
 */

package com.ula.gameapp.core.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ula.gameapp.core.logger.CatLogger;
import com.ula.gameapp.core.service.ActivityTracker;
import com.ula.gameapp.core.service.SensorListener;

public class AppUpdatedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {
        CatLogger.get().log("app updated");

        try {
            context.startService(new Intent(context, ActivityTracker.class));
        } catch (Exception ex) {
            // we can't run the service
        }
    }
}
