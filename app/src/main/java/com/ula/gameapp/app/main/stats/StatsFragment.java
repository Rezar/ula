/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 1/10/19 3:42 PM
 */

package com.ula.gameapp.app.main.stats;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.Bucket;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.result.DataReadResponse;
import com.google.android.gms.tasks.Task;
import com.ula.gameapp.App;
import com.ula.gameapp.R;
import com.ula.gameapp.ViewModels.FootStepViewModel;
import com.ula.gameapp.ViewModels.SettingsViewModel;
import com.ula.gameapp.app.main.StepViewModel;
import com.ula.gameapp.core.helper.CacheManager;
import com.ula.gameapp.core.helper.GoogleFit;
import com.ula.gameapp.item.FootStep;
import com.ula.gameapp.item.Step;
import com.ula.gameapp.utils.CalendarUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ula.gameapp.App.getContext;

public class StatsFragment extends Fragment implements StatsContract.View {
    @BindView(R.id.lnr_root)
    RelativeLayout lnrRoot;
    @BindView(R.id.rv_stats)
    RecyclerView rvStats;

    public StatsContract.Presenter mPresenter = null;

    Step todayStep;
    private StatsAdapter adapter;
    StepViewModel stepViewModel;
//    private PetViewModel petViewModel;
//    private PetStatus petStatus;

    public StatsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_stats, container, false);
        ButterKnife.bind(this, v);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("ulaData", Context.MODE_PRIVATE);
        Handler handler = new Handler(); // new handler

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                fetchData();
                String data = sharedPreferences.getString("stepsData", "{}");
                try {
                    List<FootStep> stepList = new ArrayList<FootStep>();
                    JSONObject obj = new JSONObject(data);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(new Date());
                    cal.set(Calendar.HOUR_OF_DAY, 0);
                    cal.set(Calendar.MINUTE, 0);
                    cal.set(Calendar.SECOND, 0);
                    cal.set(Calendar.MILLISECOND, 0);

                    for (int i = 0; i < 7; i++) {
                        String date = cal.getTime().toString();
                        if (obj.has(date)) {
                            JSONObject jsonObject = obj.getJSONObject(date);
                            Log.v("data", jsonObject.toString());
                            FootStep footStep = new FootStep();
                            int step1 = jsonObject.getInt("total_steps");
                            int step2 = jsonObject.getInt("google_fitness");
                            footStep.setStepCount(Math.max(step1, step2));
                            footStep.setDate(cal.getTime());
                            stepList.add(footStep);
                            cal.add(Calendar.DAY_OF_MONTH, -1);
                        }
                    }
                    if (stepList.size() == 0) {
                        FootStep footStep = new FootStep();
                        footStep.setDate(cal.getTime());
                        footStep.setStepCount(0);
                        stepList.add(footStep);
                    }
                    updateAdapter(stepList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                handler.postDelayed(this, 1000);

            }
        }, 1000);

        SettingsViewModel settingsViewModel = new ViewModelProvider(this)
                .get(SettingsViewModel.class);
        settingsViewModel.getAppConfig().observe(getViewLifecycleOwner(), appConfig -> {
            if (appConfig != null && adapter != null) {
                adapter.setDisplayStepsCount(appConfig.isDisplayStepCounts());
            }
        });

        mPresenter = new StatsPresenter();
        mPresenter.setView(this);

    }


    @Override
    public void initRecycler() {
        int dailyGoal = App.getContext().getSharedPreferences("UlaSettings",
                Context.MODE_PRIVATE).getInt("daily_goal", 0);
        adapter = new StatsAdapter(dailyGoal);
        rvStats.setAdapter(adapter);
    }

    @Override
    public void initPedometer() {
//        if (PedometerManager.getPedometerType(getContext()) == Annotation.PEDOMETER_GOOGLE_FIT && !GoogleFit.isFitInstalled(getContext())) { // force to use sensor
//            loadFromSensor();
//            PedometerManager.setPedometerType(getContext(), Annotation.PEDOMETER_SENSOR);
//            Toast.makeText(getContext(), "Fit not installed, switch to hardware.", Toast.LENGTH_LONG).show();
//
//        } else {
//            if (PedometerManager.getPedometerType(getContext()) == Annotation.PEDOMETER_SENSOR)
//                loadFromSensor();
//            else if (PedometerManager.getPedometerType(getContext()) == Annotation.PEDOMETER_GOOGLE_FIT)
//                if (GoogleFit.checkGoogleFitPermission(getActivity())) {
//                    loadFromFit();
//                } else {
//                    GoogleFit.initializeGoogleFit(getActivity());
//                }
//            else // no other choice
//                loadFromSensor();
//        }
//
//        loadFromFit();

        loadFromSensor();

    }

    private void updateAdapter(List<FootStep> steps) {
        adapter.removeAll();
        adapter.add(steps);
        adapter.notifyDataSetChanged();
    }

    private void loadFromSensor() {
        stepViewModel = new ViewModelProvider(getActivity()).get(StepViewModel.class);
        stepViewModel.getSteps().observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer step) {
                todayStep = new Step(CalendarUtil.getStartOfToday(), step, 5);
            }
        });
        stepViewModel.getStepsList().observe(getActivity(), new Observer<List<Step>>() {
            @Override
            public void onChanged(@Nullable List<Step> steps) {
                if (todayStep != null)
                    steps.add(todayStep);

                adapter.removeAll(); // clear adapter

//                adapter.add(steps); // add live data steps
                adapter.notifyDataSetChanged();

                // cache the loaded data
                CacheManager.setStatisticsCache(getContext(), steps);
            }
        });
    }

    private void fetchData() {

        SharedPreferences pref = getContext().getSharedPreferences("UlaSettings", Context.MODE_PRIVATE);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        long startTime = pref.getLong("last_update_date", cal.getTimeInMillis());

        long endTime = cal.getTimeInMillis();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.DAY_OF_MONTH, -1);

        SharedPreferences.Editor editor = pref.edit();
        editor.putLong("last_update_date", cal.getTimeInMillis());
        editor.apply();

        DataReadRequest readRequest = new DataReadRequest.Builder()
                .aggregate(DataType.TYPE_STEP_COUNT_DELTA, DataType.AGGREGATE_STEP_COUNT_DELTA)
                .bucketByTime(1, TimeUnit.DAYS)
                .enableServerQueries()
                .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                .build();

        if (GoogleSignIn.getLastSignedInAccount(getContext()) == null) return;

        Task<DataReadResponse> response = Fitness.getHistoryClient(getContext(),
                GoogleSignIn.getLastSignedInAccount(getContext())).readData(readRequest);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("ulaData", Context.MODE_PRIVATE);
        String data = sharedPreferences.getString("stepsData", "{}");

        response.addOnSuccessListener(dataReadResponse -> {

            List<Bucket> dataSets = dataReadResponse.getBuckets();

            try {
                JSONObject obj = new JSONObject(data);
                for (Bucket bucket : dataSets) {
                    for (DataSet dataSet : bucket.getDataSets()) {
                        for (DataPoint dp : dataSet.getDataPoints()) {
                            for (Field field : dp.getDataType().getFields()) {


                                cal.setTime(new Date(dp.getTimestamp(TimeUnit.MILLISECONDS)));
                                cal.set(Calendar.HOUR_OF_DAY, 0);
                                cal.set(Calendar.MINUTE, 0);
                                cal.set(Calendar.SECOND, 0);
                                cal.set(Calendar.MILLISECOND, 0);
                                String date = cal.getTime().toString();
                                if (obj.has(date)) {
                                    JSONObject jsonObject = obj.getJSONObject(date);
                                    jsonObject.put("google_fitness", dp.getValue(field).asInt());
                                    obj.put(date, jsonObject);
                                }
                            }
                        }
                    }

                }
                SharedPreferences.Editor editor2 = sharedPreferences.edit();
                editor2.putString("stepsData", obj.toString());
                Log.v("stepsData", obj.toString());
                editor2.apply();

            } catch (JSONException e) {
                e.printStackTrace();
            }


        });

        response.addOnFailureListener(e ->

        {
            Log.e("FAIL", e.getMessage());
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        });
    }

    private void loadFromFit() {
        if (getContext() == null) return;

        DataReadRequest readRequest = GoogleFit.queryFitnessWeekAgo();

        Task<DataReadResponse> response = Fitness.getHistoryClient(getContext(),
                GoogleSignIn.getLastSignedInAccount(getContext())).readData(readRequest);

        response.addOnSuccessListener(dataReadResponse -> {

            List<Bucket> dataSets = dataReadResponse.getBuckets();
            List<Step> steps = new ArrayList<>();

            for (Bucket bucket : dataSets) {
                for (DataSet dataSet : bucket.getDataSets()) {
                    for (DataPoint dp : dataSet.getDataPoints()) {
                        Step step = new Step();
                        step.setDate(dp.getStartTime(TimeUnit.MILLISECONDS));
                        for (Field field : dp.getDataType().getFields()) {
                            step.setStep(dp.getValue(field).asInt());
                        }
                        steps.add(step);
                    }
                }
            }

            // hack fix - if we are in middle of the day then DataSet contains 8 days
            if (steps.size() > 7) steps.remove(0);
//            updateAdapter(steps);

            // cache the loaded data
            CacheManager.setStatisticsCache(getContext(), steps);

            // log the data
            //FirebaseLogger.get(getContext()).log(steps);
        });

        response.addOnFailureListener(e -> {
            Log.e("FAIL", e.getMessage());
        });
    }
}