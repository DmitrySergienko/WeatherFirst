package ru.ds.weatherfirst.data.api.model


import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@Keep
@JsonClass(generateAdapter = true)
data class Hour(
    @Json(name = "cloud")
    val cloud: Int =0,
    @Json(name = "condition")
    val condition: Condition,
    @Json(name = "feelslike_c")
    val feelslikeC: Double = 0.0,
    @Json(name = "humidity")
    val humidity: Int =0,
    @Json(name = "is_day")
    val isDay: Int=0,
    @Json(name = "temp_c")
    val tempC: Double =0.0,
    @Json(name = "time")
    val time: String = "-",
    @Json(name = "time_epoch")
    val timeEpoch: Int = 0,
    @Json(name = "wind_kph")
    val windKph: Double = 0.0,
    @Json(name = "uv")
    val uv: Double = 0.0,
    @Json(name = "wind_dir")
    val wind_dir: String ="-"

)