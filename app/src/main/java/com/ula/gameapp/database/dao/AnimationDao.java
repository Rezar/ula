package com.ula.gameapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.ula.gameapp.item.JAnimation;
import com.ula.gameapp.utils.enums.Age;
import com.ula.gameapp.utils.enums.BodyShape;
import com.ula.gameapp.utils.enums.EmotionType;
import com.ula.gameapp.utils.enums.FileType;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface AnimationDao {

    @Query("SELECT * FROM JAnimation WHERE Id = :Id AND display = 1")
    JAnimation getJAnimation(int Id);

    @Insert
    void insertAll(ArrayList<JAnimation> animationList);

    @Delete
    void delete(JAnimation jAnimation);

    @Query("SELECT COUNT(Id) FROM JAnimation")
    int getAnimationsCount();

    @Query("SELECT * FROM JAnimation WHERE bodyShape = :bodyShape AND age = :age AND Id > :animationId AND display = 1")
    JAnimation getJAnimation(BodyShape bodyShape, Age age, int animationId);

    @Query("SELECT Id FROM JAnimation WHERE age = :age AND display = 1 ORDER BY Id DESC LIMIT 1")
    int getLastAnimationId(Age age);

    @Query("SELECT DISTINCT bodyShape FROM JAnimation WHERE age = :age")
    List<BodyShape> getAllBodyShape(Age age);

    @Query("SELECT DISTINCT emotionType FROM JAnimation WHERE age = :age")
    List<EmotionType> getAllEmotion(Age age);

    @Query("SELECT DISTINCT fileName FROM JAnimation WHERE age = :age AND bodyShape = :bodyShape AND display = 1")
    List<String> getAllFileName(Age age, BodyShape bodyShape);

    @Query("SELECT * FROM JAnimation WHERE fileName = :fileName")
    JAnimation getJAnimation(String fileName);

    @Query("SELECT fileType FROM janimation WHERE Id = :animationId")
    FileType getFileType(int animationId);

    @Query("SELECT Id FROM JAnimation WHERE Id > :currentId AND display = 1")
    int getNextId(int currentId);
}
