package ru.ds.weatherfirst.ui.screens.navigation

sealed class Screen(val route:String){
    object Home: Screen(route = "home_screen")
    object UVscreen: Screen(route = "UV_screen")
}
