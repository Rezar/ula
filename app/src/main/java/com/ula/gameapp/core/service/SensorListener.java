/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 1/10/19 3:42 PM
 */

package com.ula.gameapp.core.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.IBinder;

import com.ula.gameapp.BuildConfig;
import com.ula.gameapp.core.logger.CatLogger;
import com.ula.gameapp.core.receiver.ShutdownReceiver;
import com.ula.gameapp.utils.CalendarUtil;

import java.util.Date;

/**
 * Background service which keeps the step-sensor listener alive to always get
 * the number of steps since boot.
 * <p/>
 * This service won't be needed any more if there is a way to read the
 * step-value without waiting for a sensor event
 */
public class SensorListener extends Service implements SensorEventListener {

    public final static int NOTIFICATION_ID = 1;
    private final static long MICROSECONDS_IN_ONE_MINUTE = 60000000;
    private final static long SAVE_OFFSET_TIME = AlarmManager.INTERVAL_HOUR;
    private final static int SAVE_OFFSET_STEPS = 500;

    private static int steps;
    private static int lastSaveSteps;
    private static long lastSaveTime;

    private final BroadcastReceiver shutdownReceiver = new ShutdownReceiver();

    @Override
    public void onAccuracyChanged(final Sensor sensor, int accuracy) {
        CatLogger.get().log(sensor.getName() + " accuracy changed: " + accuracy);
    }

    @Override
    public void onSensorChanged(final SensorEvent event) {
        if (event.values[0] > Integer.MAX_VALUE) {
            CatLogger.get().log("probably not a real value: " + event.values[0]);
        } else {
            steps = (int) event.values[0];
        }
    }

    @Override
    public IBinder onBind(final Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        reRegisterSensor();
        registerBroadcastReceiver();

//        if (!updateIfNecessary()) {
//            showNotification();
//        }

        // restart service every half hour to save the current step count
        long nextUpdate = Math.min(CalendarUtil.getTomorrow(), System.currentTimeMillis() + AlarmManager.INTERVAL_HALF_HOUR);

        CatLogger.get().log("next update: " + new Date(nextUpdate).toLocaleString());

        AlarmManager am = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        PendingIntent pi = PendingIntent.getService(getApplicationContext(), 2, new Intent(this, SensorListener.class), PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= 23) {
            am.setAndAllowWhileIdle(AlarmManager.RTC, nextUpdate, pi);
        } else {
            am.set(AlarmManager.RTC, nextUpdate, pi);
        }

        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CatLogger.get().log("SensorListener onCreate");
    }

    @Override
    public void onTaskRemoved(final Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        CatLogger.get().log("sensor service task removed");

        // Restart service in 500 ms
        ((AlarmManager) getSystemService(Context.ALARM_SERVICE)).set(AlarmManager.RTC, System.currentTimeMillis() + 500, PendingIntent.getService(this, 3, new Intent(this, SensorListener.class), 0));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        CatLogger.get().log("SensorListener onDestroy");

        try {
            unregisterReceiver(shutdownReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
            sm.unregisterListener(this);
        } catch (Exception e) {
            CatLogger.get().log(e);
            e.printStackTrace();
        }
    }

    private void registerBroadcastReceiver() {
        CatLogger.get().log("register broadcastreceiver");

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SHUTDOWN);
        registerReceiver(shutdownReceiver, filter);
    }

    private void reRegisterSensor() {
        CatLogger.get().log("re-register sensor listener");

        SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        try {
            sm.unregisterListener(this);
        } catch (Exception e) {
            CatLogger.get().log(e);
            e.printStackTrace();
        }

        if (BuildConfig.DEBUG) {
            CatLogger.get().log("step sensors: " + sm.getSensorList(Sensor.TYPE_STEP_COUNTER).size());
            if (sm.getSensorList(Sensor.TYPE_STEP_COUNTER).size() < 1) return; // emulator
            CatLogger.get().log("default: " + sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER).getName());
        }

        // enable batching with delay of max 5 min
        sm.registerListener(this, sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER), SensorManager.SENSOR_DELAY_NORMAL, (int) (5 * MICROSECONDS_IN_ONE_MINUTE));
    }
}
