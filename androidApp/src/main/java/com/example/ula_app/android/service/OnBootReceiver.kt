package com.example.ula_app.android.service

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.ula_app.android.PermissionManager

class OnBootReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.run {
            if (
                PermissionManager.hasPermission(context, Manifest.permission.ACTIVITY_RECOGNITION)
            ) {
                // start the stepCounterService, and this is the
                val launchIntent = Intent(applicationContext, StepCounterService::class.java)
                startService(launchIntent)
            }
        }
    }
}