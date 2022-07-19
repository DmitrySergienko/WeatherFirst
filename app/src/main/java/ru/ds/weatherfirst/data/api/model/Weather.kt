package ru.ds.weatherfirst.data.api.model


import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class Weather(
    @Json(name = "current")
    val current: Current,
    @Json(name = "forecast")
    val forecast: Forecast,
    @Json(name = "location")
    val location: Location
)