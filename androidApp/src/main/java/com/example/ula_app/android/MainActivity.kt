package com.example.ula_app.android

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.datastore.dataStore
import androidx.datastore.preferences.preferencesDataStore
import com.example.ula_app.android.data.UserPreferencesSerializer
import com.example.ula_app.android.repo.UserPreferencesRepository
import com.example.ula_app.android.ui.Game
import com.example.ula_app.android.ui.viewmodel.StepViewModel
import com.example.ula_app.android.ui.viewmodel.UserPreferencesViewModel

// Name of Datastore file name
private const val USER_PREFERENCES_NAME = "user_preferences"

// Create a Datastore instance and save it to the file
private val Context.dataStore by preferencesDataStore(
    name = USER_PREFERENCES_NAME
)

class MainActivity : ComponentActivity() {

    lateinit var userPreferencesViewModel: UserPreferencesViewModel
    lateinit var stepViewModel: StepViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("MainActivity", "Main Activity is created.")

        userPreferencesViewModel = UserPreferencesViewModel(UserPreferencesRepository(dataStore))
        stepViewModel = StepViewModel(UserPreferencesRepository(dataStore))

        setContent {
            // Game
            Game(userPreferencesViewModel = userPreferencesViewModel, stepViewModel = stepViewModel)
            // End for Game
        }


/*        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACTIVITY_RECOGNITION
            ) == PackageManager.PERMISSION_DENIED
        ) {
            // ask for permission
            requestPermissions(arrayOf(Manifest.permission.ACTIVITY_RECOGNITION), 0)
        }*/
    }

}












