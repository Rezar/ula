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

    @ColumnInfo(defaultValue = "1")
    private int tapsCount;

    private boolean hasLoop;
    private boolean multiLoop;
    private int startId;
    private int endId;

    private int scenario;

    public JAnimation() {
    }

    public JAnimation(int id, FileType fileType, String fileName, int repeatCount,
                      BodyShape bodyShape, Age age, EmotionType emotionType, boolean hasLock, boolean display, int tapsCount,int scenario) {
        Id = id;
        this.fileType = fileType;
        this.fileName = fileName;
        this.repeatCount = repeatCount;
        this.bodyShape = bodyShape;
        this.age = age;
        this.emotionType = emotionType;
        this.hasLock = hasLock;
        this.display = display;
        this.tapsCount = tapsCount;
        this.hasLoop = false;
        this.multiLoop = false;
        this.scenario= scenario;

    }

    public JAnimation(int id, FileType fileType, String fileName, int repeatCount,
                      BodyShape bodyShape, Age age, EmotionType emotionType, boolean hasLock, boolean display, int tapsCount, boolean hasLoop,int scenario) {
        Id = id;
        this.fileType = fileType;
        this.fileName = fileName;
        this.repeatCount = repeatCount;
        this.bodyShape = bodyShape;
        this.age = age;
        this.emotionType = emotionType;
        this.hasLock = hasLock;
        this.display = display;
        this.tapsCount = tapsCount;
        this.hasLoop = hasLoop;
        this.multiLoop = false;
        this.scenario= scenario;

    }

    public JAnimation(int id, FileType fileType, String fileName, int repeatCount,
                      BodyShape bodyShape, Age age, EmotionType emotionType, boolean hasLock, boolean display, int tapsCount, boolean multiLoop, int startId, int endId,int scenario) {
        Id = id;
        this.fileType = fileType;
        this.fileName = fileName;
        this.repeatCount = repeatCount;
        this.bodyShape = bodyShape;
        this.age = age;
        this.emotionType = emotionType;
        this.hasLock = hasLock;
        this.display = display;
        this.tapsCount = tapsCount;
        this.hasLoop = false;
        this.multiLoop = multiLoop;
        this.startId = startId;
        this.endId = endId;
        this.scenario= scenario;

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

    public void setTapsCount(int tapsCount) {
        this.tapsCount = tapsCount;
    }

    public int getTapsCount() {
        return tapsCount;
    }

    public void setHasLoop(boolean hasLoop) {
        this.hasLoop = hasLoop;
    }

    public boolean getHasLoop() {
        return hasLoop;
    }

    public void setMultiLoop(boolean multiLoop) {
        this.multiLoop = multiLoop;
    }

    public boolean getMultiLoop() {
        return multiLoop;
    }

    public void setStartId(int startId) {
        this.startId = startId;
    }

    public int getStartId() { return startId; }

    public void setEndId(int endId) {
        this.endId = endId;
    }

    public int getEndId() { return endId; }

    public void setScenario(int scenario) {
        this.scenario = scenario;
    }

    public int getScenario() { return scenario; }
}


