/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 1/10/19 3:41 PM
 */

package com.ula.gameapp.database.dao;

import android.content.Context;

import com.ula.gameapp.BuildConfig;
import com.ula.gameapp.core.logger.CatLogger;

public class StepDaoUtils {

    public static void insertNewDay(Context context, long date, int steps) {
//        StepDao stepDao = DatabaseClient.getInstance(context).getAppDatabase().stepDao();

//        if (stepDao.hasStepByDate(date) == 0 && steps >= 0) {
//            stepDao.addToLastSteps(steps);
//
//            Step step = new Step();
//            step.setDate(date);
//            step.setStep(-steps);
//            stepDao.insert(step);
//        }

        if (BuildConfig.DEBUG) {
            CatLogger.get().log("insertDay " + date + " / " + steps);
        }
    }
}
