/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 2/9/19 10:59 PM
 */

package com.ula.gameapp.core;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Annotation {
    public static final int AGE_EGG = 0;
    public static final int AGE_CHILD = 1;
    public static final int AGE_ADULT = 2;
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({AGE_EGG, AGE_CHILD, AGE_ADULT})
    public @interface AgeDef {}

    public static final int PEDOMETER_UNKNOWN = -1;
    public static final int PEDOMETER_SENSOR = 0;
    public static final int PEDOMETER_GOOGLE_FIT = 1;
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({PEDOMETER_UNKNOWN, PEDOMETER_SENSOR, PEDOMETER_GOOGLE_FIT})
    public @interface PedometerDef {}
}
