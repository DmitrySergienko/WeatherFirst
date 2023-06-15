package ru.ds.weatherfirst.presentation.screens.graphs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.ds.weatherfirst.presentation.theme.WeatherFirstTheme
import ru.ds.weatherfirst.presentation.ui.screens.Graph

@Composable
fun ChartGraphScreen() {

    Column() {
        WeatherFirstTheme() {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val homeViewModel = hiltViewModel<ru.ds.weatherfirst.presentation.screens.HomeViewModel>()
                val state by homeViewModel.state.collectAsState()

                homeViewModel.getWeather("Dubai")

                val yStep = 1
                val result = mutableListOf<Float>()

                if (state.isNotEmpty()) {
                    for (element in state) {
                        val list = element.uv.toString().toFloat()
                        result.add(list)
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent)
                ) {
                    if (result.isNotEmpty()) {
                        Graph(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(500.dp),
                            xValues = (0..23).map { it + 1 },
                            yValues = (0..10).map { (it + 1) * yStep },
                            points = result,
                            paddingSpace = 16.dp,
                            verticalStep = yStep
                        )
                    }
                }
            }
        }
    }
}




