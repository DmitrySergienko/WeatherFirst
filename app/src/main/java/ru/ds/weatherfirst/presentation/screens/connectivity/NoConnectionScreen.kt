package ru.ds.weatherfirst.presentation.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.ds.weatherfirst.R
import ru.ds.weatherfirst.presentation.screens.main.fontFamily
import ru.ds.weatherfirst.presentation.theme.WeatherFirstTheme

@Composable
fun NoConnectionScreen() {

    WeatherFirstTheme {
        Image(
            painter = painterResource(id = R.drawable.ic_back_new),
            contentDescription = "imageBack",
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.5f),
            contentScale = ContentScale.FillBounds
        )
        Card(
            modifier = Modifier
                .fillMaxSize(),
            backgroundColor = Color.Transparent,

        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 60.dp, start = 40.dp, end = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Image(
                    painter =painterResource(id = R.drawable.ic_off_line),
                    contentDescription = stringResource(id = R.string.off_line),)

                Text(
                    modifier = Modifier.padding(top = 4.dp, bottom = 4.dp, start = 16.dp),
                    textAlign = TextAlign.Center,
                    text = stringResource(id = R.string.no_connection),
                    style = TextStyle(fontSize = 20.sp),
                    color = Color.Black,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Normal,)
            }
        }
    }
}
