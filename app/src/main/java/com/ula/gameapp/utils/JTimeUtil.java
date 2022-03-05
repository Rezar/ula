package com.ula.gameapp.utils;

import java.util.Calendar;
import java.util.Date;

public class JTimeUtil {

    private static JTimeUtil instance;
    private static Calendar calendar;

    public static JTimeUtil getInstance() {
        if (instance == null) {
            instance = getSync();
        }
        return instance;
    }

    private static synchronized JTimeUtil getSync() {
        if (instance == null) {
            instance = new JTimeUtil();
        }
        return instance;
    }

    private JTimeUtil() {
        calendar = Calendar.getInstance();
    }

    public Date getMinDate() {

        calendar.set(Calendar.YEAR, calendar.getMinimum(Calendar.YEAR));
        calendar.set(Calendar.MONTH, calendar.getMinimum(Calendar.MONTH));
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getMinimum(Calendar.DAY_OF_YEAR));
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}
