package com.example.ula_app.android.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ula_app.game.HomeViewModel
import kotlinx.datetime.Instant


class AndroidHomeViewModel(): ViewModel() {

    private val viewModel by lazy {
        HomeViewModel(
            coroutineScope = viewModelScope
        )
    }

    val uiState = viewModel.uiState
    val toastMessage = viewModel.toastMessage

    fun setAge(
        firstDateTime: Long,
        currentStep: Int,
        goal: Int,
        maxThreshold: Double,
        minThreshold: Double
    ){
        viewModel.setAge(firstDateTime, currentStep, goal, maxThreshold, minThreshold)
    }

    fun clickToUpdateMovies() {
        viewModel.clickToUpdateMovies()
    }

    fun setOpenDialog(openDialog: Boolean) {
        viewModel.setOpenDialog(openDialog)
    }

    // For debug usage only
    fun setId(id: String) {
        viewModel.setId(id)
    }

    fun setAgeAndBodyStatus(
        age: String,
        bodyStatus: String
    ){
        viewModel.setAgeAndBodyStatus(age, bodyStatus)
    }

}


