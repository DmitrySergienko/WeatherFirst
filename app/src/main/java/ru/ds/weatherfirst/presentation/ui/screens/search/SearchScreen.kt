package ru.ds.weatherfirst.presentation.ui.screens.search


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.room.Room
import coil.compose.AsyncImage
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.ds.weatherfirst.R
import ru.ds.weatherfirst.data.db.HistoryDatabase
import ru.ds.weatherfirst.data.db.databaseSan.TestDB
import ru.ds.weatherfirst.presentation.ui.screens.HomeViewModel
import ru.ds.weatherfirst.presentation.ui.screens.TabLayout
import ru.ds.weatherfirst.presentation.ui.screens.navigation.Screen
import ru.ds.weatherfirst.presentation.ui.theme.BlueLight
import ru.ds.weatherfirst.presentation.ui.theme.TextLight
import ru.ds.weatherfirst.presentation.ui.theme.WeatherFirstTheme

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun SearchScreen(navController: NavController, history: String?) {


    //====Database
    val db =
        Room.databaseBuilder(LocalContext.current, HistoryDatabase::class.java, "new_db").build()
    val dao = db.historyDao()
    //============

    var city by rememberSaveable { mutableStateOf("") }

    val mainScreenViewModel = hiltViewModel<HomeViewModel>()
    val state by mainScreenViewModel.stateMain.collectAsState()

    //Если пришел агрумент из Истории запускаем поиск
    if (history?.isNotEmpty() == true && history != "{history_argument}") mainScreenViewModel.getWeather(
        history
    )
    Log.d("VVV", history.toString())

    //mainScreenViewModel.getWeather(city)
    WeatherFirstTheme {
        Image(
            painter = painterResource(id = R.drawable.ic_back_new),
            contentDescription = "imageBack",
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.5f),
            contentScale = ContentScale.FillBounds
        )

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
                                    navController.navigate(
                                        route = Screen.UVscreen.passUVARG(
                                            state.uv.toInt()
                                        )
                                    )
                                    //если просто открыть окно не закидывая аргументов
                                    //navController.navigate(route = Screen.UVscreen.route)
                                }
                        )

                        Column(
                            modifier = Modifier
                                .padding(end = 5.dp)
                                .weight(1f),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(top = 6.dp, end = 5.dp),
                                text = "Feels like ${state.feelslikeC.toInt()}°C",
                                style = TextStyle(fontSize = 22.sp),
                                color = TextLight

                            )
//                            Text(
//                                modifier = Modifier
//                                    .padding(top = 6.dp, end = 5.dp),
//                                text = "BACK",
//                                style = TextStyle(fontSize = 18.sp),
//                                color = TextLight,
//                            )
                        }
                    }

//=============
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 2.dp, bottom = 1.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {

                        Text(
                            modifier = Modifier
                                .padding(top = 1.dp, bottom = 15.dp, end = 5.dp),
                            text = "${state.tempC.toInt()}°C",
                            style = TextStyle(fontSize = 65.sp),
                            color = TextLight
                        )
                        if (history?.isNotEmpty() == true && history != "{history_argument}") {
                            Text(
                                modifier = Modifier
                                    .padding(top = 18.dp, end = 15.dp),
                                text = history,
                                style = TextStyle(fontSize = 18.sp),
                                color = TextLight,
                            )
                        } else {
                            Text(
                                modifier = Modifier
                                    .padding(top = 18.dp, end = 15.dp),
                                text = "",
                                style = TextStyle(fontSize = 18.sp),
                                color = TextLight,
                            )
                        }

                    }

                    //===Search screen
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()

                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 2.dp, bottom = 1.dp),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            OutlinedTextField(
                                value = city,
                                onValueChange = { newCity -> city = newCity },
                                label = { Text(text = "Search") },
                                placeholder = { Text(text = "Enter city") },
                                singleLine = true,
                                modifier = Modifier.padding(start = 5.dp, bottom = 5.dp),
                                textStyle = TextStyle(color = TextLight, fontSize = 26.sp),
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                                keyboardActions = KeyboardActions(
                                    onSearch = {
                                        if (city.isNotEmpty()) {
                                            mainScreenViewModel.getWeather(city)
                                        } else {
                                            mainScreenViewModel.getWeather("default")
                                        }
                                    }
                                )
                            )

                            Column() {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_baseline_history_24),
                                    contentDescription = "Clean_image",
                                    modifier = Modifier
                                        .padding(start = 1.dp, top = 26.dp, end = 10.dp)
                                        .size(30.dp)
                                        .alpha(0.7f)
                                        .clickable {
                                            navController.navigate(route = Screen.History.route)
                                            GlobalScope.launch {
                                                dao.insertItem(TestDB(0, city)) // save to db

                                            }

                                        },
                                )
                                Text(
                                    modifier = Modifier
                                        .padding(start = 1.dp, top = 3.dp, end = 4.dp),
                                    text = "History",
                                    style = TextStyle(fontSize = 10.sp),
                                    color = TextLight
                                )
                            }

                        }
                    }

//======Detailed items
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, bottom = 1.dp),
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
                                text = "Humidity ${state.humidity}%",
                                style = TextStyle(fontSize = 16.sp),
                                color = TextLight
                            )
                            Text(
                                modifier = Modifier
                                    .padding(1.dp),
                                text = "UV ${state.uv}",
                                style = TextStyle(fontSize = 16.sp),
                                color = TextLight
                            )
                            Text(
                                modifier = Modifier
                                    .padding(1.dp),
                                text = state.condition.text,
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
                                    .size(85.dp)
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
                                text = "Temp ${state.tempF.toInt()}F",
                                style = TextStyle(fontSize = 16.sp),
                                color = TextLight
                            )
                            Text(
                                modifier = Modifier
                                    .padding(1.dp),
                                text = "Cloud ${state.cloud}%",
                                style = TextStyle(fontSize = 16.sp),
                                color = TextLight
                            )

                        }
                    }
//=============


//=============


//=============

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
                            text = "Last update: ${state.lastUpdated}",
                            style = TextStyle(fontSize = 20.sp),
                            color = TextLight
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(1.dp))
            TabLayout()
        }

    }
}