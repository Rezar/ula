/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 1/10/19 3:42 PM
 */

package com.ula.gameapp.app.main.stats;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.ula.gameapp.App;
import com.ula.gameapp.R;
import com.ula.gameapp.ViewModels.FootStepViewModel;
import com.ula.gameapp.ViewModels.SettingsViewModel;
import com.ula.gameapp.item.FootStep;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatsFragment extends Fragment implements StatsContract.View {
    @BindView(R.id.lnr_root) RelativeLayout lnrRoot;
    @BindView(R.id.rv_stats) RecyclerView rvStats;

    public StatsContract.Presenter mPresenter = null;

//    Step todayStep;
    private StatsAdapter adapter;
//    StepViewModel stepViewModel;
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

        /*petViewModel = new ViewModelProvider(requireActivity()).get(PetViewModel.class);
        petViewModel.getPetStatus().observe(getViewLifecycleOwner(), petStatus -> {
            if (petStatus != null) {
                this.petStatus = petStatus;
            }
        });*/

        FootStepViewModel footStepViewModel = new ViewModelProvider(this)
                .get(FootStepViewModel.class);

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date endDate = cal.getTime();
        cal.add(Calendar.WEEK_OF_YEAR, -1);
        Date startDate = cal.getTime();
        footStepViewModel.getStepsHistory(startDate, endDate).observe(getViewLifecycleOwner(),
                stepList -> {

                    if (stepList != null) {
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS",
                                Locale.getDefault());
                        for (FootStep footStep:
                                stepList) {
                            Log.d("DAY_STEP", dateFormat.format(footStep.getDate()));
                        }
                        updateAdapter(stepList);
                    }
                });

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

//        List<Step> steps = mPresenter.loadFromCache();
//        updateAdapter(steps);
    }

    @Override
    public void initPedometer() {
        /*if (PedometerManager.getPedometerType(getContext()) == Annotation.PEDOMETER_GOOGLE_FIT && !GoogleFit.isFitInstalled(getContext())) { // force to use sensor
            loadFromSensor();
            PedometerManager.setPedometerType(getContext(), Annotation.PEDOMETER_SENSOR);
            Toast.makeText(getContext(), "Fit not installed, switch to hardware.", Toast.LENGTH_LONG).show();

        } else {
            if (PedometerManager.getPedometerType(getContext()) == Annotation.PEDOMETER_SENSOR)
                loadFromSensor();
            else if (PedometerManager.getPedometerType(getContext()) == Annotation.PEDOMETER_GOOGLE_FIT)
                if (GoogleFit.checkGoogleFitPermission(getActivity())) {
                    loadFromFit();
                } else {
                    GoogleFit.initializeGoogleFit(getActivity());
                }
            else // no other choice
                loadFromSensor();
        }*/

//        loadFromFit();
    }

    private void updateAdapter(List<FootStep> steps) {
        adapter.removeAll();
        adapter.add(steps);
        adapter.notifyDataSetChanged();
    }

    /*private void loadFromSensor() {
        stepViewModel = ViewModelProviders.of(getActivity()).get(StepViewModel.class);
        stepViewModel.getSteps().observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer step) {
                todayStep = new Step(CalendarUtil.getStartOfToday(), step);
            }
        });
        stepViewModel.getStepsList().observe(getActivity(), new Observer<List<Step>>() {
            @Override
            public void onChanged(@Nullable List<Step> steps) {
                if (todayStep != null)
                    steps.add(todayStep);

                adapter.removeAll(); // clear adapter
                adapter.add(steps); // add live data steps
                adapter.notifyDataSetChanged();

                // cache the loaded data
                CacheManager.setStatisticsCache(getContext(), steps);
            }
        });
    }*/

    /*private void loadFromFit() {
        if (getContext() == null) return;

        DataReadRequest readRequest = GoogleFit.queryFitnessWeekAgo();

//        Task<DataReadResponse> response = Fitness.getHistoryClient(getContext(),
//                GoogleSignIn.getLastSignedInAccount(getContext())).readData(readRequest);

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
            updateAdapter(steps);

            // cache the loaded data
            CacheManager.setStatisticsCache(getContext(), steps);

            // log the data
            //FirebaseLogger.get(getContext()).log(steps);
        });

        response.addOnFailureListener(e -> {
            Log.e("FAIL", e.getMessage());
        });
    }*/
}