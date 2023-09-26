package com.example.ula_app.android.repo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.ula_app.android.Singleton
import com.example.ula_app.android.data.UserPreferences
import kotlinx.coroutines.flow.first
import kotlinx.datetime.Instant

class UserPreferencesRepository() {
    val dataStore = Singleton.getInstance<DataStore<Preferences>>()

    // set preference keys in Datastore
    private object PreferencesKeys {
        val FIRST_TIME = booleanPreferencesKey("first_time")
        val FIRST_DATE_TIME = longPreferencesKey("first_date_time")

        val DISPLAY_STEPS = booleanPreferencesKey("display_steps")
        val DISPLAY_MONSTER = booleanPreferencesKey("display_monster")
        val MAX_THRESHOLD = doublePreferencesKey("max_threshold")
        val MIN_THRESHOLD = doublePreferencesKey("min_threshold")
        val EFFECTIVE_DAYS = intPreferencesKey("effective_days")
        val EFFECTIVE_DATE = longPreferencesKey("effective_date")
        val DAILYGOAL = intPreferencesKey("daily_goal")
        val WEEKLYGOAL = intPreferencesKey("weekly_goal")

        val STEPS_WITH_DATE = stringPreferencesKey("steps_with_date")
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
    * update steps history
    * */
    suspend fun updateStepHistory(stepsHistory: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.STEPS_WITH_DATE] = stepsHistory
        }
    }

    /*
    * set display steps
    * */
    suspend fun updateDisplaySteps(displaySteps: Boolean) {
        dataStore.edit {preferences ->
            preferences[PreferencesKeys.DISPLAY_STEPS] = displaySteps
        }
    }

    /*
    * set first time
    * */
    suspend fun updateDisplayMonster(displayMonster: Boolean) {
        dataStore.edit {preferences ->
            preferences[PreferencesKeys.DISPLAY_MONSTER] = displayMonster
        }
    }

    /*
    * set max threshold
    * */
    suspend fun updateMaxThreshold(maxThreshold: Double) {
        dataStore.edit {preferences ->
            preferences[PreferencesKeys.MAX_THRESHOLD] = maxThreshold
        }
    }

    /*
    * set min threshold
    * */
    suspend fun updateMinThreshold(minThreshold: Double) {
        dataStore.edit {preferences ->
            preferences[PreferencesKeys.MIN_THRESHOLD] = minThreshold
        }
    }

    /*
    * set effective days
    * */
    suspend fun updateEffectiveDays(effectiveDays: Int) {
        dataStore.edit {preferences ->
            preferences[PreferencesKeys.EFFECTIVE_DAYS] = effectiveDays
        }
    }

    /*
    * set effective date
    * */
    suspend fun updateEffectiveDate(effectiveDate: Instant) {
        dataStore.edit {preferences ->
            preferences[PreferencesKeys.EFFECTIVE_DATE] = effectiveDate.epochSeconds
        }
    }

    /*
    * set daily goal
    * */
    suspend fun updateDailyGoal(dailyGoal: Int) {
        dataStore.edit {preferences ->
            preferences[PreferencesKeys.DAILYGOAL] = dailyGoal
        }
    }

    /*
    * set weekly goal
    * */
    suspend fun updateWeeklyGoal(weeklyGoal: Int) {
        dataStore.edit {preferences ->
            preferences[PreferencesKeys.WEEKLYGOAL] = weeklyGoal
        }
    }

    /*
    * Load initial value to a state flow of viewModel
    * */
    suspend fun fetchInitialPreferences() = mapUserPreferences(dataStore.data.first().toPreferences())

    /*
    * Read data from dataStore and convert them into a UserPreferences instance
    * This is similar to the deserializer in the UserPreferencesSerializer file
    * */
    private fun mapUserPreferences(preferences: Preferences): UserPreferences {
        val firstTime = preferences[PreferencesKeys.FIRST_TIME] ?: true  // a ? a: b
        val firstDateTime = Instant.fromEpochSeconds(preferences[PreferencesKeys.FIRST_DATE_TIME] ?: 0)

        val displaySteps = preferences[PreferencesKeys.DISPLAY_STEPS] ?: false
        val displayMonster = preferences[PreferencesKeys.DISPLAY_MONSTER] ?: false
        val maxThreshold = preferences[PreferencesKeys.MAX_THRESHOLD] ?: .2
        val minThreshold = preferences[PreferencesKeys.MIN_THRESHOLD] ?: .2
        val effectiveDays = preferences[PreferencesKeys.EFFECTIVE_DAYS] ?: 3
        val effectiveDate = Instant.fromEpochSeconds(preferences[PreferencesKeys.EFFECTIVE_DATE] ?: 0L)
        val dailyGoal = preferences[PreferencesKeys.DAILYGOAL] ?: 5000
        val weeklyGoal = preferences[PreferencesKeys.WEEKLYGOAL] ?: 20000

        val stepsWithDates = preferences[PreferencesKeys.STEPS_WITH_DATE] ?: ""

        return UserPreferences(
            firstTime,
            firstDateTime,
            displaySteps,
            displayMonster,
            maxThreshold,
            minThreshold,
            effectiveDays,
            effectiveDate,
            dailyGoal,
            weeklyGoal,
            stepsWithDates
        )
    }

}