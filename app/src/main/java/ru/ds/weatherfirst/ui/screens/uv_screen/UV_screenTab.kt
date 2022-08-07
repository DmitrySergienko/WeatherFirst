package ru.ds.weatherfirst.ui.screens.uv_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
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
                    // var value by rememberSaveable { mutableStateOf(0) }
                    UVIndicator(indicatorValue = state.uv.toInt())
                    UVTextTab()
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