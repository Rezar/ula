package com.example.ula_app.android.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.ula_app.android.Singleton
import com.example.ula_app.android.repo.UserPreferencesRepository
import java.io.IOException

private const val TAG = "SaveStepsWorker"

class SaveStepsWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {

    var stepCounts: Float = -1F

    override suspend fun doWork(): Result {
        Log.i("$TAG", "This is a test.")

        val stepData = inputData.getString("stepdata") ?: return Result.failure()

        Log.e("$TAG", "Steps: $stepData")

        try {
            val repository = Singleton.getInstance<UserPreferencesRepository>(applicationContext)
            repository.updateStepHistory(stepData)
            return Result.success()
        } catch (e: IOException) {
            return Result.failure()
        }
    }
}