/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 4/11/19 4:39 PM
 */

package com.ula.gameapp.app.main.stats;

import com.ula.gameapp.App;
import com.ula.gameapp.core.helper.CacheManager;
import com.ula.gameapp.item.Step;

import java.util.List;

public class StatsPresenter implements StatsContract.Presenter {

    private StatsContract.View mView;

    @Override
    public void setView(StatsContract.View view) {
        mView = view;

        mView.initRecycler();
        mView.initPedometer();
    }

    @Override
    public List<Step> loadFromCache() {
        return CacheManager.getStatisticsCache(App.getContext());
    }
}
