package com.example.ula_app.android

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import com.example.ula_app.android.data.StepsPerDay
import com.example.ula_app.android.repo.UserPreferencesRepository
import com.example.ula_app.android.ui.viewmodel.AndroidDebugViewModel
import com.example.ula_app.android.ui.viewmodel.AndroidHomeViewModel
import com.example.ula_app.android.ui.viewmodel.StepViewModel
import com.example.ula_app.android.ui.viewmodel.UserPreferencesViewModel
import com.example.ula_app.util.DateTimeUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDateTime

// Name of Datastore file name
private const val USER_PREFERENCES_NAME = "user_preferences"

// Create a Datastore instance and save it to the file
val Context.dataStore by preferencesDataStore(
    name = USER_PREFERENCES_NAME
)

class ULAApplication: Application() {

    init {
        instance = this
    }

    val applicationScope = CoroutineScope(Dispatchers.IO)

    // current time
    var localDateTimeState = MutableStateFlow<LocalDateTime>(DateTimeUtil.nowInLocalDateTime())

    override fun onCreate() {
        super.onCreate()
        registerMidnightTimer() //call the function here in the onCreate function so that it can actually start checking the time
    }

    // constantly checking if the time is 23:55
    private fun registerMidnightTimer() {
        val intentFilter = IntentFilter().apply {
            addAction(Intent.ACTION_TIME_TICK)
            addAction(Intent.ACTION_TIME_CHANGED)
            addAction(Intent.ACTION_TIMEZONE_CHANGED)
        }
        registerReceiver(midnightBroadcastReceiver, intentFilter)
    }

    // record today's step and date
    private val midnightBroadcastReceiver = object : BroadcastReceiver() {

        // if the conditions in the "registerMidnightTimer" is fulfilled,
        // then use the broadcast receiver to do the job below
        override fun onReceive(context: Context?, intent: Intent?) {
            val today = DateTimeUtil.nowInLocalDateTime()
            if (today.date != localDateTimeState.value.date) {
                localDateTimeState.value = today
            } else if (isSavingTime(today)) {
                applicationScope.launch {
                    withContext(Dispatchers.IO) {
                        userPreferencesRepository?.saveStepPerDayToStepHistoryAndReset()
                    }
                }
            }
        }
    }

    // check if the time is 23:55
    private fun isSavingTime(now: LocalDateTime): Boolean {
        if (
            now.time.hour == 23 &&
            now.time.minute == 55 &&
            now.time.second == 0
        ) {
            return true
        }

        return false
    }

    companion object {
        var instance: ULAApplication ?= null

        var homeViewModel: AndroidHomeViewModel ?= null
        var debugViewModel: AndroidDebugViewModel ?= null
        var stepViewModel: StepViewModel ?= null
        var userPreferencesViewModel: UserPreferencesViewModel ?= null
        var userPreferencesRepository: UserPreferencesRepository ?= null

        inline fun <reified T>getInstance(): T {

            val context = instance!!.applicationContext
            when (T::class) {
                // get the only instance of DataStore in the scope of application context.
                DataStore::class -> {
                    return context.dataStore as T
                }
                // get the only instance of UserPreferencesRepository in the scope of application context.
                UserPreferencesRepository::class -> {
                    if (userPreferencesRepository == null) {
                        userPreferencesRepository = UserPreferencesRepository()
                    }

                    return userPreferencesRepository as T
                }
                // get the only instance of AndroidHomeViewModel in the scope of application context.
                AndroidHomeViewModel::class -> {
                    if (homeViewModel == null) {
                        homeViewModel = AndroidHomeViewModel()
                    }

                    return homeViewModel as T
                }
                // get the only instance of AndroidDebugViewModel in the scope of application context.
                AndroidDebugViewModel::class -> {
                    if (debugViewModel == null) {
                        debugViewModel = AndroidDebugViewModel()
                    }

                    return debugViewModel as T
                }
                // get the only instance of StepViewModel in the scope of application context.
                StepViewModel::class -> {
                    if (stepViewModel == null) {
                        stepViewModel = StepViewModel()
                    }

                    return stepViewModel as T
                }
                // get the only instance of UserPreferencesViewModel in the scope of application context.
                UserPreferencesViewModel::class -> {
                    if (userPreferencesViewModel == null) {
                        userPreferencesViewModel = UserPreferencesViewModel()
                    }

                    return userPreferencesViewModel as T
                }
                else -> {
                    throw Exception("Unknown type")
                }
            }
        }
    }
}