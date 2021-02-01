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
    private BodyShape lastBodyShape;
    private Age lastAge;

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
}
