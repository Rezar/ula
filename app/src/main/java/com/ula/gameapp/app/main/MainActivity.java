/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 4/6/19 9:41 PM
 */

package com.ula.gameapp.app.main;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.Bucket;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.result.DataReadResponse;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.ula.gameapp.BuildConfig;
import com.ula.gameapp.R;
import com.ula.gameapp.ViewModels.FootStepViewModel;
import com.ula.gameapp.ViewModels.PrimaryDataViewModel;
import com.ula.gameapp.ViewModels.SettingsViewModel;
import com.ula.gameapp.app.BaseActivity;
import com.ula.gameapp.app.main.help.HelpFragment;
import com.ula.gameapp.app.main.home.HomeFragment;
import com.ula.gameapp.app.main.setting.SettingFragment;
import com.ula.gameapp.app.main.stats.StatsFragment;
import com.ula.gameapp.core.Annotation;
import com.ula.gameapp.core.helper.GoogleFit;
import com.ula.gameapp.core.helper.PedometerManager;
import com.ula.gameapp.core.logger.CatLogger;
import com.ula.gameapp.database.dao.StepDaoUtils;
import com.ula.gameapp.item.AppConfig;
import com.ula.gameapp.item.FootStep;
import com.ula.gameapp.item.Step;
import com.ula.gameapp.utils.CalendarUtil;
import com.ula.gameapp.utils.JTimeUtil;
import com.ula.gameapp.utils.views.NoSwipeViewPager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import devlight.io.library.ntb.NavigationTabBar;

import static com.ula.gameapp.core.helper.GoogleFit.REQUEST_OAUTH_REQUEST_CODE;

public class MainActivity extends BaseActivity implements SensorEventListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.img_nav_toggle) ImageView imgNavToggle;
    @BindView(R.id.ntb) NavigationTabBar navigationTabBar;
    @BindView(R.id.vp) public NoSwipeViewPager viewPager;

    boolean animationDone = true;

//    private StepDao stepDao;
//    StepViewModel stepViewModel;
    int todayOffset = 0, sinceBoot = 0; // pedometer handler

    private PrimaryDataViewModel primaryDataViewModel;
    private FootStepViewModel footStepViewModel;
    private final static int SIGN_IN_RESULT_CODE = 46532;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        stepDao = DatabaseClient.getInstance(this).getAppDatabase().stepDao();
//        stepViewModel = ViewModelProviders.of(this).get(StepViewModel.class);
        initViewModels();
        if (GoogleFit.checkGoogleFitPermission(this)) {
            loadFromFit();
        }
        loadPrimaryData();
        loadAppConfigs();

        launchIntroIfNeeded();
        initNTB();

        imgNavToggle.bringToFront();
        imgNavToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!animationDone) return;

                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.menu);
                imgNavToggle.startAnimation(animation);

                if (navigationTabBar.getVisibility() == View.VISIBLE) {
                    imgNavToggle.bringToFront();
                    animationDone = false;

                    navigationTabBar.animate()
                            .translationXBy(navigationTabBar.getWidth())
                            .setDuration(400)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    navigationTabBar.setVisibility(View.GONE);
                                    animationDone = true;
                                }
                            })
                            .start();
                } else {
                    navigationTabBar.setVisibility(View.VISIBLE);
                    imgNavToggle.bringToFront();
                    animationDone = false;

                    navigationTabBar.animate()
                            .translationXBy(-navigationTabBar.getWidth())
                            .setDuration(400)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    navigationTabBar.bringToFront();
                                    animationDone = true;
                                }
                            })
                            .start();
                }
            }
        });

        // start pedometer
//        PedometerManager.initializePedometer(this);
    }

    private void initNTB() {
        String[] colors = getResources().getStringArray(R.array.vertical_ntb);

        ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(new NavigationTabBar.Model.Builder(getResources().getDrawable(R.drawable.ic_ula), Color.parseColor(colors[0])).build());
        models.add(new NavigationTabBar.Model.Builder(getResources().getDrawable(R.drawable.ic_stats), Color.parseColor(colors[0])).build());
        models.add(new NavigationTabBar.Model.Builder(getResources().getDrawable(R.drawable.ic_help), Color.parseColor(colors[0])).build());
        models.add(new NavigationTabBar.Model.Builder(getResources().getDrawable(R.drawable.ic_cog), Color.parseColor(colors[0])).build());

        viewPager.setAdapter(new NTBAdapter(getSupportFragmentManager(), models.size()));
        viewPager.setOffscreenPageLimit(4); // cache 4 fragment
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {
                navigationTabBar.bringToFront();
            }
        });

        navigationTabBar.setIsTitled(false);
        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, 0);
        navigationTabBar.setBgColor(getResources().getColor(R.color.transparent));
    }

    private class NTBAdapter extends FragmentPagerAdapter {
        int count;

        NTBAdapter(FragmentManager fm, int count) {
            super(fm);
            this.count = count;
        }

        @Override
        public int getCount() {
            return count;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new HomeFragment();
                case 1:
                    return new StatsFragment();
                case 2:
                    return new HelpFragment();
                case 3:
                    return new SettingFragment();
                default:
                    throw new RuntimeException("No fragment is available.");
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_OAUTH_REQUEST_CODE) {
                Toast.makeText(getBaseContext(), "Please restart the app.", Toast.LENGTH_LONG).show();
            } else if (requestCode == SIGN_IN_RESULT_CODE) {
                loadFromFit();
            }
        }
    }

    // Pedometer

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (PedometerManager.getPedometerType(this) == Annotation.PEDOMETER_SENSOR) {

            if (BuildConfig.DEBUG) CatLogger.get().log("UI - sensorChanged | : since boot:" + event.values[0]);

            if (event.values[0] > Integer.MAX_VALUE || event.values[0] == 0) {
                return;
            }

            if (todayOffset == Integer.MIN_VALUE) {
                // no values for today
                // we don't know when the reboot was, so set today's steps to 0 by
                // initializing them with -STEPS_SINCE_BOOT
                todayOffset = -(int) event.values[0];

                StepDaoUtils.insertNewDay(this, CalendarUtil.getStartOfToday(), (int) event.values[0]);
            }

            sinceBoot = (int) event.values[0];
            int step = Math.max(todayOffset + sinceBoot, 0);
//            stepViewModel.getSteps().setValue(step);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onResume() {
        super.onResume();

        if (PedometerManager.getPedometerType(this) == Annotation.PEDOMETER_SENSOR) {
//            Step step = stepDao.getByDate(CalendarUtil.getStartOfToday());
//            todayOffset = (step == null) ? Integer.MIN_VALUE : step.getStep();
            sinceBoot = Step.getCurrentSteps(this);
            CatLogger.get().log("First: " + sinceBoot);

            // register a sensorlistener to live update the UI if a step is taken
            SensorManager sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            Sensor sensor = sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            if (sensor == null) {
//                Snackbar.make(lnrRoot, "There is no hardware pedometer sensor.", 1000).show();
            } else {
                sm.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI, 0);
            }

            CatLogger.get().log("Next: " + sinceBoot);
        }
    }

    private void loadPrimaryData() {

        primaryDataViewModel.loadPrimaryData().observe(this, primaryDataResult ->{
            switch (primaryDataResult.getLoadingState()) {
                case LOADING:

                    break;
                case DONE:

                    break;
            }
        });

    }

    private void initViewModels() {
        primaryDataViewModel = new ViewModelProvider(this).get(PrimaryDataViewModel.class);
        footStepViewModel = new ViewModelProvider(this).get(FootStepViewModel.class);
    }

    /*private void checkSignIn() {

        FitnessOptions fitnessOptions = FitnessOptions.builder()
                .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                .build();

        GoogleSignInAccount account = GoogleSignIn.getAccountForExtension(this,
                fitnessOptions);

        if (!GoogleSignIn.hasPermissions(account, fitnessOptions)) {
            GoogleSignIn.requestPermissions(
                    this, // your activity
                    SIGN_IN_RESULT_CODE, // e.g. 1
                    account,
                    fitnessOptions);
        } else {
            loadFromFit();
        }
    }*/


    private void loadFromSensor() {
        StepViewModel stepViewModel = new ViewModelProvider(this).get(StepViewModel.class);
        stepViewModel.getSteps().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer step) {
                //todayStep = new Step(CalendarUtil.getStartOfToday(), step);
                Log.d("TodaySteps", String.valueOf(step));
            }
        });
        stepViewModel.getStepsList().observe(this, steps -> {
//                if (todayStep != null)
//                    steps.add(todayStep);

//                adapter.removeAll(); // clear adapter
//                adapter.add(steps); // add live data steps
//                adapter.notifyDataSetChanged();

            // cache the loaded data
//                CacheManager.setStatisticsCache(getContext(), steps);
        });
    }

    private void loadFromFit() {

        Fitness.getRecordingClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .subscribe(DataType.TYPE_STEP_COUNT_CUMULATIVE)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        fetchData();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("Subscription", "Failed");
                    }
                });

        fetchData();
    }

    private void fetchData() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS", Locale.getDefault());

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        long endTime = cal.getTimeInMillis();
//        Log.d("FIT_ACTIVITY_START_DATE", dateFormat.format(endTime));
//        int daysCount = getResources().getInteger(R.integer.activity_impact_life_time);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.WEEK_OF_YEAR, -1);
        long startTime = cal.getTimeInMillis();
//        Log.d("FIT_ACTIVITY_END_DATE", dateFormat.format(startTime));

        DataReadRequest readRequest = new DataReadRequest.Builder()
                .aggregate(DataType.TYPE_STEP_COUNT_DELTA, DataType.AGGREGATE_STEP_COUNT_DELTA)
                .bucketByTime(1, TimeUnit.DAYS)
                .enableServerQueries()
                .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                .build();

        if (GoogleSignIn.getLastSignedInAccount(this) == null) return;

        Task<DataReadResponse> response = Fitness.getHistoryClient(this,
                GoogleSignIn.getLastSignedInAccount(this)).readData(readRequest);

        response.addOnSuccessListener(dataReadResponse -> {

            List<Bucket> dataSets = dataReadResponse.getBuckets();
            ArrayList<FootStep> stepsList = new ArrayList<>();
            FootStep footStep;

            for (Bucket bucket : dataSets) {
                for (DataSet dataSet : bucket.getDataSets()) {
                    for (DataPoint dp : dataSet.getDataPoints()) {
                        for (Field field : dp.getDataType().getFields()) {

                            Log.d("STEP_INFO", dateFormat.format(
                                    new Date(dp.getTimestamp(TimeUnit.MILLISECONDS))));
                            Log.d("STEP_INFO", String.valueOf(dp.getValue(field).asInt()));
                            footStep = new FootStep();
                            cal.setTime(new Date(dp.getTimestamp(TimeUnit.MILLISECONDS)));
                            cal.set(Calendar.HOUR_OF_DAY, 0);
                            cal.set(Calendar.MINUTE, 0);
                            cal.set(Calendar.SECOND, 0);
                            cal.set(Calendar.MILLISECOND, 0);
                            footStep.setDate(cal.getTime());
                            footStep.setStepCount(dp.getValue(field).asInt());
                            stepsList.add(footStep);
                        }
                    }
                }
            }

            if (stepsList.size() > 0) {
                footStepViewModel.insertStepsHistory(stepsList);
            }
        });

        response.addOnFailureListener(e -> {
            Log.e("FAIL", e.getMessage());
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        });
    }

    private void loadAppConfigs() {

        SettingsViewModel settingsViewModel = new ViewModelProvider(this)
                .get(SettingsViewModel.class);
        settingsViewModel.getAppConfig().observe(this, appConfig -> {

            if (appConfig == null) {
                AppConfig mConfig = new AppConfig();
                mConfig.setId(1);
                mConfig.setDisplayStepCounts(true);
                mConfig.setDisplayTapToGo(true);
                mConfig.setMaxThreshold(0.2f);
                mConfig.setMinThreshold(0.2f);
                mConfig.setEffectiveDays(3);
                mConfig.setLockTime(60000);
                mConfig.setLastLockPoint(JTimeUtil.getInstance().getMinDate());
                settingsViewModel.setAppConfig(mConfig);
            } else {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS",
                        Locale.getDefault());
                Log.d("LastLockPoint", dateFormat.format(appConfig.getLastLockPoint()));
            }
        });
    }
}
