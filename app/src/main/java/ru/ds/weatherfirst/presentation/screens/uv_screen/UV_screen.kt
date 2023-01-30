package ru.ds.weatherfirst.presentation.screens.uv_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ru.ds.weatherfirst.R
import ru.ds.weatherfirst.presentation.screens.backgroundImage
import ru.ds.weatherfirst.presentation.screens.main.fontFamily
import ru.ds.weatherfirst.presentation.screens.navigation.Screen
import ru.ds.weatherfirst.presentation.theme.BlueLight
import ru.ds.weatherfirst.presentation.theme.WeatherFirstTheme
import ru.ds.weatherfirst.ui.screens.UVIndicator

@Composable
fun UV_screen(
    navController: NavController,
    uv:String
) {
    WeatherFirstTheme {
        Image(
            painter = painterResource(backgroundImage()),
            contentDescription = "imageBack",
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.4f),
            contentScale = ContentScale.FillBounds
        )
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            backgroundColor = BlueLight,
            elevation = 0.dp,
            shape = RoundedCornerShape(4.dp)
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(modifier = Modifier
                    .fillMaxWidth(),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_baseline_arrow_back_ios_24),
                        contentDescription = "UV image",
                        modifier = Modifier
                            .padding(start = 20.dp, top = 20.dp, end = 20.dp)
                            .size(30.dp)
                            .clickable {
                                navController.navigate(route = Screen.Home.route)
                            }
                    )
                }

                UVIndicator(indicatorValue = uv.toInt())

                Text(
                    text = uVComment(uv = uv.toDouble()),
                    color = MaterialTheme.colors.primary,
                    style = TextStyle(fontSize = 18.sp),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                        .alpha(0.7f),
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Normal,
                )
                UVScreenTextItem( R.string.uv_screen_title_one)
                UVScreenTextItem(R.string.uv_chapter_one)
            }
        }

    }
}

@Composable
fun UVScreenTextItem(text: Int) {
    Text(
        text = stringResource(text),
        color = MaterialTheme.colors.primary,
        style = TextStyle(fontSize = 18.sp),
        textAlign = TextAlign.Center,
        modifier = Modifier
            .alpha(0.7f)
            .padding(start = 4.dp, end = 4.dp),
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
    )
}

@Composable
fun uVComment(uv: Double):String{
    val text = when(uv.toInt()){
        in 0..2 -> stringResource(id = R.string.low_hazard)
        in 3..5 -> stringResource(id = R.string.medium_hazard)
        in 6..7 -> stringResource(id = R.string.high_hazard)
        in 8..10 -> stringResource(id = R.string.extreme_hazard)
        in 11..15 -> stringResource(id = R.string.super_extreme_hazard)
        else -> {stringResource(id = R.string.no_internet)}
    }
    return text
}
