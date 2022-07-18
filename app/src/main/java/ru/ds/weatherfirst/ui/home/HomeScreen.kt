package ru.ds.weatherfirst.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import ru.ds.weatherfirst.data.api.model.Forecastday
import ru.ds.weatherfirst.data.api.model.Weather
import ru.ds.weatherfirst.ui.theme.BlueLight
import ru.ds.weatherfirst.ui.theme.WeatherFirstTheme

@Composable
fun HomeScreen() {
    val homeViewModel = viewModel(modelClass = HomeViewModel::class.java)
    val state by homeViewModel.state.collectAsState()


    LazyColumn {
        if (state.isEmpty()) {
            item {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(align = Alignment.Center)
                )
            }
            items(state) { name: Forecastday ->
                TestCard(name)

            }
        }
    }
}


@Composable
fun TestCard(name:Forecastday) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(0.9f)
            .padding(bottom = 5.dp),
        backgroundColor = BlueLight,
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            //Text(text="SomeText1: ${forecast}")
            Text(text = "SomeText1: ")
            //Text(text="SomeText2: ${weather.current.cloud}")
        }
    }
}
/*
@Preview
@Composable
fun TestCardPreview() {
    WeatherFirstTheme {
        TestCard(name = "Forecastday()")
    }
}*/

@Preview
@Composable
fun HomeScreenPreview() {
    WeatherFirstTheme {
        HomeScreen()
    }
}

@Composable
fun WeatherImageCard(weather: Weather) {
    val imagePainter = rememberImagePainter(data = weather.current.condition.icon)
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.padding(16.dp)
    )
    {
        Box {
            /*  Image(
                  painter = imagePainter, contentDescription = null,
                  modifier = Modifier
                      .fillMaxWidth()
                      .height(50.dp),
                  contentScale = ContentScale.FillBounds
              )*/
            Surface(
                color = MaterialTheme.colors.onSurface.copy(alpha = .3f),
                modifier = Modifier.align(Alignment.BottomCenter),
                contentColor = MaterialTheme.colors.surface
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    Text(text = "SomeText1: ${weather.current.tempC}")
                    Text(text = "SomeText2: ${weather.current.cloud}")

                }
            }
        }
    }

}
