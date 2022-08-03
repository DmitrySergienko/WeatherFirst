package ru.ds.weatherfirst.data.api.model


import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
@JsonClass(generateAdapter = true)
data class Current(
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
    @Json(name = "last_updated")
    val lastUpdated: String,
    @Json(name = "temp_c")
    val tempC: Double,
    @Json(name = "temp_f")
    val tempF: Double,
    @Json(name = "uv")
    val uv: Double
):Parcelable