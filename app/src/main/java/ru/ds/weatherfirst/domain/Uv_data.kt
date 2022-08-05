package ru.ds.weatherfirst.domain

data class Uv_data (

    val uv_data: List<String> = listOf(
        "0–2 Low",
        "3–5 Moderate",
        "6–7 High",
        "8–10 Very high",
        "11+ Extreme"
    )
)