/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 3/28/19 11:50 PM
 */

package com.ula.gameapp.app.intro.fit;

import android.app.Activity;

import androidx.annotation.NonNull;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class FitPresenter implements FitContract.Presenter {

    private FitContract.View mView;

    @Override
    public void setView(@NonNull FitContract.View view) {
        mView = checkNotNull(view);
    }

    @Override
    public void viewCreated(Activity activity) {

    }
}
