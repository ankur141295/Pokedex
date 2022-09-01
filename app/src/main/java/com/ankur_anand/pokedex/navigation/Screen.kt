package com.ankur_anand.pokedex.navigation

const val HOME_SCREEN = "home_screen"
const val DETAIL_SCREEN = "detail_screen"

const val NAVIGATION_ARGUMENT_POKEMON_NAME = "pokemon_name"
const val NAVIGATION_ARGUMENT_DOMINANT_COLOR = "dominant_color"

sealed class Screen(val routeName: String) {
    object HomeScreen : Screen(routeName = HOME_SCREEN)

    object DetailScreen : Screen(
        routeName = "$DETAIL_SCREEN/{$NAVIGATION_ARGUMENT_POKEMON_NAME}/{$NAVIGATION_ARGUMENT_DOMINANT_COLOR}"
    ) {

        fun route(pokemonName: String, dominantColor: Int): String {
            return "$DETAIL_SCREEN/$pokemonName/$dominantColor"
        }
    }
}
