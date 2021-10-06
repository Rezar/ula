package com.ula.wear;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends WearableActivity {

    private TextView textView;
    private ImageView imageView;
    private ProgressBar progressBar;

    private int progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=findViewById(R.id.text_view);
        imageView = findViewById(R.id.image_view);
        progressBar = findViewById(R.id.progress_bar);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("ulaData", Context.MODE_PRIVATE);

        if(sharedPreferences.contains("progress"))
        {
            progress = sharedPreferences.getInt("progress" , 0);
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
        }

        IntentFilter newFilter = new IntentFilter(Intent.ACTION_SEND);
        Receiver messageReceiver = new Receiver();

        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, newFilter);

        Intent i = new Intent(MainActivity.this, ActivityTracker.class);
        this.startService(i);
    }

    public class Receiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            String onMessageReceived = intent.getStringExtra("message");

            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("ulaData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();


            int progress = sharedPreferences.getInt("progress" , 0);


            String[] parts = onMessageReceived.split(" ");
            if (parts[0].equals("step") && parts[2].equals("dailyGoal")) {
                int step = Integer.parseInt(parts[1]);
                int dailyGoal = Integer.parseInt(parts[3]);
                progress = 100 * step / dailyGoal;
                editor.putInt("progress", progress);
                editor.apply();

                textView.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);

                Log.v("message",onMessageReceived);
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
            }

        }
    }

    static class SendMessage extends Thread {
        String path;
        String message;
        Context context;

        SendMessage(String p, String m, Context c) {
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
}