/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 3/29/19 12:19 AM
 */

package com.ula.gameapp.app.intro.goal;

import com.ula.gameapp.app.intro.BaseContract;

class GoalContract<T> {

    interface View extends BaseContract.View<Presenter> {

        void initialTest();

    }

    interface Presenter extends BaseContract.Presenter<View> {

    }
}
