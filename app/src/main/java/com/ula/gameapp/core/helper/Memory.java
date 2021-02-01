/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 1/27/19 6:27 PM
 */

package com.ula.gameapp.core.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.ula.gameapp.core.Annotation;
import com.ula.gameapp.item.Egg;
import com.ula.gameapp.item.ULAObject;
import com.ula.gameapp.item.Ula;

public class Memory {
    public static ULAObject decideNextGif(Context context) {
        ULAObject ula = getUlaState(context, getAge(context));

//        if (ula == null) {
//            return DatabaseClient.getInstance(context).getAppDatabase().eggDao().getById(1);
//        }

//        switch (ula.getAge()) {
//            case Annotation.AGE_EGG:
//                long id = ula.getId() + 1;
//                Log.d("ttttttttttt", "id " + id);
//                ula = DatabaseClient.getInstance(context).getAppDatabase().eggDao().getById(id);
//                break;
//
//            case Annotation.AGE_CHILD:
//            case Annotation.AGE_ADULT:
//                long i = ula.getId() + 1;
//                ula = DatabaseClient.getInstance(context).getAppDatabase().ulaDao().getById(i);
//        }

//        if (ula == null) {
//            int ulaAge = getAge(context);
//            Log.d("tttttttttt", "AGE: " + ulaAge);
//
//            if (ulaAge == Annotation.AGE_EGG) {
//                setAge(context, Annotation.AGE_CHILD);
//                setUlaState(context, DatabaseClient.getInstance(context).getAppDatabase().ulaDao().getById(1));
//                Log.d("tttttttttt", "AGE_CHILD");
//
//            } else if (ulaAge == Annotation.AGE_CHILD) {
//                setAge(context, Annotation.AGE_ADULT);
//                setUlaState(context, DatabaseClient.getInstance(context).getAppDatabase().ulaDao().getById(22));
//                Log.d("tttttttttt", "AGE_CHILD");
//            }
//        }

        return ula;
    }

    private static int getAge(Context context) {
        SharedPreferences pref = context.getSharedPreferences("UlaStateDatabase", Context.MODE_PRIVATE);
        return pref.getInt("age", 0);
    }

    public static void setAge(Context context, @Annotation.AgeDef int age) {
        SharedPreferences pref = context.getSharedPreferences("UlaStateDatabase", Context.MODE_PRIVATE);
        pref.edit().putInt("age", age).apply();
    }

    public static void setUlaState(Context context, ULAObject ula) {
        SharedPreferences mPrefs = context.getSharedPreferences("UlaStateDatabase", Context.MODE_PRIVATE);
        String json = (new Gson()).toJson(ula);
        mPrefs.edit().putString("ula", json).apply();
    }

    public static ULAObject getUlaState(Context context, @Annotation.AgeDef int age) {
        SharedPreferences mPrefs = context.getSharedPreferences("UlaStateDatabase", Context.MODE_PRIVATE);
        String json = mPrefs.getString("ula", "");

        Gson gson = new Gson();
        switch (age) {
            case Annotation.AGE_EGG:
                return gson.fromJson(json, Egg.class);
            case Annotation.AGE_CHILD:
            case Annotation.AGE_ADULT:
                return gson.fromJson(json, Ula.class);
            default:
                throw new RuntimeException("No ula class available");
        }
    }
}