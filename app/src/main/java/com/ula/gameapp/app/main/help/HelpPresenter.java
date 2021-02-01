/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 4/11/19 4:30 PM
 */

package com.ula.gameapp.app.main.help;

public class HelpPresenter implements HelpContract.Presenter {

    private HelpContract.View mView;

    @Override
    public void setView(HelpContract.View view) {
        mView = view;
    }
}
