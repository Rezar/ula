/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 4/6/19 10:01 PM
 */

package com.ula.gameapp.app.main.home;

import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.HistoryClient;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.ula.gameapp.App;
import com.ula.gameapp.item.JAnimation;
import com.ula.gameapp.item.PetStatus;
import com.ula.gameapp.utils.enums.Age;

public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View mView;
    private HistoryClient historyClient;

    private PetStatus petStatus;
    private int clickOnGif = 0;

    @Override
    public void setView(HomeContract.View view) {
        mView = view;
    }

    @Override
    public void initial(PetStatus petStatus, JAnimation animation) {
        Context context = App.getContext();
        /*if (PedometerManager.getPedometerType(context) == Annotation.PEDOMETER_GOOGLE_FIT) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                public void run() {
                    refreshStepsData();
                }
            }, 0, 3000);
        }*/

        //ula = Memory.decideNextGif(context);
        this.petStatus = petStatus;
        if (petStatus == null) return;

        mView.toggleHelpView(petStatus.getLastAge() == Age.EGG && petStatus.getId() == 1);
        //if (ula.getClick() >= 999) ula = Memory.decideNextGif(context);

        loadUlaDrawable(petStatus, animation);
        //Memory.setUlaState(context, ula);
    }

    @Override
    public void onGifClicked() {
        clickOnGif++;
    }

    @Override
    public void decideGifUpdate() {
        // we only change gif if we are in egg state
        /*if (ula.getAge() != Annotation.AGE_EGG) return;

        if (clickOnGif >= ula.getClick()) {
            mView.toggleHelpView(false);

            Egg.setLockTimestamp(App.getContext(), System.currentTimeMillis() + ula.getLockMin() * 60 * 1000);
            ula = Memory.decideNextGif(App.getContext());
            Memory.setUlaState(App.getContext(), ula);
            loadUlaDrawable(ula);

            long lastGifTimestamp;
            if (ula.getLockMin() > 0 && (lastGifTimestamp = Egg.getLockTimestamp(App.getContext())) > 0) {
                long subTime = lastGifTimestamp - System.currentTimeMillis();

                Toast.makeText(App.getContext(), "Lock " + ula.getId() + " - " + subTime, Toast.LENGTH_LONG).show();

                mView.showCountdown(subTime);
            }

            clickOnGif = 0;
        }*/
    }

    @Override
    public int getClickCount() {
        return clickOnGif;
    }

    @Override
    public void resetGifClick() {
        clickOnGif = 0;
    }

    public void loadUlaDrawable(PetStatus petStatus, JAnimation animation) {
        if (petStatus == null) return;

        mView.toggleDrawableHolder(animation.getFileType());
        mView.showDrawable(animation);
        //mView.showCountdown(ula.getLockMin());

    }

    private void refreshStepsData() {
        try {
            // initial fitness
            if (historyClient == null)
                historyClient = Fitness.getHistoryClient(App.getContext(), GoogleSignIn.getLastSignedInAccount(App.getContext()));

            Task<DataSet> response = historyClient.readDailyTotal(DataType.TYPE_STEP_COUNT_DELTA);
            response.addOnSuccessListener(new OnSuccessListener<DataSet>() {
                @Override
                public void onSuccess(DataSet dataSet) {
                    int total = dataSet.isEmpty() ? 0 : dataSet.getDataPoints().get(0).getValue(Field.FIELD_STEPS).asInt();
                    mView.updateStepsData(total);
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
