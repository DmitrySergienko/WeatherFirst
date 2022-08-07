package ru.ds.weatherfirst.ui.screens.navigation

const val UV_ARG_KEY = "uv_argument"

sealed class Screen(val route:String){
    object Home: Screen(route = "home_screen")
    object UVscreen: Screen(route = "UV_screen/{$UV_ARG_KEY}"){
        fun passUVARG(uv_arg: Int):String{
            return "UV_screen/$uv_arg"
        }
    }
}
