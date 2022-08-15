package com.ankur_anand.pokedex.utils

import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

fun supportsDynamicColor(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

@Composable
fun getVerticalGradient(
    defaultDominantColor: Color = MaterialTheme.colorScheme.onBackground,
    dominantColor: Color
): Brush {
    return Brush.verticalGradient(
        listOf(
            dominantColor,
            defaultDominantColor
        )
    )
}