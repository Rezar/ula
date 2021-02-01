/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 3/28/19 7:52 PM
 */

package com.ula.gameapp.app.intro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;

public class IntroFragment extends Fragment {
    private static final String ARG_PRESENTER = "ARG_PRESENTER";
    private static final String ARG_VIEW = "ARG_VIEW";
    private static final String ARG_TAG = "ARG_TAG";

    private String TAG;

    private BaseContract.Presenter presenter = null;
    private BaseContract.View view = null;

    public static IntroFragment newInstance(Class presenter, Class view) {
        IntroFragment fragment = new IntroFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PRESENTER, presenter.getCanonicalName());
        args.putString(ARG_VIEW, view.getCanonicalName());
        args.putString(ARG_TAG, presenter.getSimpleName());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            TAG = getArguments().getString(ARG_TAG);
            String argPresenter = getArguments().getString(ARG_PRESENTER);
            String argView = getArguments().getString(ARG_VIEW);

            try {
                presenter = (BaseContract.Presenter) Class.forName(argPresenter).newInstance();
                view = (BaseContract.View) Class.forName(argView).newInstance();

                presenter.setView(view);
                view.setPresenter(presenter);
            } catch (Exception ex) {
                throw new NullPointerException("Passed class doesn't exist: " + ex.getMessage());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(view.getView(), container, false);
        ButterKnife.bind(view, v);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        presenter.viewCreated(getActivity());
    }
}
