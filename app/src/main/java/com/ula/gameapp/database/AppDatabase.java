/*
 * Created by Amin Mastani (https://github.com/mastani)
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 1/10/19 3:39 PM
 */

package com.ula.gameapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.ula.gameapp.database.dao.AppConfigDao;
import com.ula.gameapp.database.dao.FootStepDao;
import com.ula.gameapp.database.dao.AnimationDao;
import com.ula.gameapp.database.dao.PetDao;
import com.ula.gameapp.database.dao.PetStatusDao;
import com.ula.gameapp.database.dao.StepDao;
import com.ula.gameapp.item.AppConfig;
import com.ula.gameapp.item.FootStep;
import com.ula.gameapp.item.InteractionMapping;
import com.ula.gameapp.item.JAnimation;
import com.ula.gameapp.item.Pet;
import com.ula.gameapp.item.PetStatus;
import com.ula.gameapp.item.Step;
import com.ula.gameapp.utils.Converter;

@Database(entities = {JAnimation.class, PetStatus.class, Pet.class, InteractionMapping.class,
        FootStep.class, Step.class, AppConfig.class},
        version = 8, exportSchema = false)
@TypeConverters({Converter.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract AnimationDao animationDao();

    public abstract PetStatusDao petStatusDao();

    public abstract PetDao petDao();

    public abstract FootStepDao footStepDao();

    public abstract AppConfigDao appConfigDao();

    public  abstract StepDao stepDao();
}