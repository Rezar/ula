package com.example.ula_app.android.repo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import com.example.ula_app.android.data.UserPreferences
import kotlinx.coroutines.flow.first
import kotlinx.datetime.Instant

class UserPreferencesRepository(private val dataStore: DataStore<Preferences>) {

    // set preference keys in Datastore
    private object PreferencesKeys {
        val FIRST_TIME = booleanPreferencesKey("first_time")
        val FIRST_DATE_TIME = longPreferencesKey("first_date_time")
    }


    /*
    * set first time
    * */
    suspend fun updateFirstTime(firstTime: Boolean) {
        dataStore.edit {preferences ->
            preferences[PreferencesKeys.FIRST_TIME] = firstTime
        }

    }

    /*
    * set first date time
    * */
    suspend fun updateFirstDateTime(firstDateTime: Instant) {
        dataStore.edit {preferences ->
            preferences[PreferencesKeys.FIRST_DATE_TIME] = firstDateTime.epochSeconds

        }
    }

    /*
    * Load initial value to a state flow of viewModel
    * */
    suspend fun fetchInitialPreferences() = mapUserPreferences(dataStore.data.first().toPreferences())

    /*
    * Read data from dataStore and convert them into a UserPreferences instance
    * */
    private fun mapUserPreferences(preferences: Preferences): UserPreferences {
        val firstTime = preferences[PreferencesKeys.FIRST_TIME] ?: true  // a ? a: b
        val firstDateTime = Instant.fromEpochSeconds(preferences[PreferencesKeys.FIRST_DATE_TIME] ?: 0)

        return UserPreferences(firstTime, firstDateTime)
    }

}