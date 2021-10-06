package com.ula.wear;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ActivityTracker extends Service implements SensorEventListener {


    private static final int N_SAMPLES = 200;
    private static List<Float> x;
    private static List<Float> y;
    private static List<Float> z;
    private float[] results;
    int idx = -1;

    public final static int NOTIFICATION_ID = 1;

    private TensorFlowClassifier classifier;
    private String[] labels = {"downstairs", "jogging", "sitting", "standing", "upstairs", "walking"};

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {

        Sensor accel2= getSensorManager().getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if(accel2!=null)
        {
            getSensorManager().registerListener(this, getSensorManager().getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
        }

        Sensor accel = getSensorManager().getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        if (accel != null) {
            getSensorManager().registerListener(this, accel, SensorManager.SENSOR_DELAY_UI);
        }
        x = new ArrayList<>();
        y = new ArrayList<>();
        z = new ArrayList<>();

        NotificationCompat.Builder builder;
        classifier = new TensorFlowClassifier(getApplicationContext());

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


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("ulaData", Context.MODE_PRIVATE);
                String data = sharedPreferences.getString("stepsData", "{}");
                Log.v("data-message",data);
                assert data != null;
                if(!data.equals("{}")) {
                    String datapath = "/my_path";
                    new MainActivity.SendMessage(datapath, data, getApplicationContext()).start();
                }
                handler.postDelayed(this, 300000); //now is every 5 minutes
            }
        }, 300000); //Every 120000 ms (5 minutes)




        return START_STICKY;
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
            stepDetect();
        }
        if (event.sensor.getType() == 1) {
            x.add(event.values[0]);
            y.add(event.values[1]);
            z.add(event.values[2]);

        }

    }


    private void stepDetect() {
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
                data="{}";
                obj = new JSONObject(data);
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
            int step = 0;

            if (idx != -1) {

            if (idx != 2 && idx != 3) {
                step = jsonObject.getInt("total_steps");
                step++;
                jsonObject.put("total_steps", step);
            }
                step = jsonObject.getInt(labels[idx]);
                step++;
                jsonObject.put(labels[idx], step);
            }
            obj.put(date, jsonObject);
            editor.putString("stepsData", obj.toString());
            Log.v("stepsData", obj.toString());
            editor.apply();

            Log.v("data",data);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private int activityPrediction() {


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
        return idx;
    }

    private float[] toFloatArray(List<Float> list) {
        int i = 0;
        float[] array = new float[list.size()];

        for (Float f : list) {
            array[i++] = (f != null ? f : Float.NaN);
        }
        return array;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onTaskRemoved(Intent rootIntent){
        Intent restartServiceIntent = new Intent(getApplicationContext(), this.getClass());
        restartServiceIntent.setPackage(getPackageName());

        PendingIntent restartServicePendingIntent = PendingIntent.getService(getApplicationContext(), 1, restartServiceIntent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmService = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmService.set(
                AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + 1000,
                restartServicePendingIntent);

        super.onTaskRemoved(rootIntent);
    }
}