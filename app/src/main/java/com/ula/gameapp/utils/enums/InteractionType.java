package com.ula.gameapp.utils.enums;

public enum InteractionType {
    CLICK(1);

    private int value;
    private InteractionType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
