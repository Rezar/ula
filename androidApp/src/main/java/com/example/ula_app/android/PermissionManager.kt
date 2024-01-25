package com.example.ula_app.android

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionManager {

    companion object {
        // Check the status of the permission and show a popup to ask for grant permission.
        fun askForPermission(context: Context, permission: String, permissionStatus: Int, requestCode: Int) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    permission
                ) != permissionStatus
            ) {
                // ask for permission
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(permission),
                    requestCode
                )
            }
        }

        fun hasPermission(context: Context, permission: String): Boolean {
            return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
        }
    }
}