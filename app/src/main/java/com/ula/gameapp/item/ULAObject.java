/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 1/27/19 6:58 PM
 */

package com.ula.gameapp.item;

public abstract class ULAObject<T> {

    public abstract long getId();

    public abstract int getAge();

    public abstract int getClick();

    public abstract String getFileType();

    public abstract String getFileName();

    public abstract int getRepeat();

    public abstract int getLockMin();

    public abstract T[] populateData();

    public float getSpeed() {
        return 1.6f;
    }
}