/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 4/11/19 4:39 PM
 */

package com.ula.gameapp.app.main.stats;

import com.ula.gameapp.item.Step;

import java.util.List;

class StatsContract {

    interface View {

        void initRecycler();

        void initPedometer();
    }

    interface Presenter {

        void setView(View view);

        List<Step> loadFromCache();
    }
}
