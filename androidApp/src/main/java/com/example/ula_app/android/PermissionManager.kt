package com.example.ula_app.android

import android.app.Activity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionManager {

    companion object {
        // Check the status of the permission and show a popup to ask for grant permission.
        fun askForPermission(activity: Activity, permission: String, permissionStatus: Int, requestCode: Int) {
            if (ContextCompat.checkSelfPermission(
                    activity,
                    permission
                ) != permissionStatus
            ) {
                // ask for permission
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(permission),
                    requestCode
                )
            }
        }
    }
}