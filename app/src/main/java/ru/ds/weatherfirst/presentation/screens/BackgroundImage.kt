package ru.ds.weatherfirst.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import ru.ds.weatherfirst.R

@Composable
fun backgroundImage(): Int {
    val mainScreenViewModel = hiltViewModel<HomeViewModel>()
    val state by mainScreenViewModel.stateMain.collectAsState()

    val humidity = state.humidity
    var backImage = R.drawable.ic_back_new
    val currentTemp = state.tempC.toInt()

    if (currentTemp < -1) {
        backImage = R.drawable.ic_snow_forest
    } else {
        if (humidity > 86) {
            backImage = R.drawable.ic_rein
        } else {
            if (currentTemp > 40) {
                backImage = R.drawable.ic_dune
            } else {
                backImage = R.drawable.ic_back_new
            }
        }
    }
    return backImage
}