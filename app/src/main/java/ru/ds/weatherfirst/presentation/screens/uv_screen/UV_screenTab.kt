package ru.ds.weatherfirst.presentation.screens.uv_screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import ru.ds.weatherfirst.R
import ru.ds.weatherfirst.domain.model.Current
import ru.ds.weatherfirst.domain.model.Location
import ru.ds.weatherfirst.presentation.screens.HomeViewModel
import ru.ds.weatherfirst.presentation.screens.main.fontFamily
import ru.ds.weatherfirst.presentation.screens.navigation.Screen
import ru.ds.weatherfirst.presentation.screens.utils.CustomInfoButton
import ru.ds.weatherfirst.presentation.screens.utils.MarqueeText
import ru.ds.weatherfirst.presentation.theme.BlueLight
import ru.ds.weatherfirst.presentation.theme.WeatherFirstTheme
import ru.ds.weatherfirst.presentation.utils.WeatherState
import ru.ds.weatherfirst.ui.screens.UVIndicator

const val MY_LOGG = "VVVV"

@Composable
fun UV_tabHoist(
    navController:NavController,
    weatherState: WeatherState){

    //viewModel
    val uvLiveData = hiltViewModel<HomeViewModel>()
    val state by uvLiveData.stateMain.collectAsState()
    val stateLocation by uvLiveData.location.collectAsState()

    UV_screenTab(
        navController = navController,
        state = state,
        stateLocation = stateLocation,
        weatherState = weatherState,

    )
}

@Composable
fun UV_screenTab(
    navController:NavController,
    state: Current,
    stateLocation: Location,
    weatherState:WeatherState,

) {
    val mainScreenViewModel = hiltViewModel<HomeViewModel>()

    when (weatherState) {
        is WeatherState.Loading -> Log.d(MY_LOGG, "UV screen state Loading: $weatherState")
        is WeatherState.Success -> mainScreenViewModel.getWeather("default")
        is WeatherState.Error -> Log.d(MY_LOGG, "UV screen state Error: $weatherState")
    }


    WeatherFirstTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.94f)
                    .padding(4.dp),
                backgroundColor = BlueLight,
                elevation = 0.dp,
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp)
                ) {
                    Spacer(modifier = Modifier.padding(7.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        UVIndicator(
                            indicatorValue = state.uv.toInt(),
                            canvasSize = 200.dp,
                            backgroundIndicatorStrokeWidth = 40f,
                            foregroundIndicatorStrokeWidth = 40f,
                            bigTextFontSize = 28.sp,
                            onClick = {navController.navigate(Screen.UVscreen.passUVARG(state.uv.toInt()))}
                        )
                            Column(modifier = Modifier
                                .padding(end = 16.dp),
                                horizontalAlignment = Alignment.End
                            ) {
                                AsyncImage(
                                    model = "https:${state.condition?.icon}",
                                    contentDescription = "imageIcon",
                                    modifier = Modifier
                                        .size(76.dp)
                                        .padding(top = 1.dp, end = 2.dp)
                                )
                                Text(
                                    modifier = Modifier
                                        .padding(start = 10.dp),
                                    text = "${state.tempC.toInt()}°C",
                                    style = TextStyle(fontSize = 56.sp),
                                    color = Color.Black,
                                    fontFamily = fontFamily,
                                    fontWeight = FontWeight.Normal,
                                )
                                Text(
                                    modifier = Modifier
                                        .padding(start = 10.dp),
                                    text = stateLocation.region,
                                    style = TextStyle(fontSize = 16.sp),
                                    color = Color.Black,
                                    fontFamily = fontFamily,
                                    fontWeight = FontWeight.Normal,
                                )
                            }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 20.dp, end = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                        MarqueeText(text = uVComment(state.uv))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 60.dp, bottom = 30.dp),
                            horizontalArrangement = Arrangement.Center
                        ){
                            Spacer(modifier = Modifier.height(60.dp))

                            CustomInfoButton(
                                text = stringResource(id = R.string.indication_det),
                                navController,
                                painterResource(id = R.drawable.ic_uv_img2),
                                Screen.UVscreen.passUVARG(state.uv.toInt()),
                                state.uv.toInt()
                            )
                        }
                    }
                }
            }
        }
    }
}




