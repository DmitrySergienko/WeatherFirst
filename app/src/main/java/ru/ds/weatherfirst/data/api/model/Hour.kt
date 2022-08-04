package ru.ds.weatherfirst.data.api.model


import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
@JsonClass(generateAdapter = true)
data class Hour(
    @Json(name = "cloud")
    val cloud: Int,
    @Json(name = "condition")
    val condition: Condition,
    @Json(name = "feelslike_c")
    val feelslikeC: Double,
    @Json(name = "humidity")
    val humidity: Int,
    @Json(name = "is_day")
    val isDay: Int,
    @Json(name = "temp_c")
    val tempC: Double,
    @Json(name = "time")
    val time: String,
    @Json(name = "time_epoch")
    val timeEpoch: Int,
    @Json(name = "wind_kph")
    val windKph: Double,
    @Json(name = "uv")
    val uv: Double,
    @Json(name = "wind_dir")
    val wind_dir: String

) : Parcelable