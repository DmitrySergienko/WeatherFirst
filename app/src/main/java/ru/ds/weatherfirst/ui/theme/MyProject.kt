package ru.ds.weatherfirst.ui.theme

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.ds.weatherfirst.R
import ru.ds.weatherfirst.ui.screens.MainScreen
import ru.ds.weatherfirst.ui.screens.RecyclerScreen
import ru.ds.weatherfirst.ui.screens.TabLayout

@Composable
fun MyProject(context: Context) {
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

            MainScreen(context)
            Spacer(modifier = Modifier.height(1.dp))
            //TabLayout()
            RecyclerScreen()

        }
    }
}
