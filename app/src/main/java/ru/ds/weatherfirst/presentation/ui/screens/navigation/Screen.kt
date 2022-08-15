package ru.ds.weatherfirst.presentation.ui.screens.navigation

const val UV_ARG_KEY = "uv_argument"

sealed class Screen(val route:String){
    object Home: Screen(route = "home_screen")
    object Search: Screen(route = "search_screen")
    object History: Screen(route = "history_screen")
    object UVscreen: Screen(route = "UV_screen/{$UV_ARG_KEY}"){
        fun passUVARG(uv_arg: Int):String{
            return "UV_screen/$uv_arg"
        }
    }
}
