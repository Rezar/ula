package com.example.ula_app.android.ui.lockgame.snakegame.domain.extension

import android.content.Context
import android.content.Intent
import android.os.Bundle

inline fun <reified T : Any> Context.launchActivity(
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>(this)
    intent.init()
    startActivity(intent, options)
}

inline fun <reified T : Any> newIntent(context: Context): Intent =
    Intent(context, T::class.java)
