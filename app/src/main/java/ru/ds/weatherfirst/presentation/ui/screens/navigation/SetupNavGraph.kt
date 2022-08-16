package ru.ds.weatherfirst.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.ds.weatherfirst.presentation.ui.screens.history.HistoryItemScreen
import ru.ds.weatherfirst.presentation.ui.screens.history.HistoryScreen
import ru.ds.weatherfirst.presentation.ui.screens.navigation.HISTORY_ARG_KEY
import ru.ds.weatherfirst.presentation.ui.screens.navigation.Screen
import ru.ds.weatherfirst.presentation.ui.screens.navigation.UV_ARG_KEY
import ru.ds.weatherfirst.presentation.ui.screens.search.SearchScreen
import ru.ds.weatherfirst.presentation.ui.screens.uv_screen.UV_screen
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
            route = Screen.History.route
        ) { HistoryScreen(navController = navController) }

        composable(
            route = Screen.HistoryItemScreen.route
        ) { HistoryItemScreen(navController = navController, history = "") }

        composable(
            route = Screen.UVscreen.route,
            arguments = listOf(navArgument(UV_ARG_KEY) {
                type = NavType.IntType
            })
        ) {
            //тут указываем экран куда переходим, и можно посмотреть есть ли аргументы
            UV_screen(navController = navController, it.arguments?.getInt(UV_ARG_KEY).toString())
        }

//        composable(
//            route = Screen.Search.route
//        ) {
//            SearchScreen(
//                navController = navController,
//                string = it.arguments?.getString(HISTORY_ARG_KEY)
//            )
//        }
        composable(
            route = Screen.Search.route,
            arguments = listOf(navArgument(HISTORY_ARG_KEY) {
                type = NavType.StringType
            })
        ) {
            //тут указываем экран куда переходим, и можно посмотреть есть ли аргументы
            SearchScreen(navController = navController, it.arguments?.getString(HISTORY_ARG_KEY))

        }
    }
}