package com.ula.gameapp.utils.enums;

public enum FileType {
    GIF(1),
    IMAGE(2),
    MOVIE(3);

    private final int value;
    private FileType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
