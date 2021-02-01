/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 1/26/19 7:06 PM
 */

package com.ula.gameapp.core.logger;

import android.database.Cursor;

import com.ula.gameapp.BuildConfig;

public class CatLogger implements ILogger {
    private final static String TAG = "ULA";
    private static CatLogger catLogger;

    public synchronized static CatLogger get() {
        if (catLogger == null)
            catLogger = new CatLogger();
        return catLogger;
    }

    @Override
    public void log(Object data) {
        if (data instanceof Throwable)
            logThrowable((Throwable) data);
        else if (data instanceof Cursor)
            logCursor((Cursor) data);
        else if (data instanceof String)
            logString((String) data);
    }

    private void logThrowable(Throwable ex) {
        logString(ex.getMessage());
        for (StackTraceElement ste : ex.getStackTrace()) {
            logString(ste.toString());
        }
    }

    private void logCursor(final Cursor c) {
        if (!BuildConfig.DEBUG) return;
        c.moveToFirst();
        String title = "";
        for (int i = 0; i < c.getColumnCount(); i++)
            title += c.getColumnName(i) + "\t| ";
        logString(title);
        while (!c.isAfterLast()) {
            title = "";
            for (int i = 0; i < c.getColumnCount(); i++)
                title += c.getString(i) + "\t| ";
            logString(title);
            c.moveToNext();
        }
    }

    private void logString(String msg) {
        if (!BuildConfig.DEBUG) return;
        android.util.Log.d(TAG, msg);
    }
}
