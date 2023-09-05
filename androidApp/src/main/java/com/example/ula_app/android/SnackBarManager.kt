package com.example.ula_app.android

import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SnackBarManager(
    var scaffoldState: ScaffoldState,
    var snackBarScope: CoroutineScope,
) {

    fun showSnackBar(message: String, duration: SnackbarDuration = SnackbarDuration.Short) {
        snackBarScope.launch {
            scaffoldState.snackbarHostState.showSnackbar(
                message = message,
                duration = duration
            )
        }
    }
}