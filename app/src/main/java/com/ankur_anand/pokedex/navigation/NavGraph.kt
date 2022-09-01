package com.ankur_anand.pokedex.navigation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ankur_anand.pokedex.ui.screens.detail.DetailScreen
import com.ankur_anand.pokedex.ui.screens.home.HomeScreen

private const val TAG = "NavGraph"

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.routeName
    ) {

        composable(
            route = Screen.HomeScreen.routeName
        ) {
            HomeScreen(navController = navController)
        }

        composable(
            route = Screen.DetailScreen.routeName,
            arguments = listOf(
                navArgument(
                    name = NAVIGATION_ARGUMENT_POKEMON_NAME
                ) {
                    type = NavType.StringType
                },
                navArgument(
                    name = NAVIGATION_ARGUMENT_DOMINANT_COLOR
                ) {
                    type = NavType.IntType
                }
            )
        ) {
            val pokemonName = it.arguments?.getString(NAVIGATION_ARGUMENT_POKEMON_NAME) ?: ""
            val dominantColor =
                it.arguments?.getInt(NAVIGATION_ARGUMENT_DOMINANT_COLOR)?.let { color ->
                    Color(color)
                } ?: MaterialTheme.colorScheme.surface

            DetailScreen(
                navController = navController,
                pokemonName = pokemonName,
                dominantColor = dominantColor
            )
        }
    }
}