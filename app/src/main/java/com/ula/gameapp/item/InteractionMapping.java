package com.ula.gameapp.item;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.ula.gameapp.utils.enums.InteractionType;

@Entity(tableName = "InteractionMapping")
public class InteractionMapping {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int animationId;
    private InteractionType interaction;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAnimationId() {
        return animationId;
    }

    public void setAnimationId(int animationId) {
        this.animationId = animationId;
    }

    public InteractionType getInteraction() {
        return interaction;
    }

    public void setInteraction(InteractionType interaction) {
        this.interaction = interaction;
    }
}
