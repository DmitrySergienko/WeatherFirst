package ru.ds.weatherfirst.presentation.ui.screens.days

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import ru.ds.weatherfirst.domain.model.Forecastday
import ru.ds.weatherfirst.presentation.ui.theme.BlueLight
import ru.ds.weatherfirst.presentation.ui.theme.TextLight


@Composable
fun RecyclerItemScreenDays(forecastday: Forecastday) {

    val imagePainter = rememberAsyncImagePainter(model = "https:${forecastday.day.condition.icon}")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(0.9f)
            .padding(bottom = 5.dp),
        backgroundColor = BlueLight,
        elevation = 0.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp, start = 15.dp),
                    text = "Date ${forecastday.date}",
                    style = TextStyle(fontSize = 15.sp),
                    color = TextLight

                )
                Text(
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp, start = 15.dp),
                    text = "Average t: ${forecastday.day.avgtempC.toInt()}°C",
                    style = TextStyle(fontSize = 15.sp),
                    color = TextLight

                )
                Text(
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp, start = 15.dp),
                    text = "Min t: ${forecastday.day.mintempC.toInt()}°C",
                    style = TextStyle(fontSize = 15.sp),
                    color = TextLight

                )
                Text(
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp, start = 15.dp),
                    text = "Max t: ${forecastday.day.maxtempC.toInt()}°C",
                    style = TextStyle(fontSize = 15.sp),
                    color = TextLight

                )
                Text(
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp, start = 15.dp),
                    text = "Moon phase: ${forecastday.astro.moonPhase}",
                    style = TextStyle(fontSize = 15.sp),
                    color = TextLight

                )

            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp, start = 15.dp),
                    text = "Wind: ${forecastday.day.maxwindKph} kph",
                    style = TextStyle(fontSize = 15.sp),
                    color = TextLight

                )
                Text(
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp, start = 15.dp),
                    text = forecastday.day.condition.text,
                    style = TextStyle(fontSize = 15.sp),
                    color = TextLight

                )
                Text(
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp, start = 15.dp),
                    text = "Sunrise ${forecastday.astro.sunrise}",
                    style = TextStyle(fontSize = 15.sp),
                    color = TextLight

                )
                Text(
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp, start = 15.dp),
                    text = "Sunset ${forecastday.astro.sunset}",
                    style = TextStyle(fontSize = 15.sp),
                    color = TextLight

                )

            }
            Image(
                painter = imagePainter, contentDescription = null,
                modifier = Modifier
                    .weight(0.4f)
                    .size(65.dp)
                    .padding(top = 14.dp, end = 5.dp),
                contentScale = ContentScale.FillBounds
            )

        }
    }

}
