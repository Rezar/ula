package com.example.ula_app.android.repo

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.ula_app.android.ULAApplication
import com.example.ula_app.android.data.UserPreferences
import com.example.ula_app.android.data.StepsPerDay
import com.example.ula_app.android.data.StepsWithDate
import com.example.ula_app.util.DateTimeUtil
import kotlinx.coroutines.flow.first
import kotlinx.datetime.Instant
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

private const val TAG = "UserPreferencesRepository"

class UserPreferencesRepository() {
    private val dataStore = ULAApplication.getInstance<DataStore<Preferences>>()

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
        val DAILY_GOAL = intPreferencesKey("daily_goal")
        val WEEKLY_GOAL = intPreferencesKey("weekly_goal")

        val STEPS_PER_DAY = stringPreferencesKey("steps_per_day")
        val STEPS_HISTORY = stringPreferencesKey("steps_history")
    }


    /*
    * set first time
    * */
    suspend fun updateFirstTime(firstTime: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.FIRST_TIME] = firstTime
        }
    }

    /*
    * set first date time
    * */
    suspend fun updateFirstDateTime(firstDateTime: Instant) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.FIRST_DATE_TIME] = firstDateTime.epochSeconds

        }
    }

    /*
    * update steps history
    * */
    suspend fun updateStepHistory(stepsHistory: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.STEPS_HISTORY] = stepsHistory
        }
    }

    /*
    * set display steps
    * */
    suspend fun updateDisplaySteps(displaySteps: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.DISPLAY_STEPS] = displaySteps
        }
    }

    /*
    * set first time
    * */
    suspend fun updateDisplayMonster(displayMonster: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.DISPLAY_MONSTER] = displayMonster
        }
    }

    /*
    * set max threshold
    * */
    suspend fun updateMaxThreshold(maxThreshold: Double) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.MAX_THRESHOLD] = maxThreshold
        }
    }

    /*
    * set min threshold
    * */
    suspend fun updateMinThreshold(minThreshold: Double) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.MIN_THRESHOLD] = minThreshold
        }
    }

    /*
    * set effective days
    * */
    suspend fun updateEffectiveDays(effectiveDays: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.EFFECTIVE_DAYS] = effectiveDays
        }
    }

    /*
    * set effective date
    * */
    suspend fun updateEffectiveDate(effectiveDate: Instant) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.EFFECTIVE_DATE] = effectiveDate.epochSeconds
        }
    }

    /*
    * set daily goal
    * */
    suspend fun updateDailyGoal(dailyGoal: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.DAILY_GOAL] = dailyGoal
        }
    }

    /*
    * set weekly goal
    * */
    suspend fun updateWeeklyGoal(weeklyGoal: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.WEEKLY_GOAL] = weeklyGoal
        }
    }

    /*
    * set steps per day
    * */
    suspend fun updateStepsPerDay(stepsPerDay: StepsPerDay) {
        val data = Json.encodeToString(stepsPerDay)

        dataStore.edit { preferences ->
            preferences[PreferencesKeys.STEPS_PER_DAY] = data
        }
    }

    suspend fun updateStepsHistory(stepsHistory: List<StepsWithDate>) {
        val data = Json.encodeToString(stepsHistory)

        dataStore.edit { preferences ->
            preferences[PreferencesKeys.STEPS_HISTORY] = data
        }
    }

    suspend fun saveStepPerDayToStepHistoryAndReset() {
        Log.i(TAG, "Saving started")
        val preferences: Preferences = dataStore.data.first().toPreferences()
        val stepsPerDay = fetchStepsPerDay()

        val updatedStepsHistory = mutableListOf<StepsWithDate>()

        updatedStepsHistory.addAll(fetchStepsHistory())

        if (updatedStepsHistory.size >= 7) {
            updatedStepsHistory.removeFirst()
        }

        updatedStepsHistory.add(StepsWithDate(DateTimeUtil.nowInInstant().epochSeconds, stepsPerDay.stepCount))
        updateStepsHistory(updatedStepsHistory)
        // not sure reset stepsPerDay is needed.
    }

    /*
    * Load initial value to a state flow of viewModel
    * Read data from dataStore and convert them into a UserPreferences instance
    * This is similar to the deserializer in the UserPreferencesSerializer file
    * */
    suspend fun fetchInitialPreferences(): UserPreferences {
        val preferences: Preferences = dataStore.data.first().toPreferences()

        val firstTime = preferences[PreferencesKeys.FIRST_TIME] ?: true  // a ? a: b
        val firstDateTime = Instant.fromEpochSeconds(preferences[PreferencesKeys.FIRST_DATE_TIME] ?: 0L)

        val displaySteps = preferences[PreferencesKeys.DISPLAY_STEPS] ?: false
        val displayMonster = preferences[PreferencesKeys.DISPLAY_MONSTER] ?: false
        val maxThreshold = preferences[PreferencesKeys.MAX_THRESHOLD] ?: .2
        val minThreshold = preferences[PreferencesKeys.MIN_THRESHOLD] ?: .2
        val effectiveDays = preferences[PreferencesKeys.EFFECTIVE_DAYS] ?: 3
        val effectiveDate = Instant.fromEpochSeconds(preferences[PreferencesKeys.EFFECTIVE_DATE] ?: 0L)
        val dailyGoal = preferences[PreferencesKeys.DAILY_GOAL] ?: 5000
        val weeklyGoal = preferences[PreferencesKeys.WEEKLY_GOAL] ?: 20000

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
        )
    }

    suspend fun fetchStepsPerDay(): StepsPerDay {
        val preferences: Preferences = dataStore.data.first().toPreferences()
        val data: String = preferences[PreferencesKeys.STEPS_PER_DAY] ?: return StepsPerDay(0, 0)

        try {
            return Json.decodeFromString<StepsPerDay>(data)
        } catch (e: Exception) {
            Log.e(TAG, "decode steps per day from string failed.")
            return StepsPerDay(0, 0)
        }
    }

    suspend fun fetchStepsHistory(): List<StepsWithDate> {
        val preferences: Preferences = dataStore.data.first().toPreferences()
        val data: String = preferences[PreferencesKeys.STEPS_HISTORY] ?: return emptyList<StepsWithDate>()

        try {
            return Json.decodeFromString<List<StepsWithDate>>(data)
        } catch (e: Exception) {
            Log.i(TAG, "decode step history from string failed.")
            return emptyList<StepsWithDate>()
        }
    }

}