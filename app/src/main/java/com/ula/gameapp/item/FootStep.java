package com.ula.gameapp.item;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "FootStep", indices = {@Index(value = {"date"}, unique = true)})
public class FootStep {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private Date date;
    private int stepCount;
    private int type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getStepCount() {
        return stepCount;
    }

    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) { this.type = type; }
}
