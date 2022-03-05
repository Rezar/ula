package com.ula.gameapp.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ula.gameapp.item.PetStatus;

@Dao
public interface PetStatusDao {

    @Query("SELECT * FROM PetStatus ORDER BY Id DESC LIMIT 1")
    LiveData<PetStatus> getLastStatus();

    @Update
    void update(PetStatus petStatus);

    @Delete
    void delete(PetStatus petStatus);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PetStatus petStatus);

    @Query("SELECT * FROM PetStatus ORDER BY Id DESC LIMIT 1")
    PetStatus getCurrentStatus();
}
