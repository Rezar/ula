/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 3/28/19 11:50 PM
 */

package com.ula.gameapp.app.intro.fit;

import androidx.annotation.NonNull;

import com.ula.gameapp.R;
import com.ula.gameapp.app.intro.BaseContract;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class FitView implements FitContract.View {

    private BaseContract.Presenter mPresenter;

    @Override
    public int getView() {
        return R.layout.fragment_intro_fit;
    }

    @Override
    public void setPresenter(@NonNull FitContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}
