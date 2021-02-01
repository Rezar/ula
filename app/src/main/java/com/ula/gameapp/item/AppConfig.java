package com.ula.gameapp.item;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "AppConfig")
public class AppConfig {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(defaultValue = "0")
    private boolean displayStepCounts;
    @ColumnInfo(defaultValue = "0.2")
    private float minThreshold;
    @ColumnInfo(defaultValue = "0.2")
    private float maxThreshold;
    @ColumnInfo(defaultValue = "3")
    private int effectiveDays;
    @ColumnInfo(defaultValue = "60000")
    private long lockTime;
    @ColumnInfo(defaultValue = "1")
    private boolean displayTapToGo;
    private Date lastLockPoint;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDisplayStepCounts() {
        return displayStepCounts;
    }

    public void setDisplayStepCounts(boolean displayStepCounts) {
        this.displayStepCounts = displayStepCounts;
    }

    public float getMinThreshold() {
        return minThreshold;
    }

    public void setMinThreshold(float minThreshold) {
        this.minThreshold = minThreshold;
    }

    public float getMaxThreshold() {
        return maxThreshold;
    }

    public void setMaxThreshold(float maxThreshold) {
        this.maxThreshold = maxThreshold;
    }

    public int getEffectiveDays() {
        return effectiveDays;
    }

    public void setEffectiveDays(int effectiveDays) {
        this.effectiveDays = effectiveDays;
    }

    public long getLockTime() {
        return lockTime;
    }

    public void setLockTime(long lockTime) {
        this.lockTime = lockTime;
    }

    public boolean isDisplayTapToGo() {
        return displayTapToGo;
    }

    public void setDisplayTapToGo(boolean displayTapToGo) {
        this.displayTapToGo = displayTapToGo;
    }

    public Date getLastLockPoint() {
        return lastLockPoint;
    }

    public void setLastLockPoint(Date lastLockPoint) {
        this.lastLockPoint = lastLockPoint;
    }
}
