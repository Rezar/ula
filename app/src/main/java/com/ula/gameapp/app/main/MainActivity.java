/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 4/6/19 9:41 PM
 */

package com.ula.gameapp.app.main;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
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
import com.ula.gameapp.R;
import com.ula.gameapp.ViewModels.FootStepViewModel;
import com.ula.gameapp.ViewModels.PrimaryDataViewModel;
import com.ula.gameapp.ViewModels.SettingsViewModel;
import com.ula.gameapp.app.BaseActivity;
import com.ula.gameapp.app.main.help.HelpFragment;
import com.ula.gameapp.app.main.home.HomeFragment;
import com.ula.gameapp.app.main.setting.SettingFragment;
import com.ula.gameapp.app.main.stats.StatsFragment;
import com.ula.gameapp.core.helper.CacheManager;
import com.ula.gameapp.core.helper.GoogleFit;
import com.ula.gameapp.core.helper.PedometerManager;
import com.ula.gameapp.core.receiver.ResetSensorReceiver;
import com.ula.gameapp.database.DatabaseClient;
import com.ula.gameapp.database.dao.StepDao;
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

import static com.ula.gameapp.App.getContext;
import static com.ula.gameapp.core.Annotation.PEDOMETER_GOOGLE_FIT;
import static com.ula.gameapp.core.Annotation.PEDOMETER_SENSOR;
import static com.ula.gameapp.core.helper.GoogleFit.REQUEST_OAUTH_REQUEST_CODE;

import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;

public class MainActivity extends BaseActivity implements SensorEventListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.img_nav_toggle)
    ImageView imgNavToggle;
    @BindView(R.id.ntb)
    NavigationTabBar navigationTabBar;
    @BindView(R.id.vp)
    public NoSwipeViewPager viewPager;

    boolean animationDone = true;

    private StepDao stepDao;
    StepViewModel stepViewModel;
    private Step todayStep;
    int todayOffset = 0, sinceBoot = 0; // pedometer handler
    private  int idx=-1;

    private static final int N_SAMPLES = 200;
    private static List<Float> x;
    private static List<Float> y;
    private static List<Float> z;
    private String[] labels = {"Downstairs", "Jogging", "Sitting", "Standing", "Upstairs", "Walking"};
    private float[] results;
    private TensorFlowClassifier classifier;

    private PrimaryDataViewModel primaryDataViewModel;
    private FootStepViewModel footStepViewModel;
    private final static int SIGN_IN_RESULT_CODE = 46532;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCenter.start(getApplication(), "ae6b638e-1f24-474e-b99b-f2f6d6e0b735",
                Analytics.class, Crashes.class);

        stepDao = DatabaseClient.getInstance(this).getAppDatabase().stepDao();
        stepViewModel = new ViewModelProvider(this).get(StepViewModel.class);


        initViewModels();
        x = new ArrayList<>();
        y = new ArrayList<>();
        z = new ArrayList<>();
        classifier = new TensorFlowClassifier(getApplicationContext());


        SharedPreferences pref = getSharedPreferences("UlaSettings", Context.MODE_PRIVATE);

        if(pref.contains("pedometer_type")) {
            int pedometerType = pref.getInt("pedometer_type", 0);

            switch (pedometerType) {
                case PEDOMETER_SENSOR:
                    loadFromSensor();
                    break;

                case PEDOMETER_GOOGLE_FIT:
                    if (GoogleFit.checkGoogleFitPermission(this)) {
                        loadFromFit();
                    } else {
                        loadFromSensor();
                    }
                    break;
            }
        }
        else
        { if (GoogleFit.checkGoogleFitPermission(this)) {
            loadFromFit();
        } else {
            loadFromSensor();
        }
        }
//        checkSignIn();

        if(!pref.contains("fistTime")) {

            Intent intent1 = new Intent(this, ResetSensorReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent1, 0);
            AlarmManager alarmManager = (AlarmManager) this.getSystemService(ALARM_SERVICE);


            Calendar calendar = Calendar.getInstance();
            Calendar now = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 0);

            if (now.after(calendar)) {
                calendar.add(Calendar.DATE, 1);
            }
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);

            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("fistTime", 1);
            editor.apply();

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
        PedometerManager.initializePedometer(this);


    }

    private void initNTB() {
        String[] colors = getResources().getStringArray(R.array.vertical_ntb);

        ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(new NavigationTabBar.Model.Builder(getResources().getDrawable(R.drawable.ic_ula), Color.parseColor(colors[0])).build());
        models.add(new NavigationTabBar.Model.Builder(getResources().getDrawable(R.drawable.ic_stats), Color.parseColor(colors[0])).build());
        models.add(new NavigationTabBar.Model.Builder(getResources().getDrawable(R.drawable.ic_help), Color.parseColor(colors[0])).build());
        models.add(new NavigationTabBar.Model.Builder(getResources().getDrawable(R.drawable.ic_cog), Color.parseColor(colors[0])).build());
        models.add(new NavigationTabBar.Model.Builder(getResources().getDrawable(R.drawable.ic_bug), Color.parseColor(colors[0])).build());

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
                case 4:
                    return new DebugFragment();
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

    private float[] toFloatArray(List<Float> list) {
        int i = 0;
        float[] array = new float[list.size()];

        for (Float f : list) {
            array[i++] = (f != null ? f : Float.NaN);
        }
        return array;
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
    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        Log.v("sensor_model", sensor.getType() + "");

        activityPrediction();
        if (sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {

            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("ulaData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            int step = sharedPreferences.getInt("steps", 0);
            step++;
            editor.putInt("steps", step);
            editor.apply();
            ArrayList<FootStep> stepsList = new ArrayList<>();
            FootStep footStep = new FootStep();
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            footStep.setType(5);
            footStep.setDate(cal.getTime());
            footStep.setStepCount(step);
            stepsList.add(footStep);
            if (stepsList.size() > 0) {
                footStepViewModel.insertStepsHistory(stepsList);
            }
            if(idx!=-1) {
                step = sharedPreferences.getInt(idx + "", 0);
                step++;
                editor.putInt(idx + "", step);
                editor.apply();
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

    @Override
    public void onResume() {
        super.onResume();
        getSensorManager().registerListener(this, getSensorManager().getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);

//
//        if (PedometerManager.getPedometerType(this) == Annotation.PEDOMETER_SENSOR) {
////            Step step = stepDao.getByDate(CalendarUtil.getStartOfToday());
////            todayOffset = (step == null) ? Integer.MIN_VALUE : step.getStep();
//            sinceBoot = Step.getCurrentSteps(this);
//            CatLogger.get().log("First: " + sinceBoot);
//
//            SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
//            if(sensorManager == null){
//                Toast.makeText(this, "Sensor not found!", Toast.LENGTH_SHORT).show();
//            }
//            else{
//                Sensor accel = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
//                if(accel!=null){
//                    sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_UI);
//                }
//                else{
//                    Toast.makeText(this, "Sorry Sensor is not available!", Toast.LENGTH_LONG).show();
//                }
//            }
            /*
            // register a sensorlistener to live update the UI if a step is taken
            SensorManager sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            Sensor sensor = sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            if (sensor == null) {
//                Snackbar.make(lnrRoot, "There is no hardware pedometer sensor.", 1000).show();
            } else {
                sm.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI, 0);
            }*/

//            CatLogger.get().log("Next: " + sinceBoot);
//        }
    }
    private SensorManager getSensorManager() {
        return (SensorManager) getSystemService(SENSOR_SERVICE);
    }
    private void loadPrimaryData() {

        primaryDataViewModel.loadPrimaryData().observe(this, primaryDataResult -> {
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

    private void checkSignIn() {

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
    }


    private void loadFromSensor() {


        todayStep = new Step();
        StepViewModel stepViewModel2 = new ViewModelProvider(this).get(StepViewModel.class);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("ulaData",Context.MODE_PRIVATE);
        stepViewModel2.getSteps().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer step) {
                todayStep = new Step(CalendarUtil.getStartOfToday(), step,5);
            }
        });
        todayStep.setStep(todayStep.getStep()+sharedPreferences.getInt("5",0));
        stepViewModel2.getStepsList().observe(this, steps -> {
            if (todayStep != null)
                steps.add(todayStep);

//                adapter.removeAll(); // clear adapter
//                adapter.add(steps); // add live data steps
//                adapter.notifyDataSetChanged();

            // cache the loaded data
            CacheManager.setStatisticsCache(getContext(), steps);
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
