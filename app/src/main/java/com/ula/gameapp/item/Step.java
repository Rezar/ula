/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 1/10/19 3:41 PM
 */

package com.ula.gameapp.item;

import android.content.Context;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.ula.gameapp.core.logger.CatLogger;
import com.ula.gameapp.database.DatabaseClient;
import com.ula.gameapp.database.dao.StepDao;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity(tableName = "steps")
public class Step implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "date")
    private long date;

    @ColumnInfo(name = "step")
    private int step;

    @ColumnInfo(name = "type")
    private int type;


    public Step() {
    }

    @Ignore
    public Step(long date, int step, int type) {
        this.date = date;
        this.step = step;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static void saveCurrentSteps(Context context, int steps) {
        StepDao stepDao = DatabaseClient.getInstance(context).getAppDatabase().stepDao();
        Step step = stepDao.getByDate(-1);

        if (step == null) {
            step = new Step();
            step.setDate(-1);
            step.setStep(steps);

            // insert
            stepDao.insert(step);
        } else {
            step.setStep(steps);
            stepDao.update(step);
        }

        CatLogger.get().log("saving steps in db: " + steps);
    }

    public static int getCurrentSteps(Context context) {
        StepDao stepDao = DatabaseClient.getInstance(context).getAppDatabase().stepDao();
        Step step = stepDao.getByDate(-1);
        return (step != null) ? step.getStep() : 0;
    }

    public static void insertNewDay(Context context, long date, int steps) {
        StepDao stepDao = DatabaseClient.getInstance(context).getAppDatabase().stepDao();

    }

    public Map<String, Object> toMap(String installationId) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("installation_id", installationId);
        result.put("step", step);
        result.put("date", new Date(date));
        result.put("epoch_date", date);

        return result;
    }
}
