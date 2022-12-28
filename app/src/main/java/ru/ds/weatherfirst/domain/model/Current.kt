package ru.ds.weatherfirst.domain.model


import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@Keep
@JsonClass(generateAdapter = true)
data class Current(
    @Json(name = "cloud")
    val cloud: Int =0,
    @Json(name = "condition")
    val condition: Condition,
    @Json(name = "feelslike_c")
    val feelslikeC: Double ,
    @Json(name = "humidity")
    val humidity: Int=0,
    @Json(name = "is_day")
    val isDay: Int=0,
    @Json(name = "last_updated")
    val lastUpdated: String ="",
    @Json(name = "temp_c")
    val tempC: Double =0.0,
    @Json(name = "temp_f")
    val tempF: Double =0.0 ,
    @Json(name = "uv")
    val uv: Double=0.0,
    @Json(name = "wind_kph")
    val wind_kph: Double=0.0,

    @Json(name = "wind_dir")
    val wind_dir: String="",
    @Json(name = "wind_degree")
    val wind_degree: Int=0,
    @Json(name = "pressure_in")
    val pressure_in: Double =0.0,

)