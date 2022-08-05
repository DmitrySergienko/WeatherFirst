package ru.ds.weatherfirst.data.api.model


import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@Keep
@JsonClass(generateAdapter = true)
data class Condition(
    @Json(name = "icon")
    val icon: String = "-",
    @Json(name = "text")
    val text: String = "-"
)