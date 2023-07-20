package com.example.ula_app.android.repo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.ula_app.android.data.UserPreferences
import com.example.ula_app.android.ui.viewmodel.StepsWithDates
import kotlinx.coroutines.flow.first
import kotlinx.datetime.Instant

class UserPreferencesRepository(private val dataStore: DataStore<Preferences>) {

    // set preference keys in Datastore
    private object PreferencesKeys {
        val FIRST_TIME = booleanPreferencesKey("first_time")
        val FIRST_DATE_TIME = longPreferencesKey("first_date_time")

        val DISPLAY_STEPS = booleanPreferencesKey("display_steps")
        val DISPLAY_MONSTER = booleanPreferencesKey("display_monster")
        val MAX_THRESHOLD = doublePreferencesKey("max_threshold")
        val MIN_THRESHOLD = doublePreferencesKey("min_threshold")
        val EFFECTIVE_DAYS = intPreferencesKey("effective_days")
        val GOAL = intPreferencesKey("goal")

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
    * set first time
    * */
    suspend fun updateEffectiveDays(effectiveDays: Int) {
        dataStore.edit {preferences ->
            preferences[PreferencesKeys.EFFECTIVE_DAYS] = effectiveDays
        }
    }

    /*
    * set first time
    * */
    suspend fun updateGoal(goal: Int) {
        dataStore.edit {preferences ->
            preferences[PreferencesKeys.GOAL] = goal
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
        val goal = preferences[PreferencesKeys.GOAL] ?: 5000

        val stepsWithDates = preferences[PreferencesKeys.STEPS_WITH_DATE] ?: ""

        // deserialize

        return UserPreferences(firstTime,
            firstDateTime,
            displaySteps,
            displayMonster,
            maxThreshold,
            minThreshold,
            effectiveDays,
            goal,
            stepsWithDates)
    }

}