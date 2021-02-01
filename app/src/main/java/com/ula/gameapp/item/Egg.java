/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 1/10/19 3:41 PM
 */

package com.ula.gameapp.item;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.ula.gameapp.core.Annotation;

import java.io.Serializable;

@Entity(tableName = "eggs")
public class Egg extends ULAObject implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "interaction")
    private String interaction;

    @ColumnInfo(name = "click")
    private int click;

    @ColumnInfo(name = "repeat")
    private int repeat;

    @ColumnInfo(name = "file_type")
    private String fileType;

    @ColumnInfo(name = "file_name")
    private String fileName;

    @ColumnInfo(name = "sound")
    private String sound;

    @ColumnInfo(name = "lock_min")
    private int lockMin;

    public Egg() {
    }

    @Ignore
    public Egg(String interaction, int click, int repeat, String fileType, String fileName, String sound, int lockMin) {
        this.interaction = interaction;
        this.click = click;
        this.repeat = repeat;
        this.fileType = fileType;
        this.fileName = fileName;
        this.sound = sound;
        this.lockMin = lockMin;
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getInteraction() {
        return interaction;
    }

    public void setInteraction(String interaction) {
        this.interaction = interaction;
    }

    public int getClick() {
        return click;
    }

    public void setClick(int click) {
        this.click = click;
    }

    @Override
    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    @Override
    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    @Override
    public int getLockMin() {
        return lockMin;
    }

    public void setLockMin(int lockMin) {
        this.lockMin = lockMin;
    }

    @Override
    public int getAge() {
        return Annotation.AGE_EGG;
    }

    @Override
    public Egg[] populateData() {
        return new Egg[]{
                // first run
                new Egg("open", 1, 1, "image", "1-1_00024.jpg", "", 0),
                new Egg("click", 3, 1, "gif", "1_1_1.gif", "", 0),
                new Egg("click", 1, 1, "gif", "1_1_2.gif", "", 0),
                new Egg("click", 9999, 9999, "gif", "1_1_3.gif", "", 0),

                // second run
                new Egg("open", 1, 1, "image", "1-2_00108.jpg", "", 0),
                new Egg("click", 1, 1, "gif", "1_2_1.gif", "", 0),
                new Egg("click", 1, 1, "gif", "1_2_2.gif", "", 0),
                new Egg("click", 3, 1, "gif", "1_2_3.gif", "", 0),
                new Egg("click", 9999, 9999, "gif", "1_2_4.gif", "", 0),

                // third run
                new Egg("open", 3, 3, "gif", "2_1_1.gif", "", 0),
                new Egg("click", 1, 3, "gif", "2_1_2.gif", "", 0),
                new Egg("click", 2, 3, "gif", "2_1_3.gif", "", 0),
                new Egg("click", 9999, 9999, "gif", "2_1_4.gif", "", 0),

                // fourth run
                new Egg("open", 2, 3, "gif", "2_2.gif", "", 0),
                new Egg("click", 2, 1, "gif", "2_3_1.gif", "", 0),
                new Egg("click", 1, 1, "gif", "2_3_2.gif", "", 0),
                new Egg("click", 1, 1, "gif", "2_4_1.gif", "", 0),
                new Egg("click", 3, 1, "gif", "2_4_2.gif", "", 5),
                new Egg("click", 1, 1, "gif", "2_4_3.gif", "", 0),
                new Egg("click", 9999, 1, "image", "white.png", "", 5),

                // fifth run
                new Egg("open", 1, 3, "gif", "3_1.gif", "", 0),
                new Egg("click", 4, 1, "gif", "3_2.gif", "", 0),
                new Egg("click", 1, 1, "gif", "3_2_1.gif", "", 0),
                new Egg("click", 1, 1, "gif", "3_2_2.gif", "", 0),
                new Egg("click", 9999, 1, "gif", "3_2_3.gif", "", 0),

                // sixth run
                new Egg("open", 1, 1, "gif", "4_1_1.gif", "", 0),
                new Egg("click", 1, 1, "gif", "4_1_2.gif", "", 0),
                new Egg("click", 1, 1, "gif", "4_2.gif", "", 0),
                new Egg("click", 9999, 1, "image", "white.png", "", 5),
        };
    }

    public static long getLockTimestamp(Context context) {
        SharedPreferences pref = context.getSharedPreferences("EggState", Context.MODE_PRIVATE);
        return pref.getLong("timestamp", 0);
    }

    public static void setLockTimestamp(Context context, long timestamp) {
        SharedPreferences pref = context.getSharedPreferences("EggState", Context.MODE_PRIVATE);
        pref.edit().putLong("timestamp", timestamp).apply();
    }
}