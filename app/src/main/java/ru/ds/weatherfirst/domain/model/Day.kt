package ru.ds.weatherfirst.domain.model


import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@Keep
@JsonClass(generateAdapter = true)
data class Day(
    @Json(name = "avgtemp_c")
    val avgtempC: Double = 0.0,
    @Json(name = "condition")
    val condition: Condition,
    @Json(name = "maxtemp_c")
    val maxtempC: Double = 0.0,
    @Json(name = "maxtemp_f")
    val maxtempF: Double = 0.0,
    @Json(name = "maxwind_mph")
    val maxwindMph: Double = 0.0,
    @Json(name = "maxwind_kph")
    val maxwindKph: Double = 0.0,
    @Json(name = "mintemp_c")
    val mintempC: Double = 0.0,
    @Json(name = "mintemp_f")
    val mintempF: Double = 0.0
)