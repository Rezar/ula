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
            _userPreferencesState.value = _userPreferencesState.value.copy(
                firstTime = userPreferencesRepository.fetchInitialPreferences().firstTime
            )
            _userPreferencesState.value = _userPreferencesState.value.copy(
                firstDateTime = userPreferencesRepository.fetchInitialPreferences().firstDateTime
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

}