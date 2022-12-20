package ru.ds.weatherfirst.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.ds.weatherfirst.presentation.screens.TabLayout
import ru.ds.weatherfirst.presentation.screens.backgroundImage
import ru.ds.weatherfirst.presentation.screens.main.MainScreenHoist
import ru.ds.weatherfirst.presentation.theme.WeatherFirstTheme

@Composable
fun MyProject(
    navController: NavController
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, start = 4.dp, end = 4.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                TabLayout(navController)
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                MainScreenHoist(navController)
            }
        }
    }
}
