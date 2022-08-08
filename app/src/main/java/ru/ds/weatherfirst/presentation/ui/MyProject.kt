package ru.ds.weatherfirst.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.ds.weatherfirst.R
import ru.ds.weatherfirst.presentation.ui.screens.TabLayout
import ru.ds.weatherfirst.presentation.ui.screens.main.MainScreenHoist
import ru.ds.weatherfirst.presentation.ui.theme.WeatherFirstTheme

@Composable
fun MyProject(
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
                .fillMaxSize()
                .padding(top = 4.dp, start = 4.dp, end = 4.dp)
        ) {

            MainScreenHoist(navController)
            Spacer(modifier = Modifier.height(1.dp))
            TabLayout()


        }
    }
}
