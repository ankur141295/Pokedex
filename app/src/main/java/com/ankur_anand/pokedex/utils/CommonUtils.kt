package com.ankur_anand.pokedex.utils

import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.squareup.moshi.Moshi

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

inline fun <reified T> convertJsonToObject(json: String, moshi: Moshi): T? {
    return moshi.adapter(T::class.java).fromJson(json)
}

inline fun <reified T> convertObjectToJson(objectData: T, moshi: Moshi): String =
    moshi.adapter(T::class.java).toJson(objectData)