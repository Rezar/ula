/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 3/28/19 11:10 AM
 */

package com.ula.gameapp.app.intro;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.ula.gameapp.R;
import com.ula.gameapp.app.intro.fit.FitPresenter;
import com.ula.gameapp.app.intro.fit.FitView;
import com.ula.gameapp.app.intro.goal.GoalPresenter;
import com.ula.gameapp.app.intro.goal.GoalView;
import com.ula.gameapp.app.intro.tutorial.TutorialPresenter;
import com.ula.gameapp.app.intro.tutorial.TutorialView;
import com.ula.gameapp.app.intro.welcome.WelcomePresenter;
import com.ula.gameapp.app.intro.welcome.WelcomeView;
import com.ula.gameapp.app.main.MainActivity;
import com.ula.gameapp.core.Annotation;
import com.ula.gameapp.core.helper.GoogleFit;
import com.ula.gameapp.core.helper.PedometerManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;

public class IntroHelper {
    public static final long MILLISECOND_IN_3_DAY = 3 * 24 * 60 * 60 * 1000;
    public static IntroHelper instance;

    private static final int WELCOME_FRAGMENT = 0;
    private static final int TUTORIAL_FRAGMENT = 1;
    private static final int GOAL_FRAGMENT = 2;
    private static final int FIT_FRAGMENT = 3;

    @BindView(R.id.img_next) ImageView imgNext;
    @BindView(R.id.img_back) ImageView imgBack;

    private Activity activity;
    private FragmentManager fm;

    private int currentFragment = -1;
    private List<Fragment> fragments = new ArrayList<>();

    public static boolean canContinue = false;
    public static int dailyGoal = -1;

    public IntroHelper(Activity activity, FragmentManager fm) {
        this.activity = activity;
        this.fm = fm;

        fragments.add(IntroFragment.newInstance(WelcomePresenter.class, WelcomeView.class));
        fragments.add(IntroFragment.newInstance(TutorialPresenter.class, TutorialView.class));
        fragments.add(IntroFragment.newInstance(GoalPresenter.class, GoalView.class));

        PedometerManager.setPedometerType(activity, Annotation.PEDOMETER_SENSOR,1);

//        SharedPreferences pref = activity.getSharedPreferences("UlaSettings", MODE_PRIVATE);
//        boolean intro = pref.getBoolean("intro", false);

        // if user has Google Fit app, we need to ask for permission
//        if (GoogleFit.isFitInstalled(activity)&& !intro)
//            fragments.add(IntroFragment.newInstance(FitPresenter.class, FitView.class));

        ButterKnife.bind(this, activity);
        loadFragment(true);

        instance = this;
    }

    @OnClick({R.id.img_next})
    public void nextFragment(View v) {
        if (!GoogleFit.checkGoogleFitPermission(activity) && fragments.size() == 4 && currentFragment == 3) {
            IntroActivity.permissionRequestCount = 1;
            GoogleFit.initializeGoogleFit(activity);
        } else {
            loadFragment(true);
        }
    }

    @OnClick({R.id.img_back})
    public void prevFragment(View v) {
        loadFragment(false);
    }

    public void fadeNextButton(float val) {
        imgNext.setAlpha(val);
    }

    private void loadFragment(boolean next) {
        if (next && ((fragments.size() == 4 && currentFragment == 3) || (fragments.size() == 3 && currentFragment == 2))) {
            done();
            return;
        }

        if (currentFragment == 0 && !canContinue)
            return;

        // we are in set daily goal fragment but user doesn't selected any goal
        if (next && currentFragment == 2 && dailyGoal <= 0)
            return;

        currentFragment = (next) ? currentFragment + 1 : currentFragment - 1;

        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction ft = fm.beginTransaction();

        if (next)
            ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        else
            ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);

        ft.replace(R.id.fragments, fragments.get(currentFragment)); // replace the FrameLayout with new Fragment
        ft.commit(); // save the changes

        switch (currentFragment) {
            case WELCOME_FRAGMENT:
                imgBack.setVisibility(View.INVISIBLE);
                imgNext.setVisibility(View.VISIBLE);
                imgNext.setAlpha(0.6f);
                break;

            case GOAL_FRAGMENT:
                imgBack.setVisibility(View.VISIBLE);
                imgNext.setVisibility(View.VISIBLE);

                imgBack.setAlpha(1f);
                imgNext.setAlpha(0.6f);
                break;

            case TUTORIAL_FRAGMENT:

            case FIT_FRAGMENT:
                imgBack.setVisibility(View.VISIBLE);
                imgNext.setVisibility(View.VISIBLE);

                imgBack.setAlpha(1f);
                imgNext.setAlpha(1f);
                break;
        }
    }

    public void done() {
        if (dailyGoal > 0) {
            DialogPlus.newDialog(activity)
                    .setContentHolder(new ViewHolder(R.layout.dialog_goal_confirm))
                    .setOnClickListener(new OnClickListener() {
                        public void onClick(DialogPlus dialogRate, final View view) {
                            if (view.getId() == R.id.txt_yes) {
                                SharedPreferences pref = activity.getSharedPreferences("UlaSettings", MODE_PRIVATE);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putBoolean("intro", true);
                                editor.putInt("daily_goal", dailyGoal);
                                editor.putLong("can_change_timestamp", System.currentTimeMillis() + MILLISECOND_IN_3_DAY);
                                editor.apply();

                                Intent intent = new Intent(activity, MainActivity.class);
                                activity.startActivity(intent);

                                activity.finishAffinity();
                            } else {
                                dialogRate.dismiss();
                            }
                        }
                    }).setGravity(Gravity.CENTER)
                    .setCancelable(true)
                    .setContentBackgroundResource(R.color.transparent)
                    .create()
                    .show();
        }
    }
}
