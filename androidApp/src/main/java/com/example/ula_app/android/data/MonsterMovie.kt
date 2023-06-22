package com.example.ula_app.android.data

import android.net.Uri

data class MonsterMovie(
    val uri: Uri,
    val id: String,
    val taps: Int,
    val scenario: Int,
    val hasLock: Boolean,
    val loop: Boolean,
    val age: String,
    val status: String
)
