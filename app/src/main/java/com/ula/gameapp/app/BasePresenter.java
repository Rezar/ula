/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 3/28/19 11:19 AM
 */

package com.ula.gameapp.app;

public interface BasePresenter<T> {
    void setView(T view);

    void start();
}
