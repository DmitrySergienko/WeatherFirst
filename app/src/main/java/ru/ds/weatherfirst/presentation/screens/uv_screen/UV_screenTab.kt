package ru.ds.weatherfirst.presentation.screens.uv_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.ds.weatherfirst.presentation.theme.BlueLight
import ru.ds.weatherfirst.presentation.theme.WeatherFirstTheme
import ru.ds.weatherfirst.ui.screens.UVIndicator

@Composable
fun UV_screenTab() {

//viewModel
    val uvLiveData = hiltViewModel<ru.ds.weatherfirst.presentation.screens.HomeViewModel>()
    val state by uvLiveData.stateMain.collectAsState()

    WeatherFirstTheme {

        Column(
            modifier = Modifier
                .fillMaxWidth()

        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(0.94f)
                    .padding(bottom = 2.dp),
                backgroundColor = BlueLight,
                elevation = 0.dp,
                shape = RoundedCornerShape(10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.padding(7.dp))
                    UVIndicator(
                        indicatorValue = state.uv.toInt(),
                        canvasSize = 220.dp,
                        backgroundIndicatorStrokeWidth = 50f,
                        foregroundIndicatorStrokeWidth = 50f,
                        bigTextFontSize = 28.sp
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,

                        ) {
                        Text(
                            text = "UV ${uVComment(state.uv)}",
                            color = MaterialTheme.colors.primary,
                            style = TextStyle(fontSize = 20.sp),
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    }
}