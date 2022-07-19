package ru.ds.weatherfirst.data.api


import retrofit2.http.GET
import retrofit2.http.Query
import ru.ds.weatherfirst.data.api.model.Weather

private const val DAYS = "3"
private const val CITY = "q"
private const val KEY = "key"


interface WeatherApi {

    @GET("v1/forecast.json")
    suspend fun getWeather(
        @Query(KEY) key: String,
        @Query(CITY) city: String,
        @Query(DAYS) days: String
        ): Weather
}