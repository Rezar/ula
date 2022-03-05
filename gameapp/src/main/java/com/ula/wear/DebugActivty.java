package com.ula.wear;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.wearable.activity.WearableActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ula.wear.activitytracker.FootStep;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class DebugActivty extends WearableActivity {

    private DebugAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);

        RecyclerView recyclerView = this.findViewById(R.id.rv_debug);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new DebugAdapter();

        final SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("ulaData", Context.MODE_PRIVATE);

        recyclerView.setAdapter(adapter);

        final Handler handler = new Handler(); // new handler

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                String Data = sharedPreferences.getString("stepsData", "{}");

                List<FootStep> stepList = new ArrayList<FootStep>();
                try {

                    JSONObject Obj = new JSONObject(Data);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(new Date());
                    cal.set(Calendar.HOUR_OF_DAY, 0);
                    cal.set(Calendar.MINUTE, 0);
                    cal.set(Calendar.SECOND, 0);
                    cal.set(Calendar.MILLISECOND, 0);
                    for (int i = 0; i < 7; i++) {

                        String date = cal.getTime().toString();
                        FootStep footStepW=new FootStep();;

                        if (Obj.has(date)) {
                            JSONObject jsonObject = Obj.getJSONObject(date);
                            footStepW.setType(1);
                            footStepW.setTotalSteps(jsonObject.getInt("total_steps"));
                            footStepW.setDownstairs(jsonObject.getInt("downstairs"));
                            footStepW.setUpstairs(jsonObject.getInt("upstairs"));
                            footStepW.setSitting(jsonObject.getInt("sitting"));
                            footStepW.setStanding(jsonObject.getInt("standing"));
                            footStepW.setJogging(jsonObject.getInt("jogging"));
                            footStepW.setWalking(jsonObject.getInt("walking"));
                            footStepW.setDate(cal.getTime());
                            stepList.add(footStepW);
                        }
                        cal.add(Calendar.DAY_OF_MONTH, -1);

                    }

                    if (stepList.size() == 0) {
                        FootStep footStepW=new FootStep();;
                        footStepW.setDate(cal.getTime());
                        footStepW.setType(1);
                        footStepW.setStepCount(0);
                        stepList.add(footStepW);
                    }
                    updateAdapter(stepList);

                } catch (
                        JSONException e) {
                    e.printStackTrace();
                }

                handler.postDelayed(this, 200);

            }
        }, 200);


    }

    private void updateAdapter(List<FootStep> steps) {
        adapter.removeAll();
        adapter.add(steps);
        adapter.notifyDataSetChanged();
    }

}