package com.ula.wear;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ula.wear.activitytracker.ActivityTracker;
import com.ula.wear.service.StartupService;

public class MainActivity extends WearableActivity {

    private TextView textView;
    private ImageView imageView;
    private ProgressBar progressBar;


    ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bar = findViewById(R.id.progBar);

        String datapath = "/my_path";
        new ActivityTracker.SendMessage(datapath, "280", getApplicationContext()).start();
        Log.v("sent", "sent");


        textView = findViewById(R.id.no_data_view);
        imageView = findViewById(R.id.image_view);
        progressBar = findViewById(R.id.progress_bar);
        Button debugButton = findViewById(R.id.debug_btn);


        StartupService startupService = new StartupService();
        IntentFilter startupServiceIntentFilter = new IntentFilter();
        startupServiceIntentFilter.addAction("StartupService");
        this.registerReceiver(startupService, startupServiceIntentFilter);

        Intent serviceMonitorIntent = new Intent();
        serviceMonitorIntent.setAction("StartupService");
        sendBroadcast(serviceMonitorIntent);

        bar.setVisibility(View.GONE);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("ulaData", Context.MODE_PRIVATE);

        int steps = sharedPreferences.getInt("stepsDetector", 0);
        TextView stepText = findViewById(R.id.stepsTextview);
        stepText.setText(steps + "");

        if (sharedPreferences.contains("dailyGoal")) {
            int step = sharedPreferences.getInt("step", 0);
            int dailyGoal = sharedPreferences.getInt("dailyGoal", 0);
            int watch_steps = sharedPreferences.getInt("watch_steps", 0);
            if (watch_steps > step)
                step = watch_steps;
            int progress = 100 * step / dailyGoal;

            textView.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);

            if (progress <= 17) {

                imageView.setImageResource(R.mipmap.ic_stickman_0);

            } else if (progress <= 34) {

                imageView.setImageResource(R.mipmap.ic_stickman_1);

            } else if (progress <= 50) {

                imageView.setImageResource(R.mipmap.ic_stickman_2);

            } else if (progress <= 68) {

                imageView.setImageResource(R.mipmap.ic_stickman_3);

            } else if (progress <= 85) {

                imageView.setImageResource(R.mipmap.ic_stickman_4);

            } else {

                imageView.setImageResource(R.mipmap.ic_stickman_5);

            }
            progressBar.setProgress(progress);
        } else {
            textView.setVisibility(View.VISIBLE);
        }

        debugButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DebugActivty.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        String datapath = "/my_path";
        new ActivityTracker.SendMessage(datapath, "280", getApplicationContext()).start();
        Log.v("sent", "sent");
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}