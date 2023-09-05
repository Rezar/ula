package com.example.ula_app.android.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.ula_app.android.Singleton
import com.example.ula_app.android.repo.UserPreferencesRepository
import com.example.ula_app.android.workers.SaveStepsWorker
import com.example.ula_app.data.DataSource
import com.example.ula_app.util.DateTimeUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Instant
import kotlinx.serialization.json.Json
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import java.util.concurrent.TimeUnit

private const val TAG = "StepViewModel"

@Serializable
data class StepsWithDates(
    val date: Instant,
    val steps: Int
)

data class UserState(
    val data: List<StepsWithDates> = emptyList()
)

class StepViewModel(
    context: Context
): ViewModel() {

    private val userPreferencesRepository = Singleton.getInstance<UserPreferencesRepository>(context)

    private val _userState = MutableStateFlow(UserState())
    val userState: StateFlow<UserState> = _userState.asStateFlow()

    // Stats sample data
    val stepHistory = listOf(
        StepsWithDates(
//            date = Instant.parse("2023-07-11T23:59:44.475Z").epochSeconds,
            date = Instant.parse("2023-07-11T23:59:44.475Z"),
            steps = 3000
        ),
        StepsWithDates(
            date = Instant.parse("2023-07-12T23:59:44.475Z"),
            steps = 4890
        ),
        StepsWithDates(
            date = Instant.parse("2023-07-13T23:59:44.475Z"),
            steps = 9987
        ),
        StepsWithDates(
            date = Instant.parse("2023-07-14T23:59:44.475Z"),
            steps = 15000
        ),
        StepsWithDates(
            date = Instant.parse("2023-07-15T23:59:44.475Z"),
            steps = 24000
        )
    )


    /*
    * select the monster in the progress bar that shows each day's fitness activity record
    * param: the step count of a particular day
    * return: index of the monster
    * */
    fun getMonsterProgress(
        goal: Int,
        currentSteps: Int
    ): Int {
        when{
            currentSteps < goal * DataSource.progressBarThreshold[0] -> {
                return 0
            }

            currentSteps < goal * DataSource.progressBarThreshold[1]
                    && currentSteps >= goal * DataSource.progressBarThreshold[0] -> {
                return 1
            }

            currentSteps < goal * DataSource.progressBarThreshold[2]
                    && currentSteps >= goal * DataSource.progressBarThreshold[1] -> {
                return 2
            }

            currentSteps < goal * DataSource.progressBarThreshold[3]
                    && currentSteps >= goal * DataSource.progressBarThreshold[2] -> {
                return 3
            }

            currentSteps < goal * DataSource.progressBarThreshold[4]
                    && currentSteps >= goal * DataSource.progressBarThreshold[3] -> {
                return 4
            }

            currentSteps >= goal * DataSource.progressBarThreshold[4] -> {
                return 5
            }
            else ->{
                return 0
            }
        }
    }


    init {
        init()
    }

    /*
    * Read stepsHistory from datastore and update stateflow value:
    *   - read from datastore
    *   - deserialize the string to list
    * This will update whenever you reopen the app.
    * */
    private fun init() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            try{
                // Read the stepHistory string
                val datastoreObj = userPreferencesRepository.fetchInitialPreferences()

                var stepsList = stepHistory   //TODO: When the app is working, change it to emptyList()

//                // deserialize to list
//                stepsList = Json.decodeFromString<List<StepsWithDates>>(datastoreObj.stepsHistory)

                // update to the state
                _userState.update{
                    it.copy(
                        data = stepsList
                    )
                }
            } catch(e: Exception) {
                Log.e("$TAG", "Error deserialize step histories.")
            }
        }
    }


    /*
    * update stepHistory and save it in the state flow
    * param: the new stepHistory list that updated from UI
    * return:
    *
    * This will update the datastore and also the state, so if you never close the app, it will
    * only read the stepHistory from state, which will always contains newest stepHistory
    * */
    fun setStepHistory(stepsHistory: List<StepsWithDates>) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            // update stepList state
            _userState.update{
                it.copy(
                    data = stepsHistory
                )
            }

            // update datastore
            // serializing the list
            val stepHistoryJson = Json.encodeToString(stepsHistory)

            // save it to datastore
            userPreferencesRepository.updateStepHistory(stepHistoryJson)
        }
    }

    fun saveSteps(context: Context, steps: Int) {
        val workManager = WorkManager.getInstance(context)

        val stringifiedData = Json.encodeToString(stepHistory)
        Log.i("$TAG", "data: $stringifiedData")

        val data = Data.Builder()
        data.putString("stepdata", steps.toString())
        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(SaveStepsWorker::class.java).setInputData(data.build()).setInitialDelay(10, TimeUnit.SECONDS).build()

        workManager.enqueue(oneTimeWorkRequest)
    }

}