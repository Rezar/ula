package com.ula.wear.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import com.ula.wear.activitytracker.ActivityTracker;

public class ServiceMonitor extends Service {


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        final Context _context = this;



        new Thread(new Runnable() {
            Context context = _context;

            public void run() {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                    startForegroundService(new Intent(context, ActivityTracker.class));

                } else {
                    startService(new Intent(context, ActivityTracker.class));
                }

            }
        }).start();

        return START_NOT_STICKY;

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}