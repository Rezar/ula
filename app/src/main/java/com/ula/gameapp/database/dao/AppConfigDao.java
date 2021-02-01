package com.ula.gameapp.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ula.gameapp.item.AppConfig;

@Dao
public interface AppConfigDao {

    @Query("SELECT * FROM AppConfig ORDER BY id DESC LIMIT 1")
    LiveData<AppConfig> getAppConfig();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void setAppConfig(AppConfig appConfig);

    @Query("SELECT * FROM AppConfig ORDER BY id DESC LIMIT 1")
    AppConfig getCurrentConfig();
}
