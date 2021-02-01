/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 12/24/18 11:55 PM
 */

package com.ula.gameapp.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;

public class DatabaseClient {
    private Context mCtx;
    private static DatabaseClient mInstance;

    // our app database object
    private AppDatabase appDatabase;

    private DatabaseClient(final Context mCtx) {
        this.mCtx = mCtx;

        // creating the app database with Room database builder
        appDatabase = Room.databaseBuilder(mCtx, AppDatabase.class, "game_app_db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });
                    }
                })
                .build();
    }

    public static synchronized DatabaseClient getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new DatabaseClient(mCtx);
        }
        return mInstance;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }
}