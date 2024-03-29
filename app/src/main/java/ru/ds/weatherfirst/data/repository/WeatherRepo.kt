package ru.ds.weatherfirst.data.repository

import ru.ds.weatherfirst.data.api.WeatherApi
import ru.ds.weatherfirst.domain.model.Weather
import javax.inject.Inject

private const val API_KEY = "886e042c31bc49c3a3f131017220902"

class WeatherRepo @Inject constructor(
    private val weatherApi: WeatherApi
) {
    suspend fun weatherResponse(city: String): Weather {
        return weatherApi.getWeather(API_KEY,city,"3")
    }

}