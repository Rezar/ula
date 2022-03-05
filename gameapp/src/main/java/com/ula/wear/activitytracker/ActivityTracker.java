package com.ula.wear.activitytracker;

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
import android.os.Binder;
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
import com.ula.wear.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class ActivityTracker extends Service implements SensorEventListener {


    private static final int N_SAMPLES = 200;
    private static List<Float> x;
    private static List<Float> y;
    private static List<Float> z;
    private float[] results;
    int idx = -1;

    private static final String FOREGROUND_NOTIFICATION_CHANNEL_ID = "SensorServiceForegroundServiceAssist";
    private static final String FOREGROUND_NOTIFICATION_NAME = "SensorServiceForegroundService";

    private TensorFlowClassifier classifier;
    private String[] labels = {"downstairs", "jogging", "sitting", "standing", "upstairs", "walking"};


    @Override
    public void onCreate() {
        super.onCreate();
        startForeground(3, getNotification());
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {

        Sensor accel2 = getSensorManager().getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accel2 != null) {
            getSensorManager().registerListener(this, getSensorManager().getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
        }

        Sensor accel = getSensorManager().getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        if (accel != null) {
            getSensorManager().registerListener(this, accel, SensorManager.SENSOR_DELAY_UI);
        }
        x = new ArrayList<>();
        y = new ArrayList<>();
        z = new ArrayList<>();

        classifier = new TensorFlowClassifier(getApplicationContext());
        startForeground(3, getNotification());


        IntentFilter newFilter = new IntentFilter(Intent.ACTION_SEND);
        Receiver messageReceiver = new Receiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, newFilter);

        return START_STICKY;
    }

    public static class SendMessage extends Thread {
        String path;
        String message;
        Context context;

        public SendMessage(String p, String m, Context c) {
            path = p;
            message = m;
            context = c;
        }

        public void run() {


            Task<List<Node>> nodeListTask = Wearable.getNodeClient(context).getConnectedNodes();

            try {

                List<Node> nodes = Tasks.await(nodeListTask);
                for (Node node : nodes) {


                    Task<Integer> sendMessageTask = Wearable.getMessageClient(context).sendMessage(node.getId(), path, message.getBytes());

                    try {

                        Integer result = Tasks.await(sendMessageTask);


                    } catch (ExecutionException | InterruptedException exception) {
                        Log.v("error", exception.getMessage());
                    }

                }

            } catch (ExecutionException | InterruptedException exception) {
                Log.v("error", exception.getMessage());

            }
        }
    }

    public class Receiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            String onMessageReceived = intent.getStringExtra("message");
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("ulaData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            if (onMessageReceived.equals("280")) {
                String data = sharedPreferences.getString("stepsData", "{}");
                assert data != null;
                if (!data.equals("{}")) {
                    String datapath = "/my_path";
                    new SendMessage(datapath, data, getApplicationContext()).start();
                }
            } else {
                String[] parts = onMessageReceived.split(" ");
                if (parts[0].equals("step") && parts[2].equals("dailyGoal")) {
                    int step = Integer.parseInt(parts[1]);
                    int dailyGoal = Integer.parseInt(parts[3]);
                    // int progress = 100 * step / dailyGoal;
                    editor.putInt("step", step);
                    editor.putInt("dailyGoal", dailyGoal);

                    editor.apply();
//                Toast.makeText(context, "mobile :"+step, Toast.LENGTH_SHORT).show();

                }
            }

        }
    }

    private Notification getNotification() {
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, FOREGROUND_NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Foreground Service Notification")
                .setContentText("Foreground Service Notification - Running sensor service");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(FOREGROUND_NOTIFICATION_CHANNEL_ID,
                    FOREGROUND_NOTIFICATION_NAME, NotificationManager.IMPORTANCE_HIGH);
            // Get an instance of the NotificationManager service
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        return notification.build();
    }


    private SensorManager getSensorManager() {
        return (SensorManager) getSystemService(SENSOR_SERVICE);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        activityPrediction();

        if (sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("ulaData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            int step = sharedPreferences.getInt("stepsDetector", 0);
            step++;
            editor.putInt("stepsDetector", step);
            editor.apply();
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
                obj = new JSONObject(data);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("total_steps", 0);
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
                    editor.putInt("watch_steps", step);
                }
                step = jsonObject.getInt(labels[idx]);
                step++;
                jsonObject.put(labels[idx], step);
            }
            obj.put(date, jsonObject);
            editor.putString("stepsData", obj.toString());
            Log.v("stepsData", obj.toString());
            editor.apply();

            Log.v("data", data);


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


    public class LocalBinder extends Binder {
        public ActivityTracker getService() {
            return ActivityTracker.this;
        }
    }


    private final LocalBinder binder = new LocalBinder();


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
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

}