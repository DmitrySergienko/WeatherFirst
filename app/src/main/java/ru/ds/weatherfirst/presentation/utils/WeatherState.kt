package ru.ds.weatherfirst.presentation.utils

import ru.ds.weatherfirst.domain.model.Weather

sealed interface WeatherState {
    data class Success(val data: Weather) : WeatherState
    object Error : WeatherState
    object Loading : WeatherState
}
