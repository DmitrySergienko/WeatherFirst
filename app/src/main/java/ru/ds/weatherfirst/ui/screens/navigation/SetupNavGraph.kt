package ru.ds.weatherfirst.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.ds.weatherfirst.ui.screens.navigation.Screen
import ru.ds.weatherfirst.ui.screens.uv_screen.UV_screen
import ru.ds.weatherfirst.ui.theme.MyProject


@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(
            route = Screen.Home.route
        ) {

            MyProject(navController = navController)

        }
        composable(
            route = Screen.UVscreen.route
        ) {

            UV_screen(
                //navController = navController
            )

        }
    }
}