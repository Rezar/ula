package com.ula.gameapp.item;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.ula.gameapp.utils.enums.Age;
import com.ula.gameapp.utils.enums.BodyShape;

@Entity(tableName = "Pet")
public class Pet {
    @PrimaryKey(autoGenerate = false)
    private int id;
    private Age age;
    private BodyShape bodyShape;
    private int currentAnimationId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Age getAge() {
        return age;
    }

    public void setAge(Age age) {
        this.age = age;
    }

    public BodyShape getBodyShape() {
        return bodyShape;
    }

    public void setBodyShape(BodyShape bodyShape) {
        this.bodyShape = bodyShape;
    }

    public int getCurrentAnimationId() {
        return currentAnimationId;
    }

    public void setCurrentAnimationId(int currentAnimationId) {
        this.currentAnimationId = currentAnimationId;
    }
}
