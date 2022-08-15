package com.ankur_anand.pokedex.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ankur_anand.pokedex.ui.screens.home.Home

private const val TAG = "NavGraph"

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.routeName
    ) {

        composable(
            route = Screen.Home.routeName
        ) {
            Home(navController = navController)
        }

/*//        composable(
//            route = Screen.Screen2.route,
//            arguments = listOf(
//                navArgument(
//                   name = NAVIGATION_ARGUMENT_SCREEN2
//                ){
//                    type = NavType.IntType
//                }
//            )
//        ){
//            Log.d(TAG, "Id: ${it.arguments?.getInt(NAVIGATION_ARGUMENT_SCREEN2)}")
//            Screen2(navController = navController)
//        }
//
//        composable(
//            route = Screen.Screen3.route
//        ){
//            Screen3(navController = navController)
//        }*/
    }
}