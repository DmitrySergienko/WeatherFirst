package ru.ds.weatherfirst.ui.screens.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import ru.ds.weatherfirst.ui.screens.HomeViewModel
import ru.ds.weatherfirst.ui.theme.BlueLight
import ru.ds.weatherfirst.ui.theme.TextLight

//вытаскиваем переменные в отедльную функцию Hoist, чтобы MainScreen сделать stateless
@Composable
fun MainScreenHoist() {
//это переменная (наш город, который может быть изменен)
    var cityInputText by rememberSaveable { mutableStateOf("") }

    MainScreen(city = cityInputText, onCityChange = { cityInputText = it })
}

@Composable
fun MainScreen(city: String, onCityChange: (String) -> Unit) {

    val mainScreenViewModel = viewModel(modelClass = HomeViewModel::class.java)
    val state by mainScreenViewModel.stateMain.collectAsState()

    val rowSection = listOf(
        "${state.uv}",
        "${state.condition}",
        "${state.humidity}",
        "${state.cloud}",
        "${state.isDay}"
    )

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
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        modifier = Modifier
                            .padding(1.dp),
                        text = "Feels like ${state.feelslikeC.toInt()}°C",
                        style = TextStyle(fontSize = 22.sp),
                        color = TextLight
                    )

                    AsyncImage(
                        model = "https:${state.condition.icon}",
                        contentDescription = "imageIcon",
                        modifier = Modifier
                            .size(65.dp)
                            .padding(top = 14.dp, end = 2.dp)
                            .clickable {

                            }
                    )
                }


                Column(
                    modifier = Modifier
                        .fillMaxWidth()

                ) {

                    OutlinedTextField(
                        value = city,
                        onValueChange = onCityChange,
                        label = { Text(text = "City") },
                        placeholder = { Text(text = "Dubai") },
                        singleLine = true,
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .align(Alignment.CenterHorizontally),

                        textStyle = TextStyle(color = TextLight, fontSize = 26.sp),
                        trailingIcon = {
                            IconButton(onClick = { mainScreenViewModel.getWeather(city) }) {
                                Icon(
                                    imageVector = Icons.Filled.Edit,
                                    contentDescription = "City"
                                )
                            }
                        }
                    )
                }

                Text(
                    modifier = Modifier
                        .padding(top = 5.dp),
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
                        modifier = Modifier.padding(1.dp).padding(end = 23.dp),
                        text = state.condition.text,
                        style = TextStyle(fontSize = 22.sp),
                        color = TextLight
                    )
                    Text(
                        modifier = Modifier.padding(1.dp).padding(end = 23.dp),
                        text = "UV ${state.uv}",
                        style = TextStyle(fontSize = 22.sp),
                        color = TextLight
                    )
                    Text(
                        modifier = Modifier.padding(1.dp).padding(end = 23.dp),
                        text = "Humidity ${state.humidity}%",
                        style = TextStyle(fontSize = 22.sp),
                        color = TextLight
                    )
                    Text(
                        modifier = Modifier.padding(1.dp).padding(end = 23.dp),
                        text = "Temperature ${state.tempF.toInt()}F",
                        style = TextStyle(fontSize = 22.sp),
                        color = TextLight
                    )
                    Text(
                        modifier = Modifier.padding(1.dp).padding(end = 23.dp),
                        text = "Cloud ${state.cloud}",
                        style = TextStyle(fontSize = 22.sp),
                        color = TextLight
                    )
                }
            }
        }
    }

}

