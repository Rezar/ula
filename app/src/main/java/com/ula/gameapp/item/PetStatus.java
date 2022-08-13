package com.ula.gameapp.item;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.ula.gameapp.utils.enums.Age;
import com.ula.gameapp.utils.enums.BodyShape;

@Entity(tableName = "PetStatus")
public class PetStatus {
    @PrimaryKey(autoGenerate = true)
    private int Id;
    private int AnimationId;
    private int repeatCounter;
    private int tapCounter;
    private int startId;
    private int endId;
    private boolean hasLoop;
    private boolean multiLoop;
    private BodyShape lastBodyShape;
    private Age lastAge;
    private int scenario;



    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getAnimationId() {
        return AnimationId;
    }

    public void setAnimationId(int animationId) {
        AnimationId = animationId;
    }

    public int getRepeatCounter() {
        return repeatCounter;
    }

    public void setRepeatCounter(int repeatCounter) {
        this.repeatCounter = repeatCounter;
    }

    public int getTapCounter() {
        return tapCounter;
    }

    public void setTapCounter(int tapCounter) {
        this.tapCounter = tapCounter;
    }

    public BodyShape getLastBodyShape() {
        return lastBodyShape;
    }

    public void setLastBodyShape(BodyShape lastBodyShape) {
        this.lastBodyShape = lastBodyShape;
    }

    public Age getLastAge() {
        return lastAge;
    }

    public void setLastAge(Age lastAge) {
        this.lastAge = lastAge;
    }

    public void setHasLoop(boolean hasLoop) {
        this.hasLoop= hasLoop;
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
