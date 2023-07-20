package com.example.ula_app.android.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ula_app.android.data.UserPreferences
import com.example.ula_app.android.repo.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.datetime.Instant

class UserPreferencesViewModel(
    private val userPreferencesRepository: UserPreferencesRepository
): ViewModel() {

    private val _userPreferencesState = MutableStateFlow(UserPreferences())
    val userPreferencesState: StateFlow<UserPreferences> = _userPreferencesState.asStateFlow()

    init {
        initFirstTime()
    }

    /*
    * Read firstTime and firstDateTime from Datastore and update stateflow value
    * */
    private fun initFirstTime() = viewModelScope.launch {
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
                goal = datastoreObj.goal
            )
        }
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
    * set goal and save it in the state flow
    * */
    fun setGoal(goal: Int) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            userPreferencesRepository.updateGoal(goal)
        }
        _userPreferencesState.update { currentState ->
            currentState.copy(
                goal = goal
            )
        }
    }

}