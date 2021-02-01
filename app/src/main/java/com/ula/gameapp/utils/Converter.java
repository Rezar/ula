package com.ula.gameapp.utils;

import androidx.room.TypeConverter;

import com.ula.gameapp.utils.enums.Age;
import com.ula.gameapp.utils.enums.BodyShape;
import com.ula.gameapp.utils.enums.EmotionType;
import com.ula.gameapp.utils.enums.FileType;
import com.ula.gameapp.utils.enums.InteractionType;

import java.util.Date;


public class Converter {
    @TypeConverter
    public static Age ageFromValue(Integer value){
        Age age = null;
        switch (value){
            case 1:
                age = Age.EGG;
                break;
            case 2:
                age = Age.CHILD;
                break;
            case 3:
                age = Age.ADULT;
                break;
        }

        return age;
    }

    @TypeConverter
    public static Age ageFromName(String name){

        Age age = null;
        if (name.equalsIgnoreCase(Age.EGG.name())) {
            age = Age.EGG;
        } else if (name.equalsIgnoreCase(Age.CHILD.name())) {
            age = Age.CHILD;
        } else if (name.equalsIgnoreCase(Age.ADULT.name())) {
            age = Age.ADULT;
        }

        return age;
    }

    @TypeConverter
    public static Integer ageToValue(Age age){

        return age.getValue();
    }

    @TypeConverter
    public static BodyShape bodyShapeFromValue(Integer value){
        BodyShape bodyShape = null;
        switch (value){
            case 1:
                bodyShape = BodyShape.NORMAL;
                break;
            case 2:
                bodyShape = BodyShape.FIT;
                break;
            case 3:
                bodyShape = BodyShape.FAT;
                break;
            case 4:
                bodyShape = BodyShape.OVER_WEIGHT;
                break;
            default:
                bodyShape = BodyShape.NONE;
        }

        return bodyShape;
    }

    @TypeConverter
    public static BodyShape bodyShapeFromName(String name){

        BodyShape bodyShape = null;
        if (name.equalsIgnoreCase(BodyShape.NORMAL.name())) {
            bodyShape = BodyShape.NORMAL;
        } else if (name.equalsIgnoreCase(BodyShape.FIT.name())) {
            bodyShape = BodyShape.FIT;
        } else if (name.equalsIgnoreCase(BodyShape.FAT.name())) {
            bodyShape = BodyShape.FAT;
        } else if (name.equalsIgnoreCase(BodyShape.OVER_WEIGHT.name())) {
            bodyShape = BodyShape.OVER_WEIGHT;
        } else {
            bodyShape = BodyShape.NONE;
        }

        return bodyShape;
    }

    @TypeConverter
    public static Integer bodyShapeToValue(BodyShape bodyShape){

        return bodyShape.getValue();
    }

    @TypeConverter
    public static FileType fileTypeFromValue(Integer value){
        FileType fileType = null;
        switch (value){
            case 1:
                fileType = FileType.GIF;
                break;
            case 2:
                fileType = FileType.IMAGE;
                break;
            case 3:
                fileType = FileType.MOVIE;
                break;
        }

        return fileType;
    }

    @TypeConverter
    public static Integer fileTypeToValue(FileType fileType){

        return fileType.getValue();
    }

    @TypeConverter
    public static InteractionType fileInteractionFromValue(Integer value){
        InteractionType interaction = null;
        if (value == 1) {
            interaction = InteractionType.CLICK;
        }

        return interaction;
    }

    @TypeConverter
    public static Integer interactionToValue(InteractionType interaction){

        return interaction.getValue();
    }

    @TypeConverter
    public static EmotionType emotionTypeFromValue(Integer value){
        EmotionType emotionType = null;
        switch(value) {
            case 1:
                emotionType = EmotionType.OK;
                break;
            case 2:
                emotionType = EmotionType.SAD;
                break;
            case 3:
                emotionType = EmotionType.HAPPY;
                break;
            default:
                emotionType = EmotionType.NONE;
        }

        return emotionType;
    }

    @TypeConverter
    public static Integer emotionTypeToValue(EmotionType emotionType){

        return emotionType.getValue();
    }

    @TypeConverter
    public static Date dateFromValue(long value){

        return new Date(value);
    }

    @TypeConverter
    public static long dateToLong(Date date){

        return date.getTime();
    }
}
