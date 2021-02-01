/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 2/20/19 12:09 AM
 */

package com.ula.gameapp.item;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.ula.gameapp.core.Annotation;

import java.io.Serializable;

@Entity(tableName = "ula")
public class Ula extends ULAObject implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "age")
    private String strAge;

    @ColumnInfo(name = "body")
    private String body;

    @ColumnInfo(name = "emotion")
    private String emotion;

    @ColumnInfo(name = "file_type")
    private String fileType;

    @ColumnInfo(name = "file_name")
    private String fileName;

    @ColumnInfo(name = "sound")
    private String sound;

    public Ula() {
    }

    @Ignore
    private Ula(String age, String body, String emotion, String fileType, String fileName, String sound) {
        this.strAge = age;
        this.body = body;
        this.emotion = emotion;
        this.fileType = fileType;
        this.fileName = fileName;
        this.sound = sound;
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStrAge() {
        return strAge;
    }

    public void setStrAge(String strAge) {
        this.strAge = strAge;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
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
    public int getClick() {
        return 0;
    }

    @Override
    public int getRepeat() {
        return 1;
    }

    @Override
    public int getLockMin() {
        return 0;
    }

    @Override
    public int getAge() {
        switch (strAge) {
            case "child":
                return Annotation.AGE_CHILD;
            case "adult":
                return Annotation.AGE_ADULT;
            default:
                throw new RuntimeException("Age not specified: " + strAge);
        }
    }

    @Override
    public Ula[] populateData() {
        return new Ula[]{
                new Ula("child", "normal", "happy", "gif", "6_1.gif", ""),
                new Ula("child", "normal", "happy", "gif", "6_2.gif", ""),
                new Ula("child", "normal", "happy", "gif", "6_3.gif", ""),
//                new Ula("child", "normal", "happy", "gif", "6_4.gif", ""),
                new Ula("child", "normal", "happy", "gif", "7_1.gif", ""),
                new Ula("child", "normal", "happy", "gif", "7_2.gif", ""),
                new Ula("child", "normal", "happy", "gif", "7_3.gif", ""),
//                new Ula("child", "normal", "happy", "gif", "7_4.gif", ""),

                new Ula("child", "normal", "ok", "gif", "8_1.gif", ""),
                new Ula("child", "normal", "ok", "gif", "8_2.gif", ""),
                new Ula("child", "normal", "ok", "gif", "8_3.gif", ""),
//                new Ula("child", "normal", "ok", "gif", "8_4.gif", ""),

                new Ula("child", "overweight", "ok", "gif", "9_1.gif", ""),
                new Ula("child", "overweight", "ok", "gif", "9_2.gif", ""),
                new Ula("child", "overweight", "ok", "gif", "9_3.gif", ""),
//                new Ula("child", "overweight", "ok", "gif", "9_4.gif", ""),
                new Ula("child", "overweight", "ok", "gif", "10_1.gif", ""),
                new Ula("child", "overweight", "ok", "gif", "10_2.gif", ""),
                new Ula("child", "overweight", "ok", "gif", "10_3.gif", ""),
//                new Ula("child", "overweight", "ok", "gif", "10_4.gif", ""),

                new Ula("child", "fat", "sad", "gif", "11_1.gif", ""),
                new Ula("child", "fat", "sad", "gif", "11_2.gif", ""),
                new Ula("child", "fat", "sad", "gif", "11_3.gif", ""),
                new Ula("child", "fat", "sad", "gif", "12_1.gif", ""),
                new Ula("child", "fat", "sad", "gif", "12_2.gif", ""),
                new Ula("child", "fat", "sad", "gif", "12_3.gif", ""),
//                new Ula("child", "fat", "sad", "gif", "12_4.gif", ""),

                new Ula("child_to_adult", "normal", "ok", "gif", "13_1.gif", ""),
                new Ula("adult", "normal", "ok", "gif", "13_2.gif", ""),
                new Ula("adult", "normal", "ok", "gif", "13_3.gif", ""),
                new Ula("adult", "normal", "ok", "gif", "14_1.gif", ""),
                new Ula("adult", "normal", "ok", "gif", "14_2.gif", ""),
                new Ula("adult", "normal", "ok", "gif", "14_3.gif", ""),
                new Ula("adult", "normal", "ok", "gif", "15_1.gif", ""),
                new Ula("adult", "normal", "ok", "gif", "15_2.gif", ""),
                new Ula("adult", "normal", "ok", "gif", "15_3.gif", ""),
                new Ula("adult", "normal", "ok", "gif", "16_1.gif", ""),
                new Ula("adult", "normal", "ok", "gif", "16_2.gif", ""),
                new Ula("adult", "normal", "ok", "gif", "16_3.gif", ""),

                new Ula("adult", "overweight", "sad", "gif", "17_1.gif", ""),
                new Ula("adult", "overweight", "sad", "gif", "17_2.gif", ""),
                new Ula("adult", "overweight", "sad", "gif", "17_3.gif", ""),
                new Ula("adult", "overweight", "sad", "gif", "17_3_1.gif", ""),
                new Ula("adult", "overweight", "sad", "gif", "18_1.gif", ""),
                new Ula("adult", "overweight", "sad", "gif", "18_2.gif", ""),
                new Ula("adult", "overweight", "sad", "gif", "18_3.gif", ""),
                new Ula("adult", "overweight", "sad", "gif", "19_1.gif", ""),
                new Ula("adult", "overweight", "sad", "gif", "19_2.gif", ""),
                new Ula("adult", "overweight", "sad", "gif", "19_3.gif", ""),
                new Ula("adult", "overweight", "sad", "gif", "20_1.gif", ""),
                new Ula("adult", "overweight", "sad", "gif", "20_2.gif", ""),
                new Ula("adult", "overweight", "sad", "gif", "20_3.gif", ""),

                new Ula("adult", "fat", "sad", "gif", "21_1.gif", ""),
                new Ula("adult", "fat", "sad", "gif", "21_2.gif", ""),
                new Ula("adult", "fat", "sad", "gif", "21_3.gif", ""),
                new Ula("adult", "fat", "sad", "gif", "22_1.gif", ""),
                new Ula("adult", "fat", "sad", "gif", "22_2.gif", ""),
                new Ula("adult", "fat", "sad", "gif", "22_3.gif", ""),
                new Ula("adult", "fat", "sad", "gif", "23_1.gif", ""),
                new Ula("adult", "fat", "sad", "gif", "23_2.gif", ""),
                new Ula("adult", "fat", "sad", "gif", "23_3.gif", ""),

                new Ula("adult", "fit", "happy", "gif", "24_1.gif", ""),
                new Ula("adult", "fit", "happy", "gif", "24_2.gif", ""),
                new Ula("adult", "fit", "happy", "gif", "24_3.gif", ""),
                new Ula("adult", "fit", "happy", "gif", "24_4.gif", ""),
                new Ula("adult", "fit", "happy", "gif", "25_1.gif", ""),
                new Ula("adult", "fit", "happy", "gif", "25_2.gif", ""),
                new Ula("adult", "fit", "happy", "gif", "25_3.gif", ""),
                new Ula("adult", "fit", "happy", "gif", "26_1.gif", ""),
                new Ula("adult", "fit", "happy", "gif", "26_2.gif", ""),
                new Ula("adult", "fit", "happy", "gif", "26_3.gif", ""),

        };
    }
}
