package com.example.ula_app.android.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ula_app.android.data.Goal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.Instant

class GoalViewModel : ViewModel() {

    /*
    * Creating a state for welcome goal selection page
    * This is a way to remember the state flow for jetpack compose
    * */
    private val _uiState = MutableStateFlow(Goal())
    val uiState: StateFlow<Goal> = _uiState.asStateFlow()

    /*
    * Update the value of steps
    * */
    fun setSteps(steps: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                steps = steps
            )
        }
    }

}