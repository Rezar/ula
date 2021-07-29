package com.ula.gameapp.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ula.gameapp.item.FootStep;

import java.util.Date;
import java.util.List;

@Dao
public interface FootStepDao {

    @Query("SELECT * FROM FootStep WHERE type = 5 AND date BETWEEN :startDate AND :endDate ")
    List<FootStep> getStepsHistory(Date startDate, Date endDate);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertStepsHistory(List<FootStep> footSteps);

    @Query("SELECT stepCount FROM FootStep WHERE type = 5 AND  date = :mDate  ")
    int getStepsOfDate(Date mDate);
}
