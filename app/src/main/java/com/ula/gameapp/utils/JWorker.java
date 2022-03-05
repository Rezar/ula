package com.ula.gameapp.utils;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.ula.gameapp.repository.DataRepository;

public class JWorker extends Worker {
    public static final String TAG = JWorker.class.getSimpleName();

    public JWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            DataRepository.getInstance().onLockEnd();
            Log.i(TAG, TAG);
        } catch (Exception e) {
            return Result.retry();
        }
        return Result.success();
    }
}
