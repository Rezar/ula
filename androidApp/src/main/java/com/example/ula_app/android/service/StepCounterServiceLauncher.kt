package com.example.ula_app.android.service

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.ula_app.android.PermissionManager

class StepCounterServiceLauncher: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.run {
            if (
                intent?.action === Intent.ACTION_BOOT_COMPLETED &&
                PermissionManager.hasPermission(context, Manifest.permission.ACTIVITY_RECOGNITION)
            ) {
                val launchIntent = Intent(applicationContext, StepCounterService::class.java)
                context.startForegroundService(launchIntent)
            }
        }
    }
}