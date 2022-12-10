package ru.ds.weatherfirst.presentation.ui.screens.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.ds.weatherfirst.R

@Composable
fun translateCondition(param: String): String {

    val text = when (param) {
        "Sunny" -> stringResource(id = R.string.sunny)
        "Partly cloudy" -> stringResource(id = R.string.partly_cloudy)
        "Cloudy" -> stringResource(id = R.string.cloudy)
        "Light rain shower" -> stringResource(id = R.string.light_rain_shower)
        "Overcast" -> stringResource(id = R.string.overcast)
        "Heavy rain" -> stringResource(id = R.string.heavy_rain)
        "Patchy rain possible" -> stringResource(id = R.string.patchy_rain)
        "Moderate or heavy rain shower" -> stringResource(id = R.string.moderate_rain)
        "Last Quarter" -> stringResource(id = R.string.last_quarter)
        "Waning Crescent" -> stringResource(id = R.string.waning_crescent)
        "Clear" -> stringResource(id = R.string.clear)
        else -> {
            param
        }
    }
    return text
}