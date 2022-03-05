package com.ula.gameapp.item;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.ula.gameapp.utils.enums.Age;
import com.ula.gameapp.utils.enums.BodyShape;
import com.ula.gameapp.utils.enums.EmotionType;
import com.ula.gameapp.utils.enums.FileType;

@Entity(tableName = "JAnimation")
public class JAnimation {
    @PrimaryKey(autoGenerate = false)
    private int Id;
    private FileType fileType;
    private String fileName;
    private int repeatCount;
    private BodyShape bodyShape;
    private Age age;
    private EmotionType emotionType;
    private boolean hasLock;
    @ColumnInfo(defaultValue = "1")
    private boolean display;

    public JAnimation() {
    }

    public JAnimation(int id, FileType fileType, String fileName, int repeatCount,
                      BodyShape bodyShape, Age age, EmotionType emotionType, boolean hasLock, boolean display) {
        Id = id;
        this.fileType = fileType;
        this.fileName = fileName;
        this.repeatCount = repeatCount;
        this.bodyShape = bodyShape;
        this.age = age;
        this.emotionType = emotionType;
        this.hasLock = hasLock;
        this.display = display;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
    }

    public BodyShape getBodyShape() {
        return bodyShape;
    }

    public void setBodyShape(BodyShape bodyShape) {
        this.bodyShape = bodyShape;
    }

    public Age getAge() {
        return age;
    }

    public void setAge(Age age) {
        this.age = age;
    }

    public EmotionType getEmotionType() {
        return emotionType;
    }

    public void setEmotionType(EmotionType emotionType) {
        this.emotionType = emotionType;
    }

    public boolean isHasLock() {
        return hasLock;
    }

    public void setHasLock(boolean hasLock) {
        this.hasLock = hasLock;
    }

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }
}
