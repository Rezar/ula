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
import android.graphics.Color;
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
import com.ula.gameapp.activitytracker.TensorFlowClassifier;
import com.ula.gameapp.core.logger.CatLogger;
import com.ula.gameapp.core.receiver.ShutdownReceiver;
import com.ula.gameapp.database.AppDatabase;
import com.ula.gameapp.database.DatabaseClient;
import com.ula.gameapp.utils.CalendarUtil;

import org.json.JSONException;
import org.json.JSONObject;

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
    int stepCount;

    private float[] results;
    private TensorFlowClassifier classifier;

    private int idx = -1;

    private String[] labels = {"downstairs", "jogging", "sitting", "standing", "upstairs", "walking"};

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
        NotificationCompat.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
            builder = new NotificationCompat.Builder(this, "10").
                    setPriority(NotificationCompat.PRIORITY_LOW);
        } else
            builder = new NotificationCompat.Builder(this)
                    .setPriority(NotificationCompat.PRIORITY_LOW);

        builder.setSilent(true);

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
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel channel = new NotificationChannel("10", name, importance);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

            channel.setSound(null, null);
            channel.enableLights(false);
            channel.setLightColor(Color.BLUE);
            channel.enableVibration(false);
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

            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);

            String data = sharedPreferences.getString("stepsData", "{}");
            try {
                JSONObject obj = new JSONObject(data);
                String date = cal.getTime() + "";

                if (!obj.has(date)) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("total_steps", 0);
                    jsonObject.put("google_fitness", 0);
                    jsonObject.put("downstairs", 0);
                    jsonObject.put("jogging", 0);
                    jsonObject.put("upstairs", 0);
                    jsonObject.put("walking", 0);
                    jsonObject.put("sitting", 0);
                    jsonObject.put("standing", 0);
                    obj.put(date, jsonObject);
                }
                JSONObject jsonObject = obj.getJSONObject(date);
                int step = jsonObject.getInt("total_steps");
                step++;
                jsonObject.put("total_steps", step);

//                ArrayList<FootStep> stepsList = new ArrayList<>();
//                FootStep footStep = new FootStep();
//                footStep.setType(5);
//                footStep.setDate(cal.getTime());
//                footStep.setStepCount(step);
//                stepsList.add(footStep);
//                if (stepsList.size() > 0) {
//                    footStepDao.insertStepsHistory(stepsList);
//                }
                if (idx != -1) {
                    step = jsonObject.getInt(labels[idx]);
                    step++;
                    jsonObject.put(labels[idx], step);
                }
                obj.put(date, jsonObject);
                editor.putString("stepsData", obj.toString());
                Log.v("stepsData", obj.toString());
                editor.apply();
            } catch (JSONException e) {
                e.printStackTrace();
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