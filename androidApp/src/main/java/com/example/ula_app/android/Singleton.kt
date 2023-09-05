package com.example.ula_app.android

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.ula_app.android.repo.UserPreferencesRepository
import com.example.ula_app.android.ui.viewmodel.AndroidDebugViewModel
import com.example.ula_app.android.ui.viewmodel.AndroidHomeViewModel
import com.example.ula_app.android.ui.viewmodel.StepViewModel
import com.example.ula_app.android.ui.viewmodel.UserPreferencesViewModel

// Name of Datastore file name
private const val USER_PREFERENCES_NAME = "user_preferences"

// Create a Datastore instance and save it to the file
val Context.dataStore by preferencesDataStore(
    name = USER_PREFERENCES_NAME
)

class Singleton {
    companion object {
        var homeViewModel: AndroidHomeViewModel ?= null
        var debugViewModel: AndroidDebugViewModel ?= null
        var stepViewModel: StepViewModel ?= null
        var userPreferencesViewModel: UserPreferencesViewModel ?= null
        var userPreferencesRepository: UserPreferencesRepository ?= null

        inline fun <reified T>getInstance(context: Context): T {
            when (T::class) {
                // get the only instance of DataStore in the scope of application context.
                DataStore::class -> {
                    return context.dataStore as T
                }
                // get the only instance of UserPreferencesRepository in the scope of application context.
                UserPreferencesRepository::class -> {
                    if (userPreferencesRepository == null) {
                        userPreferencesRepository = UserPreferencesRepository(context)
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
                        stepViewModel = StepViewModel(context)
                    }

                    return stepViewModel as T
                }
                // get the only instance of UserPreferencesViewModel in the scope of application context.
                UserPreferencesViewModel::class -> {
                    if (userPreferencesViewModel == null) {
                        userPreferencesViewModel = UserPreferencesViewModel(context)
                    }

                    return userPreferencesViewModel as T
                }
                else -> {
                    throw Exception("Unknown type")
                }
            }
        }

        // get the only instance of DataStore in the scope of application context.
        fun getDataStore(context: Context): DataStore<Preferences> {
            return context.dataStore
        }

        // get the only instance of UserPreferencesRepository in the scope of application context.
        fun getRepository(context: Context): UserPreferencesRepository {
            if (userPreferencesRepository == null) {
                userPreferencesRepository = UserPreferencesRepository(context)
            }

            return userPreferencesRepository as UserPreferencesRepository
        }

        // get the only instance of AndroidHomeViewModel in the scope of application context.
        fun getHomeVM(): AndroidHomeViewModel {
            if (homeViewModel == null) {
                homeViewModel = AndroidHomeViewModel()
            }

            return homeViewModel as AndroidHomeViewModel
        }

        // get the only instance of AndroidDebugViewModel in the scope of application context.
        fun getDebugVM(): AndroidDebugViewModel {
            if (debugViewModel == null) {
                debugViewModel = AndroidDebugViewModel()
            }

            return debugViewModel as AndroidDebugViewModel
        }

        // get the only instance of StepViewModel in the scope of application context.
        fun getStepVM(context: Context): StepViewModel {
            if (stepViewModel == null) {
                stepViewModel = StepViewModel(context)
            }

            return stepViewModel as StepViewModel
        }

        // get the only instance of UserPreferencesViewModel in the scope of application context.
        fun getUserPreferencesVM(context: Context): UserPreferencesViewModel {
            if (userPreferencesViewModel == null) {
                userPreferencesViewModel = UserPreferencesViewModel(context)
            }

            return userPreferencesViewModel as UserPreferencesViewModel
        }
    }
}