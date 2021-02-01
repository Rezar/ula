package com.ula.gameapp.utils.enums;

public enum Age {
    EGG(1),
    CHILD(2),
    ADULT(3);

    private final int value;
    private Age(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
