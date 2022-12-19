package ru.ds.weatherfirst.presentation.screens.uv_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ru.ds.weatherfirst.R
import ru.ds.weatherfirst.presentation.screens.main.fontFamily
import ru.ds.weatherfirst.presentation.theme.WeatherFirstTheme
import ru.ds.weatherfirst.ui.screens.UVIndicator

@Composable
fun UV_screen(
    navController: NavController,
    uv:String
) {
    WeatherFirstTheme {
        Image(
            painter = painterResource(id = R.drawable.ic_back_new),
            contentDescription = "imageBack",
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.94f),
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
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
            uvScreenTextItem( R.string.uv_screen_title_one)
            uvScreenTextItem(R.string.uv_chapter_one)

        }
    }
}

@Composable
fun uvScreenTextItem(text: Int){
    Text(
        text = stringResource(text),
        color = MaterialTheme.colors.primary,
        style = TextStyle(fontSize = 18.sp),
        textAlign = TextAlign.Center,
        modifier = Modifier
            .alpha(0.7f),
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
    )
}

@Composable
fun uVComment(uv: Double):String{
    val text = when(uv.toInt()){
        in 0..2 -> stringResource(id = R.string.low_hazard)
        in 3..6 -> stringResource(id = R.string.medium_hazard)
        in 7..9 -> stringResource(id = R.string.high_hazard)
        in 10..12 -> stringResource(id = R.string.extreme_hazard)
        else -> {stringResource(id = R.string.no_internet)}
    }
    return text
}
