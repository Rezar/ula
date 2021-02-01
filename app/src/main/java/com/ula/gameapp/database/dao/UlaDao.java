/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 2/28/19 6:24 PM
 */

package com.ula.gameapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ula.gameapp.item.Ula;

import java.util.List;

@Dao
public interface UlaDao {
    @Insert
    long insert(Ula ula);

    @Insert
    void insertAll(Ula... ula);

    @Update
    void update(Ula ula);

    @Delete
    void delete(Ula ula);

    @Query("SELECT * FROM ula")
    List<Ula> getAll();

    @Query("SELECT * FROM ula WHERE id = :id")
    Ula getById(long id);

    @Query("SELECT * FROM ula WHERE age = :age AND body = :body AND emotion = :emotion")
    List<Ula> getByStatus(String age, String body, String emotion);

    @Query("DELETE FROM ula")
    void nukeTable();
}
