/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 1/10/19 3:39 PM
 */

package com.ula.gameapp.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ula.gameapp.item.Step;

import java.util.List;

@Dao
public interface StepDao {
    @Insert
    long insert(Step step);

    @Update
    void update(Step step);

    @Delete
    void delete(Step step);

    @Query("SELECT * FROM steps WHERE type = 5")
    List<Step> getAll();

    @Query("SELECT * FROM steps WHERE date >= strftime('%s', date('now','-7 days')) * 1000 AND date <= strftime('%s', date('now')) * 1000 AND step > 0 AND type = 5")
    LiveData<List<Step>> getLast7Days();

    @Query("SELECT * FROM steps WHERE id = :id")
    Step getById(long id);

    @Query("SELECT COUNT(*) FROM steps WHERE date = :date")
    int hasStepByDate(long date);

    @Query("SELECT * FROM steps WHERE date = :date AND type = 5")
    Step getByDate(long date);

    @Query("DELETE FROM steps")
    void nukeTable();

    @Query("DELETE FROM steps WHERE step < 0")
    void deleteNegativeSteps();

    @Query("UPDATE steps SET step = :step WHERE date = -1")
    void setCurrentSteps(long step);

    @Query("UPDATE steps SET step = step + :step WHERE date = (SELECT MAX(date) FROM steps)")
    void addToLastSteps(int step);
}
