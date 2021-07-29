package com.ula.gameapp.core.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.ula.gameapp.App;
import com.ula.gameapp.ViewModels.FootStepViewModel;
import com.ula.gameapp.activitytracker.TensorFlowClassifier;
import com.ula.gameapp.core.logger.CatLogger;
import com.ula.gameapp.core.receiver.ShutdownReceiver;
import com.ula.gameapp.database.AppDatabase;
import com.ula.gameapp.database.DatabaseClient;
import com.ula.gameapp.database.dao.FootStepDao;
import com.ula.gameapp.item.FootStep;
import com.ula.gameapp.utils.CalendarUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

//Bavan Divaani-azar

public class ActivityTracker extends Service implements SensorEventListener {


    private static final int N_SAMPLES = 200;
    private static List<Float> x;
    private static List<Float> y;
    private static List<Float> z;
    public final static int NOTIFICATION_ID = 1;
    private final BroadcastReceiver shutdownReceiver = new ShutdownReceiver();

    private float[] results;
    private TensorFlowClassifier classifier;
    private FootStepViewModel footStepViewModel;
    private FootStepDao footStepDao;

    private  int idx=-1;

    private String[] labels = {"Downstairs", "Jogging", "Sitting", "Standing", "Upstairs", "Walking"};

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {

        getSensorManager().registerListener(this, getSensorManager().getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
        Sensor accel = getSensorManager().getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        if (accel != null) {
            getSensorManager().registerListener(this, accel, SensorManager.SENSOR_DELAY_UI);
        }
        x = new ArrayList<>();
        y = new ArrayList<>();
        z = new ArrayList<>();

        classifier = new TensorFlowClassifier(getApplicationContext());
        footStepViewModel = new FootStepViewModel();
        AppDatabase appDatabase = DatabaseClient.getInstance(App.getContext()).getAppDatabase();
        footStepDao = appDatabase.footStepDao();
        NotificationCompat.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
            builder = new NotificationCompat.Builder(this, "10").
                    setPriority(NotificationCompat.PRIORITY_MIN);
        } else
            builder = new NotificationCompat.Builder(this)
                    .setPriority(NotificationCompat.PRIORITY_MIN);


        Notification notification = builder.build();
        startForeground(NOTIFICATION_ID, notification);

//        reRegisterSensor();
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

    private void registerBroadcastReceiver() {
        CatLogger.get().log("register broadcastreceiver");

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SHUTDOWN);
        registerReceiver(shutdownReceiver, filter);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "step counter";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("10", name, importance);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    private SensorManager getSensorManager() {
        return (SensorManager) getSystemService(SENSOR_SERVICE);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        Log.v("sensor_model", sensor.getType() + "");

        activityPrediction();
        if (sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {

            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("ulaData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            int step = sharedPreferences.getInt("steps", 0);
            step++;
            editor.putInt("steps", step);
            editor.apply();
            ArrayList<FootStep> stepsList = new ArrayList<>();
            FootStep footStep = new FootStep();
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            footStep.setType(5);
            footStep.setDate(cal.getTime());
            footStep.setStepCount(step);
            stepsList.add(footStep);
            if (stepsList.size() > 0) {
                footStepDao.insertStepsHistory(stepsList);
            }
            if(idx!=-1) {
                step = sharedPreferences.getInt(idx + "", 0);
                step++;
                editor.putInt(idx + "", step);
                editor.apply();
            }
        }
        if (event.sensor.getType() == 1) {
            x.add(event.values[0]);
            y.add(event.values[1]);
            z.add(event.values[2]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private void activityPrediction() {
        if (x.size() == N_SAMPLES && y.size() == N_SAMPLES && z.size() == N_SAMPLES) {
            List<Float> data = new ArrayList<>();
            data.addAll(x);
            data.addAll(y);
            data.addAll(z);

            results = classifier.predictProbabilities(toFloatArray(data));

            float max = -1;
            for (int i = 0; i < results.length; i++) {
                if (results[i] > max) {
                    idx = i;
                    max = results[i];
                }
            }


            Log.v("Activity_Tracker", labels[idx] + " ");

            x.clear();
            y.clear();
            z.clear();

        }
    }


    private float[] toFloatArray(List<Float> list) {
        int i = 0;
        float[] array = new float[list.size()];

        for (Float f : list) {
            array[i++] = (f != null ? f : Float.NaN);
        }
        return array;
    }

    private static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}