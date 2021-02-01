/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 3/29/19 12:19 AM
 */

package com.ula.gameapp.app.intro.tutorial;

import androidx.annotation.NonNull;

import com.ula.gameapp.R;
import com.ula.gameapp.app.intro.BaseContract;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class TutorialView implements TutorialContract.View {

    private BaseContract.Presenter mPresenter;

    @Override
    public int getView() {
        return R.layout.fragment_intro_tutorial;
    }

    @Override
    public void setPresenter(@NonNull TutorialContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}
