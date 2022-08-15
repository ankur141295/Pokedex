package com.ankur_anand.pokedex.navigation

const val SCREEN_HOME = "Home"

sealed class Screen(val routeName: String){
    object Home : Screen(routeName = SCREEN_HOME)
}
