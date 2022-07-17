package ru.ds.weatherfirst.data.api

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import ru.ds.weatherfirst.data.api.model.Weather

private const val CITY = "q"
private const val DAYS = "days"
private const val KEY = "886e042c31bc49c3a3f131017220902"



interface WeatherApi {

    @GET(ApiConstants.END_POINT)
    suspend fun getWeather(
        @Header(KEY) key: String,
        @Header(CITY) q: String,
        @Header(DAYS) days: Int,
    ): List<Weather>
}