package com.ula.gameapp.utils.enums;

public enum EmotionType {
    NONE(0),
    OK(1),
    SAD(2),
    HAPPY(3);

    private int value;
    private  EmotionType(int value){
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }
}
