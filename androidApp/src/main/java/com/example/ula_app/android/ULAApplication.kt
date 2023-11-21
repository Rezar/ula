package com.example.ula_app.android

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
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

    companion object {
        var instance: ULAApplication ?= null

        var homeViewModel: AndroidHomeViewModel ?= null
        var debugViewModel: AndroidDebugViewModel ?= null
        var stepViewModel: StepViewModel ?= null
        var userPreferencesViewModel: UserPreferencesViewModel ?= null
        var userPreferencesRepository: UserPreferencesRepository ?= null

        val localDateTimeState = MutableStateFlow<LocalDateTime>(DateTimeUtil.nowInLocalDateTime())

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