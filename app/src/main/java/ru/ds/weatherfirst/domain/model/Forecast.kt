package ru.ds.weatherfirst.domain.model


import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@Keep
@JsonClass(generateAdapter = true)
data class Forecast(
    @Json(name = "forecastday")
    val forecastday: List<Forecastday>
)