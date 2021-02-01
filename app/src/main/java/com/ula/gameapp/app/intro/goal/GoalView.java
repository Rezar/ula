/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 3/29/19 12:19 AM
 */

package com.ula.gameapp.app.intro.goal;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.ula.gameapp.R;
import com.ula.gameapp.app.intro.BaseContract;
import com.ula.gameapp.app.intro.IntroHelper;

import butterknife.BindView;
import butterknife.OnClick;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class GoalView implements GoalContract.View {

    @BindView(R.id.img_test_1) ImageView imgTest1;
    @BindView(R.id.img_test_2) ImageView imgTest2;
    @BindView(R.id.img_test_3) ImageView imgTest3;
    @BindView(R.id.img_test_4) ImageView imgTest4;
    @BindView(R.id.dialog_test_1) LinearLayout dialogTest1;
    @BindView(R.id.dialog_test_4) LinearLayout dialogTest4;

    private BaseContract.Presenter mPresenter;

    @Override
    public int getView() {
        return R.layout.fragment_intro_goal;
    }

    @Override
    public void setPresenter(@NonNull GoalContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void initialTest() {
        // trying to remember user choice
        switch (IntroHelper.dailyGoal) {
            case 5000:
                imgTest1.setImageResource(R.mipmap.circle_tick);
                break;
            case 10000:
                imgTest2.setImageResource(R.mipmap.circle_tick);
                break;
            case 15000:
                imgTest3.setImageResource(R.mipmap.circle_tick);
                break;
            case 20000:
                imgTest4.setImageResource(R.mipmap.circle_tick);
                break;
        }
    }

    @OnClick({R.id.img_test_1, R.id.img_test_2, R.id.img_test_3, R.id.img_test_4, R.id.txt_test_1, R.id.txt_test_2, R.id.txt_test_3, R.id.txt_test_4})
    public void imgClick(View v) {
        switch (v.getId()) {
            case R.id.txt_test_1:
            case R.id.img_test_1:
                IntroHelper.dailyGoal = 5000;
                imgTest1.setImageResource(R.mipmap.circle_tick);
                imgTest2.setImageResource(R.mipmap.circle);
                imgTest3.setImageResource(R.mipmap.circle);
                imgTest4.setImageResource(R.mipmap.circle);
                dialogTest1.setVisibility(View.VISIBLE);
                dialogTest4.setVisibility(View.INVISIBLE);

                new CountDownTimer(2000, 1000) {
                    public void onTick(long millisUntilFinished) {

                    }

                    public void onFinish() {
                        dialogTest1.setVisibility(View.INVISIBLE);
                    }
                }.start();
                break;

            case R.id.txt_test_2:
            case R.id.img_test_2:
                IntroHelper.dailyGoal = 10000;
                imgTest1.setImageResource(R.mipmap.circle);
                imgTest2.setImageResource(R.mipmap.circle_tick);
                imgTest3.setImageResource(R.mipmap.circle);
                imgTest4.setImageResource(R.mipmap.circle);
                dialogTest1.setVisibility(View.INVISIBLE);
                dialogTest4.setVisibility(View.INVISIBLE);
                break;

            case R.id.txt_test_3:
            case R.id.img_test_3:
                IntroHelper.dailyGoal = 15000;
                imgTest1.setImageResource(R.mipmap.circle);
                imgTest2.setImageResource(R.mipmap.circle);
                imgTest3.setImageResource(R.mipmap.circle_tick);
                imgTest4.setImageResource(R.mipmap.circle);
                dialogTest1.setVisibility(View.INVISIBLE);
                dialogTest4.setVisibility(View.INVISIBLE);
                break;

            case R.id.txt_test_4:
            case R.id.img_test_4:
                IntroHelper.dailyGoal = 20000;
                imgTest1.setImageResource(R.mipmap.circle);
                imgTest2.setImageResource(R.mipmap.circle);
                imgTest3.setImageResource(R.mipmap.circle);
                imgTest4.setImageResource(R.mipmap.circle_tick);
                dialogTest1.setVisibility(View.INVISIBLE);
                dialogTest4.setVisibility(View.VISIBLE);

                new CountDownTimer(2000, 1000) {
                    public void onTick(long millisUntilFinished) {

                    }

                    public void onFinish() {
                        dialogTest4.setVisibility(View.INVISIBLE);
                    }
                }.start();
                break;
        }

        IntroHelper.instance.fadeNextButton(1f);
    }
}
