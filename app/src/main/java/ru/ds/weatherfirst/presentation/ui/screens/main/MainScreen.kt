package ru.ds.weatherfirst.presentation.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import ru.ds.weatherfirst.R
import ru.ds.weatherfirst.presentation.ui.screens.HomeViewModel
import ru.ds.weatherfirst.presentation.ui.screens.navigation.Screen
import ru.ds.weatherfirst.presentation.ui.screens.utils.translateCondition
import ru.ds.weatherfirst.presentation.ui.theme.BlueLight
import ru.ds.weatherfirst.presentation.ui.theme.TextLight

//вытаскиваем переменные в отедльную функцию Hoist, чтобы MainScreen сделать stateless
@Composable
fun MainScreenHoist(
    navController: NavController
) {
//это переменная (наш город, который может быть изменен)
    var cityInputText by rememberSaveable { mutableStateOf("") }

    MainScreen(
        city = cityInputText,
        onCityChange = { cityInputText = it },
        navController = navController
    )
}

@Composable
fun MainScreen(city: String, onCityChange: (String) -> Unit, navController: NavController) {


    val mainScreenViewModel = hiltViewModel<HomeViewModel>()
    val state by mainScreenViewModel.stateMain.collectAsState()


    mainScreenViewModel.getWeather("default")


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
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.ic_uv_img2),
                        contentDescription = "UV image",
                        modifier = Modifier
                            .padding(start = 5.dp, top = 6.dp, end = 20.dp)
                            .size(30.dp)
                            .alpha(0.7f)
                            .clickable {
                                //открываем UV_screen и перекидываем туда аргументы
                                navController.navigate(route = Screen.UVscreen.passUVARG(state.uv.toInt()))
                                //если просто открыть окно не закидывая аргументов
                                //navController.navigate(route = Screen.UVscreen.route)

                            }
                    )
                    Column(
                        modifier = Modifier,
                        horizontalAlignment = Alignment.End

                    ) {
                        Text(
                            modifier = Modifier
                                .padding(top = 6.dp, end = 5.dp),
                            text = "${stringResource(id = R.string.feels_like)} ${state.feelslikeC.toInt()}°C",
                            style = TextStyle(fontSize = 22.sp),
                            color = TextLight

                        )
                        Image(
                            painter = painterResource(id = R.drawable.ic_baseline_search_24),
                            contentDescription = "UV image",
                            modifier = Modifier
                                .padding(start = 5.dp, top = 14.dp, end = 12.dp)
                                .size(30.dp)
                                .alpha(0.7f)
                                .clickable {
                                    //открываем UV_screen и перекидываем туда аргументы
                                    navController.navigate(route = Screen.Search.route)

                                },

                            )
                    }


                }


                Column(
                    modifier = Modifier
                        .fillMaxWidth()

                ) {

//======Details========
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 1.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 5.dp),
                        horizontalAlignment = Alignment.Start

                    ) {

                        Text(
                            modifier = Modifier
                                .padding(1.dp),
                            text = "${stringResource(id = R.string.humidity)} ${state.humidity}%",
                            style = TextStyle(fontSize = 16.sp),
                            color = TextLight
                        )
                        Text(
                            modifier = Modifier
                                .padding(1.dp),
                            text = translateCondition(param = state.condition.text),
                            style = TextStyle(fontSize = 16.sp),
                            color = TextLight
                        )
                        Text(
                            modifier = Modifier
                                .padding(1.dp),
                            text = "${stringResource(id = R.string.uv)} ${state.uv}",
                            style = TextStyle(fontSize = 16.sp),
                            color = TextLight
                        )
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {
                        AsyncImage(
                            model = "https:${state.condition.icon}",
                            contentDescription = "imageIcon",
                            modifier = Modifier
                                .size(105.dp)
                                .padding(top = 1.dp, end = 2.dp)

                        )
                    }
                    Column(
                        modifier = Modifier
                            .padding(end = 5.dp)
                            .weight(1f),
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(1.dp),
                            text = "${stringResource(id = R.string.cloud)} ${state.cloud}%",
                            style = TextStyle(fontSize = 16.sp),
                            color = TextLight
                        )
                        Text(
                            modifier = Modifier
                                .padding(1.dp),
                            text = "${stringResource(id = R.string.temp)} ${state.tempF.toInt()}F",
                            style = TextStyle(fontSize = 16.sp),
                            color = TextLight
                        )
                    }
                }

//======Main Temperature=======

                Text(
                    modifier = Modifier
                        .padding(top = 1.dp, bottom = 15.dp, end = 5.dp),
                    text = "${state.tempC.toInt()}°C",
                    style = TextStyle(fontSize = 65.sp),
                    color = TextLight
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 4.dp, end = 4.dp)
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.Start,

                    ) {

                    Text(
                        modifier = Modifier
                            .padding(1.dp)
                            .padding(end = 23.dp),
                        text = "${stringResource(id = R.string.last_update)} ${state.lastUpdated}",
                        style = TextStyle(fontSize = 20.sp),
                        color = TextLight
                    )

                }
            }
        }
    }
}





