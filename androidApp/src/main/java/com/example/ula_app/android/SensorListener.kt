package com.example.ula_app.android

import android.app.Service
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.ula_app.android.repo.UserPreferencesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SensorListener: Service(), SensorEventListener {

    private var userPreferencesRepository = Singleton.getInstance<UserPreferencesRepository>()
    var curStepCount: Long = 0

    private val serviceJob = Job() // Create a Job for the service
    private val serviceScope = CoroutineScope(Dispatchers.IO + serviceJob) // Create a CoroutineScope with IO dispatcher

    init {
        init()
    }

    private fun init() = serviceScope.launch {
        withContext(Dispatchers.IO) {
            val datastoreObj = userPreferencesRepository.fetchInitialPreferences()
            curStepCount = datastoreObj.curStepCount
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val sensor = event?.sensor

        if (sensor?.type == Sensor.TYPE_STEP_COUNTER) {
            curStepCount++
            serviceScope.launch {
                withContext(Dispatchers.IO) {
                    userPreferencesRepository.updateCurrentStepCount(curStepCount)
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.i("SensorListener", sensor?.name + " accuracy changed: " + accuracy)
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
    }
}