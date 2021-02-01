/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 1/26/19 7:06 PM
 */

package com.ula.gameapp.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.ula.gameapp.item.Egg;

import java.util.List;

@Dao
public interface EggDao {
    @Query("SELECT * FROM eggs WHERE id = :id")
    Egg getById(long id);

    @Query("SELECT * FROM eggs")
    List<Egg> getAll();

    @Insert
    void insertAll(Egg... eggs);
}
