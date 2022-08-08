package ru.ds.weatherfirst.ui.screens.main

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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import ru.ds.weatherfirst.R
import ru.ds.weatherfirst.ui.screens.HomeViewModel
import ru.ds.weatherfirst.ui.screens.navigation.Screen
import ru.ds.weatherfirst.ui.theme.BlueLight
import ru.ds.weatherfirst.ui.theme.TextLight

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


    mainScreenViewModel.getWeather("Dubai")


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

                    Text(
                        modifier = Modifier
                            .padding(top = 6.dp, end = 5.dp),
                        text = "Feels like ${state.feelslikeC.toInt()}°C",
                        style = TextStyle(fontSize = 22.sp),
                        color = TextLight

                    )


                }


                Column(
                    modifier = Modifier
                        .fillMaxWidth()

                ) {

//                        OutlinedTextField(
//                            value = city,
//                            onValueChange = onCityChange,
//                            label = { Text(text = "City") },
//                            placeholder = { Text(text = "Dubai") },
//                            singleLine = true,
//                            modifier = Modifier
//                                .padding(start = 5.dp)
//                                .align(Alignment.CenterHorizontally),
//
//                            textStyle = TextStyle(color = TextLight, fontSize = 26.sp),
//                            trailingIcon = {
//                                IconButton(onClick = {
//                                    if (city.isNotEmpty()) mainScreenViewModel.getWeather(city)
//                                    else {
//                                        mainScreenViewModel.getWeather("Dubai")
////                                    Toast.makeText(context, "Enter city name", Toast.LENGTH_SHORT).show()
//                                    }
//                                }) {
//                                    Icon(
//                                        imageVector = Icons.Filled.Edit,
//                                        contentDescription = "City"
//                                    )
//                                }
//                            }
//                        )
                }
                AsyncImage(
                    model = "https:${state.condition.icon}",
                    contentDescription = "imageIcon",
                    modifier = Modifier
                        .size(105.dp)
                        .padding(top = 1.dp, end = 2.dp)
                        .clickable {

                        }
                )
                Text(
                    modifier = Modifier
                        .padding(top = 5.dp, bottom = 15.dp, end = 5.dp),
                    text = "${state.tempC.toInt()}°C",
                    style = TextStyle(fontSize = 65.sp),
                    color = TextLight
                )





                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 4.dp, end = 4.dp)
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.SpaceBetween,

                    ) {
                    Text(
                        modifier = Modifier
                            .padding(1.dp)
                            .padding(end = 23.dp),
                        text = state.condition.text,
                        style = TextStyle(fontSize = 20.sp),
                        color = TextLight
                    )
                    Text(
                        modifier = Modifier
                            .padding(1.dp)
                            .padding(end = 23.dp),
                        text = "Humidity ${state.humidity}%",
                        style = TextStyle(fontSize = 20.sp),
                        color = TextLight
                    )
                    Text(
                        modifier = Modifier
                            .padding(1.dp)
                            .padding(end = 23.dp),
                        text = "Temperature ${state.tempF.toInt()}F",
                        style = TextStyle(fontSize = 20.sp),
                        color = TextLight
                    )
                    Text(
                        modifier = Modifier
                            .padding(1.dp)
                            .padding(end = 23.dp),
                        text = "Cloud ${state.cloud}%",
                        style = TextStyle(fontSize = 20.sp),
                        color = TextLight
                    )
                    Text(
                        modifier = Modifier
                            .padding(1.dp)
                            .padding(end = 23.dp),
                        text = "UV ${state.uv}",
                        style = TextStyle(fontSize = 20.sp),
                        color = TextLight
                    )
                    Text(
                        modifier = Modifier
                            .padding(1.dp)
                            .padding(end = 23.dp),
                        text = "Last update: ${state.lastUpdated}",
                        style = TextStyle(fontSize = 20.sp),
                        color = TextLight
                    )
                }
            }
        }
    }
}


