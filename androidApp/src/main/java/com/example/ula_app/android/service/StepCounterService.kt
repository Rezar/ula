package com.example.ula_app.android.service

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.IBinder
import android.util.Log
import com.example.ula_app.android.ULAApplication
import com.example.ula_app.android.repo.UserPreferencesRepository
import com.example.ula_app.android.data.StepsPerDay
import com.example.ula_app.util.DateTimeUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDateTime

private const val TAG = "StepCounterService"

//
class StepCounterService: Service(), SensorEventListener {

    private var userPreferencesRepository = ULAApplication.getInstance<UserPreferencesRepository>()
    // must to include this variable whenever you want to use the android sensor
    private lateinit var sensorManager: SensorManager
    //
    private var stepsPerDay: StepsPerDay = StepsPerDay()


    private val serviceScope = CoroutineScope(Dispatchers.IO) // Create a CoroutineScope with IO dispatcher

    // this is the function when you want to bind the service to the activity
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val sensor = event?.sensor

        when (sensor?.type) {
            Sensor.TYPE_STEP_COUNTER -> {
                Log.i(TAG, "Step sensor changed")

                // get the step counter for today, and update the step constantly within a day

                serviceScope.launch {
                    withContext(Dispatchers.IO) {
                        stepsPerDay = userPreferencesRepository.fetchStepsPerDay() // get steps for today
                    }
                }

                // calculate
                val sensorStepCount = event.values[0].toInt() // this is the steps count from t = 0 to n
                stepsPerDay.stepCount += sensorStepCount - stepsPerDay.preStepCount
                stepsPerDay.preStepCount = sensorStepCount

                serviceScope.launch {
                    withContext(Dispatchers.IO) {
                        userPreferencesRepository.updateStepsPerDay(stepsPerDay)  // update steps of today
                    }
                }

                /*if (DateTimeUtil.getDayDifference(stepsPerDay.date) == 0L) {
                    serviceScope.launch {
                        withContext(Dispatchers.IO) {
                            userPreferencesRepository.updateStepsPerDay(stepsPerDay)
                        }
                    }
                } else {
                    val newDay = StepsWithDate(DateTimeUtil.nowInInstant().epochSeconds, -1)
                    val updatedStepsHistory = mutableListOf<StepsWithDate>()

                    serviceScope.launch {
                        withContext(Dispatchers.IO) {
                            updatedStepsHistory.addAll(userPreferencesRepository.fetchStepsHistory())
                        }
                    }
                    updatedStepsHistory.add(stepsPerDay)

                    serviceScope.launch {
                        withContext(Dispatchers.IO) {
                            userPreferencesRepository.updateStepsPerDay(newDay)
                            userPreferencesRepository.updateStepsHistory(updatedStepsHistory)
                        }
                    }
                }*/


            }
            else -> {
                Log.e(TAG, "Sensor is not supported")
            }
        }
    }

    // Should override this function if you implement SensorEventListener
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.i(TAG, sensor?.name + " accuracy changed: " + accuracy)
    }

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "Sensor Service is created.")

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager //instantiate a SensorManager object
        val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) // find the stepCounter sensor in the sensor list
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL) // monitor the sensor constantly in a slower rate
    }

    // this is the job that you want to do when the instance of this service is destroyed
    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(this)  // do not monitor the sensor if the service is destroyed
    }

    // this is the job that you want to do when the service is unbinded or stopped
    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
    }

    // when the stepCounterService is started, the tasks in this functions will started
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(TAG, "Step Detector Service is Started")
        return START_STICKY
    }


}