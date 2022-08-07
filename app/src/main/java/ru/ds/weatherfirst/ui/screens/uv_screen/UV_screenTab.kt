package ru.ds.weatherfirst.ui.screens.uv_screen

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
import ru.ds.weatherfirst.ui.screens.HomeViewModel
import ru.ds.weatherfirst.ui.screens.UVIndicator
import ru.ds.weatherfirst.ui.theme.BlueLight
import ru.ds.weatherfirst.ui.theme.WeatherFirstTheme

@Composable
fun UV_screenTab() {

//viewModel
    val uvLiveData = hiltViewModel<HomeViewModel>()
    val state by uvLiveData.stateMain.collectAsState()


    WeatherFirstTheme {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(0.7f)
                    .padding(bottom = 5.dp),
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
                        backgroundIndicatorStrokeWidth = 70f,
                        foregroundIndicatorStrokeWidth = 70f,
                        bigTextFontSize = 35.sp
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,

                        ) {
                        Text(
                            text = "UV ${uvComment(state.uv)}",
                            color = MaterialTheme.colors.primary,
                            style = TextStyle(fontSize = 28.sp),
                            textAlign = TextAlign.Center,
                            //fontSize = MaterialTheme.typography.h1.fontSize,
                            //fontWeight = FontWeight.Bold,

                        )
                    }
                }

            }
        }

    }

//@Preview(showBackground = true)
//@Composable
//fun UV_screen_preview() {
//    UV_screen(navController = rememberNavController())
//}
}