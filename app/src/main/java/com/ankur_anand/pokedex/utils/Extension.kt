package com.ankur_anand.pokedex.utils

import android.app.Activity
import android.view.View
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat

fun String.capitalizeFirstLetter(): String {
    return this.replaceFirstChar {
        if (it.isLowerCase()) {
            it.titlecase(java.util.Locale.ROOT)
        } else {
            it.toString()
        }
    }
}

fun Activity.setHomeStatusBarAndIconColor(
    view: View,
    darkTheme: Boolean,
    statusBarColor: Color
) {
    this.window.statusBarColor = statusBarColor.toArgb()

    WindowCompat.getInsetsController(
        this.window, view
    ).isAppearanceLightStatusBars = !darkTheme
}

fun Activity.setDetailStatusBarAndIconColor(
    view: View,
    darkTheme: Boolean = true,
    dominantColor: Color
) {
    this.window.statusBarColor = dominantColor.copy(alpha = 0.5f).toArgb()

    WindowCompat.getInsetsController(
        this.window, view
    ).isAppearanceLightStatusBars = darkTheme
}