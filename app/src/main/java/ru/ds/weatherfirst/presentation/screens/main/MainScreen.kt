package ru.ds.weatherfirst.presentation.screens.main

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.ds.weatherfirst.R
import ru.ds.weatherfirst.domain.model.Current
import ru.ds.weatherfirst.presentation.screens.HomeViewModel
import ru.ds.weatherfirst.presentation.screens.days.CustomTextItem
import ru.ds.weatherfirst.presentation.screens.navigation.Screen
import ru.ds.weatherfirst.presentation.screens.utils.translateCondition
import ru.ds.weatherfirst.presentation.screens.uv_screen.CustomInfoButton
import ru.ds.weatherfirst.presentation.theme.BlueLight

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
    //mainScreenViewModel.getWeather("default")
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 2.dp)
                .alpha(0.94f),
            backgroundColor = BlueLight,
            elevation = 0.dp,
            shape = RoundedCornerShape(topEnd = 2.dp,
                topStart = 2.dp, bottomEnd = 8.dp, bottomStart = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, start = 4.dp, end = 10.dp)
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.End,
                ) {
                    CustomTextItem(text = "${stringResource(id = R.string.last_update)} ${state.lastUpdated}")
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f),
                        horizontalAlignment = Alignment.Start
                    ) {
                        CustomTextItem(text = "${stringResource(id = R.string.humidity)} ${state.humidity}%")
                        state.condition?.text?.let { translateCondition(param = it) }
                            ?.let { CustomTextItem(text = it) }
                        CustomTextItem(text = "${stringResource(id = R.string.uv)} ${state.uv}")
                        CustomTextItem(text = "${stringResource(id = R.string.wind)} ${state.wind_kph} ${stringResource(id = R.string.kph)}")
                        CustomTextItem(text = "${stringResource(id = R.string.wind_degree)} ${state.wind_degree}")
                    }
                    Column(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .weight(1f),
                        horizontalAlignment = Alignment.End
                    ) {
                        CustomTextItem(text = "${stringResource(id = R.string.feels_like)} ${state.feelslikeC.toInt()}°C")
                        CustomTextItem(text = "${stringResource(id = R.string.cloud)} ${state.cloud}%")
                        CustomTextItem(text = "${stringResource(id = R.string.temp)} ${state.tempF.toInt()}F")
                        CustomTextItem(text = "${stringResource(id = R.string.wind_direction)} ${state.wind_dir}")
                        CustomTextItem(text = "${stringResource(id = R.string.pressure)} ${state.pressure_in} in")
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))

                CustomInfoButton(
                    text = stringResource(id = R.string.search_btn),
                    navController = navController,
                    icon = painterResource(id = R.drawable.ic_baseline_search_24),
                    Screen.Search.route
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}






