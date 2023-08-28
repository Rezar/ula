package com.example.ula_app.game

import com.example.ula_app.data.DataSource
import com.example.ula_app.data.dataclass.DebugSelections
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DebugViewModel(){

    private val _uiState = MutableStateFlow(DebugSelections())
    val uiState: StateFlow<DebugSelections> = _uiState.asStateFlow()

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

    fun getMovieIdOptions (): List<String> {
        return DataSource.movieIdOptions
    }



}