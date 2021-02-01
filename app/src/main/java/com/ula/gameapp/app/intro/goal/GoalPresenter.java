/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 3/29/19 12:19 AM
 */

package com.ula.gameapp.app.intro.goal;

import android.app.Activity;

import androidx.annotation.NonNull;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class GoalPresenter implements GoalContract.Presenter {

    private GoalContract.View mView;

    @Override
    public void setView(@NonNull GoalContract.View view) {
        mView = checkNotNull(view);
    }

    @Override
    public void viewCreated(Activity activity) {
        mView.initialTest();
    }
}
