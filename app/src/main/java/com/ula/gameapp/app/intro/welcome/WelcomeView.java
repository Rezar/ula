/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 3/28/19 11:46 PM
 */

package com.ula.gameapp.app.intro.welcome;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.ula.gameapp.R;
import com.ula.gameapp.app.intro.BaseContract;
import com.ula.gameapp.app.intro.IntroHelper;

import butterknife.BindView;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class WelcomeView implements WelcomeContract.View {

    @BindView(R.id.img_chk) ImageView imgCheck;
    @BindView(R.id.lnr_rules) LinearLayout lnrRules;
    @BindView(R.id.lnr_countdown) LinearLayout lnrCountdown;
    @BindView(R.id.countdown_view) CountdownView countdownView;

    private BaseContract.Presenter mPresenter;
    private boolean checked = false;

    @Override
    public int getView() {
        return R.layout.fragment_intro_welcome;
    }

    @Override
    public void setPresenter(@NonNull WelcomeContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void toggleCountdown(boolean visible) {
        lnrCountdown.setVisibility((visible) ? View.VISIBLE : View.GONE);
        lnrRules.setVisibility((visible) ? View.GONE : View.VISIBLE);
    }

    @Override
    public void startCountdown(long time) {
        countdownView.start(time);
        countdownView.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {
                lnrRules.setVisibility(View.VISIBLE);
                lnrCountdown.setVisibility(View.GONE);
            }
        });
    }

    @OnClick({R.id.img_chk, R.id.txt_terms})
    public void termClick() {
        if (checked) {
            imgCheck.setImageResource(R.mipmap.square);
            IntroHelper.instance.fadeNextButton(0.6f);
            IntroHelper.canContinue = false;
        } else {
            imgCheck.setImageResource(R.mipmap.square_tick);
            IntroHelper.instance.fadeNextButton(1f);
            IntroHelper.canContinue = true;
        }

        checked = !checked;
    }

    @Override
    public void checkTerms(boolean state) {
        if (state)
            imgCheck.setImageResource(R.mipmap.square_tick);
        else
            imgCheck.setImageResource(R.mipmap.square);
    }
}
