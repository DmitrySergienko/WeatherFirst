package ru.ds.weatherfirst.presentation.screens.utils

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import ru.ds.weatherfirst.presentation.theme.R0
import ru.ds.weatherfirst.presentation.theme.R1
import ru.ds.weatherfirst.presentation.theme.R2
import ru.ds.weatherfirst.presentation.theme.R4

@Composable
fun animatedColor(index: Int): Color {
    val color by animateColorAsState(
        targetValue = when (index){
            in 0..2 -> {R1}
            in 3..5 -> {R1}
            in 6..7 -> {R2}
            in 8..10 -> {R1}
            in 11..15 -> {R0}
            else -> {R4}
        },
        animationSpec = tween(2000)
    )
    return color
}