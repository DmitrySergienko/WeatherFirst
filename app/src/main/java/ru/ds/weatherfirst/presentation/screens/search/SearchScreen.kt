package ru.ds.weatherfirst.presentation.screens.search


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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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
import ru.ds.weatherfirst.data.db.TestDB
import ru.ds.weatherfirst.presentation.screens.TabLayout
import ru.ds.weatherfirst.presentation.screens.backgroundImage
import ru.ds.weatherfirst.presentation.screens.main.MainScreenTextItem
import ru.ds.weatherfirst.presentation.screens.main.fontFamily
import ru.ds.weatherfirst.presentation.screens.navigation.Screen
import ru.ds.weatherfirst.presentation.screens.uv_screen.CustomInfoButton
import ru.ds.weatherfirst.presentation.theme.BlueLight
import ru.ds.weatherfirst.presentation.theme.TextLight
import ru.ds.weatherfirst.presentation.theme.WeatherFirstTheme

@OptIn(DelicateCoroutinesApi::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(navController: NavController, history: String?) {

    //====Database
    val db =
        Room.databaseBuilder(LocalContext.current, HistoryDatabase::class.java, "new_db").build()
    val dao = db.historyDao()
    //============

    var city by rememberSaveable { mutableStateOf("") }

    val mainScreenViewModel = hiltViewModel<ru.ds.weatherfirst.presentation.screens.HomeViewModel>()
    val state by mainScreenViewModel.stateMain.collectAsState()

    //dismiss keyboard
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    //Если пришел агрумент из Истории запускаем поиск
    if ((history?.isNotEmpty() == true) && (history != "{history_argument}")) mainScreenViewModel.getWeather(
        history
    )
    //set limit for char
    val maxChar = 19

    //mainScreenViewModel.getWeather(city)
    WeatherFirstTheme {
        Image(
            painter = painterResource(backgroundImage()),
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
                    .alpha(0.94f)
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
                            .padding(top = 4.dp, bottom = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_baseline_arrow_back_ios_24),
                            contentDescription = "UV image",
                            modifier = Modifier
                                .padding(start = 20.dp, top = 20.dp, end = 20.dp)
                                .size(30.dp)
                                .clickable {
                                    //открываем UV_screen и перекидываем туда аргументы
                                    // route = Screen.UVscreen.passUVARG(state.uv.toInt())
                                    //если просто открыть окно не закидывая аргументов
                                    navController.navigate(route = Screen.Home.route)
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
                                text = "${stringResource(id = R.string.feels_like)} ${state.feelslikeC.toInt()}°C",
                                style = TextStyle(fontSize = 22.sp),
                                color = TextLight,
                                fontFamily = fontFamily,
                                fontWeight = FontWeight.Normal,
                            )
                        }
                    }
//======Main Temperature
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 2.dp, bottom = 1.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        if (history?.isNotEmpty() == true && history != "{history_argument}" && city.isEmpty()) {
                            Text(
                                modifier = Modifier
                                    .padding(top = 18.dp, end = 15.dp),
                                text = history,
                                style = TextStyle(fontSize = 18.sp),
                                color = TextLight,
                                maxLines = 1,
                                fontFamily = fontFamily,
                                fontWeight = FontWeight.Normal,
                            )
                        } else {
                            Text(
                                modifier = Modifier
                                    .padding(top = 18.dp, end = 15.dp),
                                text = city,
                                style = TextStyle(fontSize = 18.sp),
                                color = TextLight,
                                fontFamily = fontFamily,
                                fontWeight = FontWeight.Normal,
                            )
                        }
                    }
//===Search field
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 2.dp, bottom = 1.dp),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            OutlinedTextField(
                                value = city,
                                onValueChange = { newCity ->
                                    if (newCity.length < maxChar) {
                                        city = newCity
                                    }
                                },
                                label = { Text(text = stringResource(id = R.string.search)) },
                                placeholder = {
                                    Text(
                                        text = stringResource(id = R.string.enter_city),
                                        maxLines = 1,
                                        fontFamily = fontFamily,
                                        fontWeight = FontWeight.Normal,
                                    )
                                },
                                singleLine = true,
                                modifier = Modifier.padding(start = 5.dp, bottom = 5.dp),
                                textStyle = TextStyle(
                                    color = TextLight,
                                    fontSize = 26.sp,
                                    fontFamily = fontFamily,
                                    fontWeight = FontWeight.Normal,
                                ),
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                                keyboardActions = KeyboardActions(
                                    onSearch = {
                                        if (city.isNotEmpty()) {
                                            // 1.run search
                                            mainScreenViewModel.getWeather(city)
                                            // 2.save to db
                                            GlobalScope.launch {
                                                if (city.isNotEmpty()) {
                                                    dao.insertItem(TestDB(0, city))
                                                }
                                            }
                                        } else {
                                            mainScreenViewModel.getWeather("default")
                                        }
                                        //dismiss keyboard
                                        keyboardController?.hide()
                                        focusManager.clearFocus()
                                    },
                                )
                            )
                        }
                    }
//======Detailed items
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 5.dp, top = 10.dp, end = 5.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 5.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            MainScreenTextItem(text = "${stringResource(id = R.string.humidity)} ${state.humidity}%")
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
                            MainScreenTextItem(text = "${stringResource(id = R.string.cloud)} ${state.cloud}%")
                            MainScreenTextItem(text = "${stringResource(id = R.string.temp)} ${state.tempF.toInt()}F")
                        }
                    }

                    CustomInfoButton(
                        text = stringResource(id = R.string.history),
                        navController =  navController,
                        icon = painterResource(id = R.drawable.ic_baseline_history_24),
                        road = Screen.History.route)

                    Spacer(modifier = Modifier.height(20.dp))

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
                            color = TextLight,
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.Normal,
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(1.dp))
            TabLayout(navController)
        }
    }
}