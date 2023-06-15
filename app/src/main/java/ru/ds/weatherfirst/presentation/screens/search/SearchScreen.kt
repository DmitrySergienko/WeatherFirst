package ru.ds.weatherfirst.presentation.screens.search


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import ru.ds.weatherfirst.presentation.screens.days.CustomTextItem
import ru.ds.weatherfirst.presentation.screens.main.fontFamily
import ru.ds.weatherfirst.presentation.screens.navigation.Screen
import ru.ds.weatherfirst.presentation.screens.utils.CustomInfoButton
import ru.ds.weatherfirst.presentation.theme.BlueLight
import ru.ds.weatherfirst.presentation.theme.TextLight
import ru.ds.weatherfirst.presentation.theme.WeatherFirstTheme

@OptIn(DelicateCoroutinesApi::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(navController: NavController, history: String?) {

    val context = LocalContext.current
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
                    .padding(4.dp),
                backgroundColor = BlueLight,
                elevation = 0.dp,
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
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
                                    .padding(top = 20.dp, end = 8.dp),
                                text = "${stringResource(id = R.string.temperature)} ${state.tempC.toInt()}°C",
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
                            CustomTextItem(text = history)
                        } else {
                            CustomTextItem(text = city)
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
                                            Toast.makeText(context,R.string.enter_city,Toast.LENGTH_SHORT).show()
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
                    Column(
                        modifier = Modifier
                            .padding(start = 5.dp, top = 10.dp, end = 5.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = "https:${state.condition?.icon}",
                            contentDescription = "imageIcon",
                            modifier = Modifier
                                .size(85.dp)
                                .padding(top = 1.dp, end = 2.dp)
                        )
                    }
                    CustomInfoButton(
                        text = stringResource(id = R.string.history),
                        navController = navController,
                        icon = painterResource(id = R.drawable.ic_baseline_history_24),
                        road = Screen.History.route,
                        state.uv.toInt()
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 4.dp, end = 4.dp)
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.Start,
                    ) {

                    }
                }
            }
            Spacer(modifier = Modifier.height(1.dp))
            TabLayout(navController)
        }
    }
}