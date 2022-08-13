package com.ula.wear.activitytracker;


import java.util.Date;

public class FootStep {
    private int id;
    private Date date;
    private int stepCount;
    private int totalSteps;
    private int googleFitness;
    private int downstairs;
    private int jogging;
    private int upstairs;
    private int walking;
    private int sitting;
    private int standing;
    private  int type;

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

    public int getTotalSteps() {
        return totalSteps;
    }

    public void setTotalSteps(int totalSteps) {
        this.totalSteps = totalSteps;
    }

    public int getGoogleFitness() {
        return googleFitness;
    }

    public void setGoogleFitness(int googleFitness) {
        this.googleFitness = googleFitness;
    }

    public int getDownstairs() {
        return downstairs;
    }

    public void setDownstairs(int downstairs) {
        this.downstairs = downstairs;
    }

    public int getJogging() {
        return jogging;
    }

    public void setJogging(int jogging) {
        this.jogging = jogging;
    }

    public int getUpstairs() {
        return upstairs;
    }

    public void setUpstairs(int upstairs) {
        this.upstairs = upstairs;
    }

    public int getWalking() {
        return walking;
    }

    public void setWalking(int walking) {
        this.walking = walking;
    }

    public int getSitting() {
        return sitting;
    }

    public void setSitting(int sitting) {
        this.sitting = sitting;
    }

    public int getStanding() {
        return standing;
    }

    public void setStanding(int standing) {
        this.standing = standing;
    }

    public int getType() { return type; }

    public void setType(int type) { this.type = type; }


}