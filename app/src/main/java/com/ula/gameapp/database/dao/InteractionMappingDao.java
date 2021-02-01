package com.ula.gameapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.ula.gameapp.item.InteractionMapping;

@Dao
public interface InteractionMappingDao {

    @Query("SELECT * FROM InteractionMapping WHERE animationId = :animationId ")
    InteractionMapping getInteractionMapping(int animationId);

    @Insert
    void insert(InteractionMapping interactionMapping);

    @Delete
    void delete(InteractionMapping interactionMapping);
}
