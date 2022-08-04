package ru.ds.weatherfirst.ui.screens.uv_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.ds.weatherfirst.R
import ru.ds.weatherfirst.ui.screens.navigation.Screen
import ru.ds.weatherfirst.ui.theme.WeatherFirstTheme

@Composable
fun UV_screen(
    navController: NavController
) {
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
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .clickable {
                        navController.navigate(Screen.UVscreen.route){
                            popUpTo(Screen.UVscreen.route){
                                inclusive = true
                            }
                        }
                    },
                text = "Any Test",
                color = MaterialTheme.colors.primary,
                fontSize = MaterialTheme.typography.h2.fontSize,
                fontWeight = FontWeight.Bold,

                )
        }


    }
}

@Preview(showBackground = true)
@Composable
fun UV_screen_preview() {
    UV_screen(navController = rememberNavController())
}