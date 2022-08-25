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
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;
import com.ula.gameapp.activitytracker.TensorFlowClassifier;
import com.ula.gameapp.core.logger.CatLogger;
import com.ula.gameapp.core.receiver.ShutdownReceiver;
import com.ula.gameapp.utils.CalendarUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.ula.gameapp.App.getContext;
import static com.ula.gameapp.core.Annotation.PEDOMETER_GOOGLE_FIT;
import static com.ula.gameapp.core.helper.PedometerManager.getTypeEnable;

//Bavan Divaani-azar

public class ActivityTracker extends Service implements SensorEventListener {




    private static final int N_SAMPLES = 200;
    private static List<Float> x;
    private static List<Float> y;
    private static List<Float> z;
    private float[] results;
    int idx = -1;


    public final static int NOTIFICATION_ID = 1;
    private final BroadcastReceiver shutdownReceiver = new ShutdownReceiver();
    int stepCount;


    private TensorFlowClassifier classifier;


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


//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            public void run() {
//
//                int dailyGoal = getContext().getSharedPreferences("UlaSettings",
//                        Context.MODE_PRIVATE).getInt("daily_goal", 0);
//                int step = getStep();
//
//                String datapath = "/my_path";
//                String Message = "step " + step + " dailyGoal " + dailyGoal + " ";
//                new SendMessage(datapath, Message, getApplicationContext()).start();
//
//                handler.postDelayed(this, 60000); //now is every 5 minutes
//            }
//        }, 60000); //Every 600 ms (5 minutes)


        IntentFilter messageFilter = new IntentFilter(Intent.ACTION_SEND);
        Receiver messageReceiver = new Receiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, messageFilter);

        return START_STICKY;
    }

    public class Receiver extends BroadcastReceiver {
        @Override

        public void onReceive(Context context, Intent intent) {

            //Upon receiving each message from the wearable, display the following text//
            String message = intent.getStringExtra("message");
            Log.v("step_message", message);
            assert message != null;
            if (message.equals("280")) {
                int dailyGoal = getContext().getSharedPreferences("UlaSettings",
                        Context.MODE_PRIVATE).getInt("daily_goal", 0);
                int step = getStep();
                String datapath = "/my_path";
                String Message = "step " + step + " dailyGoal " + dailyGoal + " ";
                new SendMessage(datapath, Message, getApplicationContext()).start();
            } else {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("ulaData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("stepsData-1", message);
                editor.apply();
            }

        }
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

//        Log.v("sensor",sensor.getType()+"");

        activityPrediction();
        if (event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
            stepDetect();
            Log.v("sensor","step");

        }
        if (event.sensor.getType() == 1) {
            x.add(event.values[0]);
            y.add(event.values[1]);
            z.add(event.values[2]);
        }

    }

    private int getStep() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("ulaData", Context.MODE_PRIVATE);

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        String mobileData = sharedPreferences.getString("stepsData-0", "{}");
        String watchData = sharedPreferences.getString("stepsData-1", "{}");

        try {
            JSONObject mobileObj = new JSONObject(mobileData);
            JSONObject watchObj = new JSONObject(watchData);
            String date = cal.getTime() + "";

            int step1 = 0, step2 = 0;

            if (mobileObj.has(date)) {
                JSONObject jsonObject = mobileObj.getJSONObject(date);
                step1 += jsonObject.getInt("total_steps");
                step2 = jsonObject.getInt("google_fitness");
            }

            if (watchObj.has(date)) {
                JSONObject jsonObject = watchObj.getJSONObject(date);
                step1 += jsonObject.getInt("total_steps");
            }

            if (getTypeEnable(getContext(), PEDOMETER_GOOGLE_FIT))
                return Math.max(step1, step2);
            else
                return step1;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 0;
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

        String data = sharedPreferences.getString("stepsData-0", "{}");
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
            editor.putString("stepsData-0", obj.toString());
            Log.v("stepsData-0", obj.toString());
            editor.apply();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    //mobile
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


    @Override
    public void onTaskRemoved(Intent rootIntent) {
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent broadcastIntent = new Intent(".RestartService");
        sendBroadcast(broadcastIntent);
    }

    public static class SendMessage extends Thread {
        String path;
        String message;
        Context context;

        public SendMessage(String p, String m, Context c) {
            this.path = p;
            this.message = m;
            this.context = c;
        }

        public void run() {

            Task<List<Node>> wearableList = Wearable.getNodeClient(context).getConnectedNodes();
            try {
                List<Node> nodes = Tasks.await(wearableList);
                for (Node node : nodes) {
                    Task<Integer> sendMessageTask = Wearable.getMessageClient(context).sendMessage(node.getId(), path, message.getBytes());
                    try {
                        Integer result = Tasks.await(sendMessageTask);
                        Log.v("Message", result + "");
                    } catch (ExecutionException | InterruptedException exception) {
                        Log.v("error", exception.getMessage());


                    }

                }

            } catch (ExecutionException | InterruptedException exception) {
                Log.v("error", exception.getMessage());
            }

        }
    }
}