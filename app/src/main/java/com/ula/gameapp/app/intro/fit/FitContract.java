/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 3/28/19 11:50 PM
 */

package com.ula.gameapp.app.intro.fit;

import com.ula.gameapp.app.intro.BaseContract;

class FitContract<T> {

    interface View extends BaseContract.View<Presenter> {

    }

    interface Presenter extends BaseContract.Presenter<View> {

    }
}
