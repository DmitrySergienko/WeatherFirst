package ru.ds.weatherfirst.presentation.screens.main

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.ds.weatherfirst.R
import ru.ds.weatherfirst.domain.model.Current
import ru.ds.weatherfirst.presentation.screens.HomeViewModel
import ru.ds.weatherfirst.presentation.screens.navigation.Screen
import ru.ds.weatherfirst.presentation.screens.utils.translateCondition
import ru.ds.weatherfirst.presentation.screens.uv_screen.CustomInfoButton
import ru.ds.weatherfirst.presentation.theme.BlueLight
import ru.ds.weatherfirst.presentation.theme.TextLight

val fontFamily = FontFamily(
    Font(R.font.poppins_black, FontWeight.Black),
    Font(R.font.poppins_bold, FontWeight.Bold),
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_thin, FontWeight.Thin)
)

//вытаскиваем переменные в отедльную функцию Hoist, чтобы MainScreen сделать stateless
@Composable
fun MainScreenHoist(
    navController: NavController
) {
    val mainScreenViewModel = hiltViewModel<HomeViewModel>()
    val state by mainScreenViewModel.stateMain.collectAsState()

    MainScreen(
        navController = navController,
        mainScreenViewModel = mainScreenViewModel,
        state = state
    )
}

@Composable
fun MainScreen(
    navController: NavController,
    mainScreenViewModel: HomeViewModel,
    state: Current
) {
    mainScreenViewModel.getWeather("default")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(2.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.94f),
            backgroundColor = BlueLight,
            elevation = 0.dp,
            shape = RoundedCornerShape(4.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, start = 20.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f),
                        horizontalAlignment = Alignment.Start
                    ) {
                        MainScreenTextItem(text = "${stringResource(id = R.string.humidity)} ${state.humidity}%")
                        state.condition?.text?.let { translateCondition(param = it) }
                            ?.let { MainScreenTextItem(text = it) }
                        MainScreenTextItem(text = "${stringResource(id = R.string.uv)} ${state.uv}")
                        MainScreenTextItem(text = "${stringResource(id = R.string.wind)} ${state.wind_kph} ${stringResource(id = R.string.kph)}")
                        MainScreenTextItem(text = "${stringResource(id = R.string.wind_degree)} ${state.wind_degree}")
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f),
                        horizontalAlignment = Alignment.Start
                    ) {
                        MainScreenTextItem(text = "${stringResource(id = R.string.feels_like)} ${state.feelslikeC.toInt()}°C")
                        MainScreenTextItem(text = "${stringResource(id = R.string.cloud)} ${state.cloud}%")
                        MainScreenTextItem(text = "${stringResource(id = R.string.temp)} ${state.tempF.toInt()}F")
                        MainScreenTextItem(text = "${stringResource(id = R.string.wind_direction)} ${state.wind_dir}")
                        MainScreenTextItem(text = "${stringResource(id = R.string.pressure)} ${state.pressure_in} in")
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))

                CustomInfoButton(
                    text = stringResource(id = R.string.search_btn),
                    navController = navController,
                    icon = painterResource(id = R.drawable.ic_baseline_search_24),
                    Screen.Search.route
                )

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
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Normal,
                        style = TextStyle(fontSize = 16.sp),
                        color = TextLight
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreenTextItem(text: String) {
    Text(
        modifier = Modifier.padding(end = 4.dp),
        text = text,
        style = TextStyle(fontSize = 18.sp),
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        color = Color.Black
    )
}





