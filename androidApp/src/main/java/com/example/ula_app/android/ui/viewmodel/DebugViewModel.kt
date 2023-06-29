package com.example.ula_app.android.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ula_app.android.data.DebugSelections
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DebugViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(DebugSelections())
    private val uiState: StateFlow<DebugSelections> = _uiState.asStateFlow()

    fun isEnabledButton(
        ageSelection: String,
        bodyStatusSelection: String,
        movieSelection: String
    ): Boolean {
        return when {
            ageSelection.isEmpty() && bodyStatusSelection.isEmpty() && movieSelection.isNotEmpty() -> {
                true
            }

            ageSelection.isNotEmpty() && bodyStatusSelection.isNotEmpty() && movieSelection.isEmpty() -> {
                true
            }

            else -> {
                false
            }
        }
    }

    fun isOnlyChangeId (
        ageSelection: String,
        bodyStatusSelection: String,
        movieSelection: String
    ): Boolean {

        return when {
            ageSelection.isEmpty() && bodyStatusSelection.isEmpty() && movieSelection.isNotEmpty() -> {
                true
            }

//            ageSelection.isNotEmpty() && bodyStatusSelection.isNotEmpty() && movieSelection.isEmpty() -> {
//                false
//            }

            else -> {
                false
            }
        }
    }



}