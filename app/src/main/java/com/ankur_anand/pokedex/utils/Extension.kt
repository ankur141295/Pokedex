package com.ankur_anand.pokedex.utils

fun String.capitalizeFirstLetter(): String {
    return this.replaceFirstChar {
        if (it.isLowerCase()) {
            it.titlecase(java.util.Locale.ROOT)
        } else {
            it.toString()
        }
    }
}