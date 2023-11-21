package com.example.ula_app.android.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ula_app.android.ULAApplication
import com.example.ula_app.android.data.UserPreferences
import com.example.ula_app.android.repo.UserPreferencesRepository
import com.example.ula_app.util.DateTimeUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.datetime.Instant

class UserPreferencesViewModel(): ViewModel() {

    private val userPreferencesRepository = ULAApplication.getInstance<UserPreferencesRepository>()

    private val _userPreferencesState = MutableStateFlow(UserPreferences())
    val userPreferencesState: StateFlow<UserPreferences> = _userPreferencesState.asStateFlow()

    init {
        init()
    }

    /*
    * Read firstTime, firstDateTime, displaySteps, displayMonster, maxThreshold, minThreshold, effectiveDays, effectiveDate, and goal from Datastore and update stateflow value
    * */
    private fun init() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            val datastoreObj = userPreferencesRepository.fetchInitialPreferences()
            _userPreferencesState.value = _userPreferencesState.value.copy(
                firstTime = datastoreObj.firstTime,
                firstDateTime = datastoreObj.firstDateTime,
                displaySteps = datastoreObj.displaySteps,
                displayMonster = datastoreObj.displayMonster,
                maxThreshold = datastoreObj.maxThreshold,
                minThreshold = datastoreObj.minThreshold,
                effectiveDays = datastoreObj.effectiveDays,
                effectiveDate = datastoreObj.effectiveDate,
                dailyGoal = datastoreObj.dailyGoal,
                weeklyGoal = datastoreObj.weeklyGoal
            )
        }
    }

    fun isEffective(effectiveDays: Int): Boolean {
        return DateTimeUtil.getDayDifference(
            DateTimeUtil.nowInInstant(),
            userPreferencesState.value.effectiveDate
        ) >= userPreferencesState.value.effectiveDays
    }

    /*
    * set firstTime and save it in the state flow
    * */
    fun setFirstTime(firstTime: Boolean) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            userPreferencesRepository.updateFirstTime(firstTime)
        }
        _userPreferencesState.update { currentState ->
            currentState.copy(
                firstTime = firstTime
            )
        }
    }

    /*
    * set firstDateTime and save it in the state flow
    * */
    fun setFirstDateTime(firstDateTime: Instant) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            userPreferencesRepository.updateFirstDateTime(firstDateTime)
        }
        _userPreferencesState.update { currentState ->
            currentState.copy(
                firstDateTime = firstDateTime
            )
        }
    }

    /*
    * set displaySteps and save it in the state flow
    * */
    fun setDisplaySteps(displaySteps: Boolean) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            userPreferencesRepository.updateDisplaySteps(displaySteps)
        }
        _userPreferencesState.update { currentState ->
            currentState.copy(
                displaySteps = displaySteps
            )
        }
    }

    /*
    * set displayMonster and save it in the state flow
    * */
    fun setDisplayMonster(displayMonster: Boolean) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            userPreferencesRepository.updateDisplayMonster(displayMonster)
        }
        _userPreferencesState.update { currentState ->
            currentState.copy(
                displayMonster = displayMonster
            )
        }
    }

    /*
    * set maxThreshold and save it in the state flow
    * */
    fun setMaxThreshold(maxThreshold: Double) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            userPreferencesRepository.updateMaxThreshold(maxThreshold)
        }
        _userPreferencesState.update { currentState ->
            currentState.copy(
                maxThreshold = maxThreshold
            )
        }
    }

    /*
    * set minThreshold and save it in the state flow
    * */
    fun setMinThreshold(minThreshold: Double) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            userPreferencesRepository.updateMinThreshold(minThreshold)
        }
        _userPreferencesState.update { currentState ->
            currentState.copy(
                minThreshold = minThreshold
            )
        }
    }

    /*
    * set firstDateTime and save it in the state flow
    * */
    fun setEffectiveDays(effectiveDays: Int) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            userPreferencesRepository.updateEffectiveDays(effectiveDays)
        }
        _userPreferencesState.update { currentState ->
            currentState.copy(
                effectiveDays = effectiveDays
            )
        }
    }

    /*
    * set effectiveDate and save it in the state flow
    * */
    fun setEffectiveDate(effectiveDate: Instant) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            userPreferencesRepository.updateEffectiveDate(effectiveDate)
        }
        _userPreferencesState.update { currentState ->
            currentState.copy(
                effectiveDate = effectiveDate
            )
        }
    }

    /*
    * set daily goal and save it in the state flow
    * */
    fun setDailyGoal(dailyGoal: Int) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            userPreferencesRepository.updateDailyGoal(dailyGoal)
        }
        _userPreferencesState.update { currentState ->
            currentState.copy(
                dailyGoal = dailyGoal
            )
        }
    }

    /*
    * set weekly goal and save it in the state flow
    * */
    fun setWeeklyGoal(weeklyGoal: Int) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            userPreferencesRepository.updateWeeklyGoal(weeklyGoal)
        }
        _userPreferencesState.update { currentState ->
            currentState.copy(
                weeklyGoal = weeklyGoal
            )
        }
    }


}