package ru.ds.weatherfirst.data.api.model


import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
@JsonClass(generateAdapter = true)
data class Forecastday(
    @Json(name = "astro")
    val astro: Astro,
    @Json(name = "date")
    val date: String,
    @Json(name = "date_epoch")
    val dateEpoch: Int,
    @Json(name = "day")
    val day: Day,
    @Json(name = "hour")
    val hour: List<Hour>
):Parcelable