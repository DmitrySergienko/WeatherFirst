package ru.ds.weatherfirst.data.api.model


import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class Day(
    @Json(name = "avgtemp_c")
    val avgtempC: Double,
    @Json(name = "condition")
    val condition: Condition,
    @Json(name = "maxtemp_c")
    val maxtempC: Double,
    @Json(name = "maxtemp_f")
    val maxtempF: Double,
    @Json(name = "maxwind_mph")
    val maxwindMph: Double,
    @Json(name = "mintemp_c")
    val mintempC: Double,
    @Json(name = "mintemp_f")
    val mintempF: Double
)