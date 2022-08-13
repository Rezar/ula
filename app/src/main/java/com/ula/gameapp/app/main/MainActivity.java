/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 4/6/19 9:41 PM
 */

package com.ula.gameapp.app.main;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.amitshekhar.DebugDB;
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
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;
import com.ula.gameapp.R;
import com.ula.gameapp.ViewModels.PrimaryDataViewModel;
import com.ula.gameapp.ViewModels.SettingsViewModel;
import com.ula.gameapp.activitytracker.DebugFragment;
import com.ula.gameapp.app.BaseActivity;
import com.ula.gameapp.app.main.help.HelpFragment;
import com.ula.gameapp.app.main.home.HomeFragment;
import com.ula.gameapp.app.main.setting.SettingFragment;
import com.ula.gameapp.app.main.stats.StatsFragment;
import com.ula.gameapp.core.Annotation;
import com.ula.gameapp.core.helper.GoogleFit;
import com.ula.gameapp.core.helper.PedometerManager;
import com.ula.gameapp.core.receiver.ResetSensorReceiver;
import com.ula.gameapp.core.service.ActivityTracker;
import com.ula.gameapp.item.AppConfig;
import com.ula.gameapp.utils.JTimeUtil;
import com.ula.gameapp.utils.views.NoSwipeViewPager;

import org.json.JSONException;
import org.json.JSONObject;

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

public class MainActivity extends BaseActivity  {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.img_nav_toggle)
    ImageView imgNavToggle;
    @BindView(R.id.ntb)
    NavigationTabBar navigationTabBar;
    @BindView(R.id.vp)
    public NoSwipeViewPager viewPager;

    boolean animationDone = true;

    StepViewModel stepViewModel;

    private PrimaryDataViewModel primaryDataViewModel;
    private final static int SIGN_IN_RESULT_CODE = 46532;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.v("bavan",DebugDB.getAddressLog());
        setContentView(R.layout.activity_main);

        AppCenter.start(getApplication(), "ae6b638e-1f24-474e-b99b-f2f6d6e0b735",
                Analytics.class, Crashes.class);

        new ActivityTracker.SendMessage("/my_path", "280", getApplicationContext()).start();

        stepViewModel = new ViewModelProvider(this).get(StepViewModel.class);


        initViewModels();


        SharedPreferences pref = getSharedPreferences("UlaSettings", Context.MODE_PRIVATE);

        if (pref.contains("pedometer_type")) {
            int pedometerType = pref.getInt("pedometer_type", 0);

            switch (pedometerType) {
                case PEDOMETER_SENSOR:
                    break;
                case PEDOMETER_GOOGLE_FIT:
                    checkSignIn();
                    if (GoogleFit.checkGoogleFitPermission(this))
                        loadFromFit();
                    break;
            }
        } else {
            if (GoogleFit.checkGoogleFitPermission(this))
                loadFromFit();
        }

        if (!pref.contains("fist_time")) {

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
            editor.putInt("fist_time", 1);
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            cal.add(Calendar.DAY_OF_MONTH, -1);
            editor.putLong("last_update_date", cal.getTimeInMillis());
            editor.apply();

        }
        loadPrimaryData(getContext());

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

    private SensorManager getSensorManager() {
        return (SensorManager) getSystemService(SENSOR_SERVICE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        new ActivityTracker.SendMessage("/my_path", "280", getApplicationContext()).start();

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



    private void loadPrimaryData(Context c) {

        primaryDataViewModel.loadPrimaryData(c).observe(this, primaryDataResult -> {
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
            if(!GoogleSignIn.hasPermissions(account, fitnessOptions))
                PedometerManager.setPedometerType(getBaseContext(), Annotation.PEDOMETER_GOOGLE_FIT,0);
        } else {
            loadFromFit();
        }
    }


    private void loadFromFit() {

        Fitness.getRecordingClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .subscribe(DataType.TYPE_STEP_COUNT_CUMULATIVE)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid)
                    {
                        PedometerManager.setPedometerType(getBaseContext(), Annotation.PEDOMETER_GOOGLE_FIT,1);
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
        SharedPreferences pref = getSharedPreferences("UlaSettings", Context.MODE_PRIVATE);
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

        if (GoogleSignIn.getLastSignedInAccount(this) == null) return;

        Task<DataReadResponse> response = Fitness.getHistoryClient(this,
                GoogleSignIn.getLastSignedInAccount(this)).readData(readRequest);

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
