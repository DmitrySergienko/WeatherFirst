package ru.ds.weatherfirst.ui.screens.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
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




@Composable
fun MainScreen() {

    val mainScreenViewModel = viewModel(modelClass = HomeViewModel::class.java)
    val state by mainScreenViewModel.stateMain.collectAsState()
    var cityInputText by remember { mutableStateOf("Dubai") }


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
                        text = " ${state.lastUpdated}",
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
                        value = cityInputText,
                        onValueChange = { newText ->
                            cityInputText = newText
                        }, label = {
                            Text(text = "City")
                        },
                        singleLine = true,
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .align(Alignment.CenterHorizontally),

                        textStyle = TextStyle(color = TextLight, fontSize = 26.sp),
                        trailingIcon = {
                            IconButton(onClick = { mainScreenViewModel.getWeather(cityInputText) }) {
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
                    text = "${state.tempC} C",
                    style = TextStyle(fontSize = 65.sp),
                    color = TextLight
                )
                Text(
                    modifier = Modifier.padding(1.dp),
                    text = state.condition.text,
                    style = TextStyle(fontSize = 22.sp),
                    color = TextLight
                )

            }
        }
    }


}

