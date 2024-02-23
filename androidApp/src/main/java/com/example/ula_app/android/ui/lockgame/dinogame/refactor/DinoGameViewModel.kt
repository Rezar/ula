package com.example.ula_app.android.ui.lockgame.dinogame.refactor

import androidx.lifecycle.ViewModel
import com.example.ula_app.android.ui.lockgame.dinogame.refactor.model.ViewState
import kotlinx.coroutines.flow.MutableStateFlow

class DinoGameViewModel: ViewModel() {

    private val _viewState = MutableStateFlow(ViewState())
    val viewState = _viewState
}