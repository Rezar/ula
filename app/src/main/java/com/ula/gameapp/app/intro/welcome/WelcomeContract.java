/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 3/28/19 11:38 PM
 */

package com.ula.gameapp.app.intro.welcome;

import com.ula.gameapp.app.intro.BaseContract;

class WelcomeContract<T> {

    interface View extends BaseContract.View<Presenter> {

        void toggleCountdown(boolean visible);

        void startCountdown(long time);

        void checkTerms(boolean state);
    }

    interface Presenter extends BaseContract.Presenter<View> {

    }
}
