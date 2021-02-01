/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 3/28/19 12:11 PM
 */

package com.ula.gameapp.app.intro;

import android.app.Activity;

public class BaseContract {

    public interface View<T> {

        int getView();

        void setPresenter(T presenter);
    }

    public interface Presenter<T> {

        void setView(T view);

        void viewCreated(Activity activity);
    }
}
