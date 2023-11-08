package com.example.ula_app.android.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ula_app.android.Singleton
import com.example.ula_app.android.repo.UserPreferencesRepository
import com.example.ula_app.data.DataSource
import com.example.ula_app.data.dataclass.StepsWithDate
import com.example.ula_app.util.DateTimeUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant

private const val TAG = "StepViewModel"

data class UserState(
    val stepsPerDay: StepsWithDate = StepsWithDate(0L, 0),
    val stepsHistory: List<StepsWithDate> = emptyList(),
)

class StepViewModel(): ViewModel() {

    private val userPreferencesRepository = Singleton.getInstance<UserPreferencesRepository>()

    private val _userState = MutableStateFlow(UserState())
    val userState: StateFlow<UserState> = _userState.asStateFlow()

    init {
        init()
    }

    private fun init() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            val dataStoreObj = UserState(
                userPreferencesRepository.fetchStepsPerDay(),
                userPreferencesRepository.fetchStepsHistory()
            )

            _userState.value = _userState.value.copy(
                stepsPerDay = dataStoreObj.stepsPerDay,
                stepsHistory = mockStepsHistoryIfNeeded(dataStoreObj.stepsHistory)
//                stepsHistory = dataStoreObj.stepsHistory
            )
        }
    }

    fun mockStepsHistoryIfNeeded(stepsHistory: List<StepsWithDate>): List<StepsWithDate> {
        val updatedStepsHistory = mutableListOf<StepsWithDate>()

        // TODO: prepare stepHistoryList if 0 <= stepHistory.length < 7
        if (_userState.value.stepsHistory.size in 0..6) {
            val nowInstant = if (_userState.value.stepsHistory.isEmpty()) {
                DateTimeUtil.nowInInstant()
            } else {
                Instant.fromEpochSeconds(_userState.value.stepsHistory[0].date)
            }

            var mockDays = 7 - _userState.value.stepsHistory.size
            while (mockDays-- > 0) {
                updatedStepsHistory.add(
                    StepsWithDate(
                        DateTimeUtil.nowInstantShift(
                            nowInstant,
                            mockDays - 8,
                            DateTimeUnit.DAY
                        ).epochSeconds,
                        0
                    )
                )
            }
        } else {
            updatedStepsHistory.addAll(_userState.value.stepsHistory)
        }

        Log.i(TAG, "length is ${updatedStepsHistory.size}")

        return updatedStepsHistory
    }

    // Stats sample data
    val stepHistory = listOf(
        StepsWithDate(
//            date = Instant.parse("2023-07-11T23:59:44.475Z").epochSeconds,
            date = Instant.parse("2023-07-11T23:59:44.475Z").epochSeconds,
            steps = 3000
        ),
        StepsWithDate(
            date = Instant.parse("2023-07-12T23:59:44.475Z").epochSeconds,
            steps = 4890
        ),
        StepsWithDate(
            date = Instant.parse("2023-07-13T23:59:44.475Z").epochSeconds,
            steps = 9987
        ),
        StepsWithDate(
            date = Instant.parse("2023-07-14T23:59:44.475Z").epochSeconds,
            steps = 15000
        ),
        StepsWithDate(
            date = Instant.parse("2023-07-15T23:59:44.475Z").epochSeconds,
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


//    init {
//        init()
//    }

    /*
    * Read stepsHistory from datastore and update stateflow value:
    *   - read from datastore
    *   - deserialize the string to list
    * This will update whenever you reopen the app.
    * */
//    private fun init() = viewModelScope.launch {
//        withContext(Dispatchers.IO) {
//            try{
//                // Read the stepHistory string
//                val datastoreObj = userPreferencesRepository.fetchInitialPreferences()
//
//                var stepsList = stepHistory   //TODO: When the app is working, change it to emptyList()
//
////                // deserialize to list
////                stepsList = Json.decodeFromString<List<StepsWithDates>>(datastoreObj.stepsHistory)
//
//                // update to the state
//                _userState.update{
//                    it.copy(
//                        data = stepsList
//                    )
//                }
//            } catch(e: Exception) {
//                Log.e("$TAG", "Error deserialize step histories.")
//            }
//        }
//    }


    /*
    * update stepHistory and save it in the state flow
    * param: the new stepHistory list that updated from UI
    * return:
    *
    * This will update the datastore and also the state, so if you never close the app, it will
    * only read the stepHistory from state, which will always contains newest stepHistory
    * */
//    fun setStepHistory(stepsHistory: List<StepsWithDate>) = viewModelScope.launch {
//        withContext(Dispatchers.IO) {
//            // update stepList state
//            _userState.update{
//                it.copy(
//                    data = stepsHistory
//                )
//            }
//
//            // update datastore
//            // serializing the list
//            val stepHistoryJson = Json.encodeToString(stepsHistory)
//
//            // save it to datastore
//            userPreferencesRepository.updateStepHistory(stepHistoryJson)
//        }
//    }
}