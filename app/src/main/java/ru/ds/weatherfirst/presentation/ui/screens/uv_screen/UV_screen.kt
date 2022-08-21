package ru.ds.weatherfirst.presentation.ui.screens.uv_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ru.ds.weatherfirst.R
import ru.ds.weatherfirst.presentation.ui.theme.WeatherFirstTheme
import ru.ds.weatherfirst.ui.screens.UVIndicator

@Composable
fun UV_screen(
    navController: NavController,
    uv:String
) {

//viewModel
//    val uvLiveData = hiltViewModel<HomeViewModel>()
//    val state by uvLiveData.stateMain.collectAsState()


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
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // var value by rememberSaveable { mutableStateOf(0) }
            UVIndicator(indicatorValue = uv.toInt())
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,

                ) {
                Text(
                    text = uVComment(uv = uv.toDouble()),
                    color = MaterialTheme.colors.primary,
                    style = TextStyle(fontSize = 40.sp),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .alpha(0.7f)
                    //fontSize = MaterialTheme.typography.h2.fontSize,
                    //fontWeight = FontWeight.Bold,

                )
            }

        }
    }

}
@Composable
fun uVComment(uv: Double):String{
    val text = when(uv.toInt()){
        in 0..3 -> stringResource(id = R.string.low_hazard)
        in 4..6 -> stringResource(id = R.string.medium_hazard)
        in 7..9 -> stringResource(id = R.string.high_hazard)
        in 10..12 -> stringResource(id = R.string.extreme_hazard)
        else -> {stringResource(id = R.string.no_internet)}
    }
    return text
}
