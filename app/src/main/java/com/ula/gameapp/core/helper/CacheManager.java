/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 2/19/19 12:29 PM
 */

package com.ula.gameapp.core.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ula.gameapp.item.Step;

import java.util.List;

public class CacheManager {

    public static void setStatisticsCache(Context context, List<Step> list) {
        Gson gson = new Gson();
        SharedPreferences.Editor editor = context.getSharedPreferences("UlaCache", Context.MODE_PRIVATE).edit();
        editor.putString("stats_json", gson.toJson(list));
        editor.apply();
    }

    public static List<Step> getStatisticsCache(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("UlaCache", Context.MODE_PRIVATE);
        String statsJson = preferences.getString("stats_json", "[]");

        Gson gson = new Gson();
        return gson.fromJson(statsJson, new TypeToken<List<Step>>() {
        }.getType());
    }
}
