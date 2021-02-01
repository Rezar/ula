/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 4/12/19 1:37 PM
 */

package com.ula.gameapp.app.main.help;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ula.gameapp.R;
import com.ula.gameapp.app.intro.IntroHelper;

import butterknife.ButterKnife;

public class HelpFragment extends Fragment implements HelpContract.View {

    public HelpContract.Presenter mPresenter = null;

    public HelpFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_intro, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter = new HelpPresenter();
        mPresenter.setView(this);

        IntroHelper helper = new IntroHelper(getActivity(), getFragmentManager());
    }
}