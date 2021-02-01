/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 3/28/19 11:38 PM
 */

package com.ula.gameapp.app.intro.welcome;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.ula.gameapp.app.intro.IntroHelper;

import static android.content.Context.MODE_PRIVATE;
import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class WelcomePresenter implements WelcomeContract.Presenter {

    private WelcomeContract.View mView;

    @Override
    public void setView(@NonNull WelcomeContract.View view) {
        mView = checkNotNull(view);
    }

    @Override
    public void viewCreated(Activity activity) {
        long timestamp = activity.getSharedPreferences("UlaSettings", MODE_PRIVATE).getLong("can_change_timestamp", 0);

        if (timestamp > 0 && timestamp - System.currentTimeMillis() > 0) {
            mView.toggleCountdown(true);
            mView.startCountdown(timestamp - System.currentTimeMillis());
        } else {
            mView.toggleCountdown(false);
        }

        if (IntroHelper.canContinue)
            mView.checkTerms(true);
        else
            mView.checkTerms(false);
    }
}
