package com.example.ula_app.android

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.content.ContextCompat
import com.example.ula_app.android.ui.Game
import android.Manifest
import androidx.core.app.ActivityCompat

/*// Name of Datastore file name
private const val USER_PREFERENCES_NAME = "user_preferences"

// Create a Datastore instance and save it to the file
val Context.dataStore by preferencesDataStore(
    name = USER_PREFERENCES_NAME
)*/

private const val TAG = "MainActivity"


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PermissionManager.askForPermission(
            this,
            Manifest.permission.ACTIVITY_RECOGNITION,
            PackageManager.PERMISSION_GRANTED,
            1
        )
        Log.i("$TAG", "Main Activity is created.")

        setContent {
            // Game
            Game()
            // End for Game
        }



    }

}












