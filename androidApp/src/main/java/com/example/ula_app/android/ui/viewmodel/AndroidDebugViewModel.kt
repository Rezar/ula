package com.example.ula_app.android.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ula_app.game.DebugViewModel
import kotlinx.coroutines.coroutineScope

class AndroidDebugViewModel(): ViewModel() {

    private val viewModel by lazy {
        DebugViewModel()
    }

    val uiState = viewModel.uiState

    fun isOnlyChangeId (
        ageSelection: String,
        bodyStatusSelection: String,
        movieSelection: String
    ): Boolean {
        return viewModel.isOnlyChangeId(ageSelection, bodyStatusSelection, movieSelection)
    }

    fun isEnabledButton(
        ageSelection: String,
        bodyStatusSelection: String,
        movieSelection: String
    ): Boolean {
        return viewModel.isEnabledButton(ageSelection, bodyStatusSelection, movieSelection)
    }
}