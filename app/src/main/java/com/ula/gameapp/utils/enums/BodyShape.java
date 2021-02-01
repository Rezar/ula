package com.ula.gameapp.utils.enums;

public enum BodyShape {
    NONE(0),
    NORMAL(1),
    FIT(2),
    FAT(3),
    OVER_WEIGHT(4);

    private final int value;
    private BodyShape(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
