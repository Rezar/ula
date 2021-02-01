/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 3/1/19 3:21 PM
 */

package com.ula.gameapp.core.logger;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ula.gameapp.item.Step;
import com.ula.gameapp.utils.Installation;

import java.util.Arrays;
import java.util.List;

public class FirebaseLogger implements ILogger {
    private final static String TAG = "FirebaseLogger";
    private static FirebaseLogger firebaseLogger;

    private Context mContext;
    private FirebaseDatabase db;

    private FirebaseLogger(Context context) {
        this.mContext = context;
    }

    public synchronized static FirebaseLogger get(Context context) {
        if (firebaseLogger == null)
            firebaseLogger = new FirebaseLogger(context);
        return firebaseLogger;
    }

    @Override
    public void log(Object data) {
        if (db == null)
            db = FirebaseDatabase.getInstance();

        if (data instanceof List)
            //noinspection unchecked
            logStep((List<Step>) data);

        else if (data instanceof Step)
            logStep((Step) data);
    }

    private void logStep(Step step) {
        this.logStep(Arrays.asList(step));
    }

    private void logStep(List<Step> steps) {
        DatabaseReference stepsReference = db.getReference("steps");
        stepsReference.child(Installation.id(mContext)).setValue(steps);
    }
}
