package com.ula.gameapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;
import androidx.room.Update;

import com.ula.gameapp.item.Pet;

@Dao
public interface PetDao {

    @Query("SELECT * FROM PET LIMIT 1")
    Pet getPet();

    @Update
    void update(Pet pet);

    @Delete
    void delete(Pet pet);
}
