/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 4/21/19 8:20 PM
 */

package com.ula.gameapp.app.main;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ula.gameapp.database.AppDatabase;
import com.ula.gameapp.database.DatabaseClient;
import com.ula.gameapp.item.Step;

import java.util.List;

public class StepViewModel extends AndroidViewModel {
    private AppDatabase database;
    private LiveData<List<Step>> stepsList;
    private MutableLiveData<Integer> steps;

    public StepViewModel(Application app) {
        super(app);
        database = DatabaseClient.getInstance(app).getAppDatabase();
        stepsList = database.stepDao().getLast7Days();
    }

    public LiveData<List<Step>> getStepsList() {
        return stepsList;
    }

    public MutableLiveData<Integer> getSteps() {
        if (steps == null) {
            steps = new MutableLiveData<>();
        }
        return steps;
    }

    public void deleteItem(Step step) {
        new deleteAsyncTask(database).execute(step);
    }

    private static class deleteAsyncTask extends AsyncTask<Step, Void, Void> {
        private AppDatabase db;

        deleteAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final Step... params) {
            db.stepDao().delete(params[0]);
            return null;
        }
    }
}