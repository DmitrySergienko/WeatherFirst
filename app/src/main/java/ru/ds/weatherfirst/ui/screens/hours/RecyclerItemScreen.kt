package ru.ds.weatherfirst.ui.screens

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
import ru.ds.weatherfirst.data.api.model.Hour
import ru.ds.weatherfirst.ui.theme.BlueLight
import ru.ds.weatherfirst.ui.theme.TextLight


@Composable
fun RecyclerItemScreen(hour: Hour) {

    val imagePainter = rememberAsyncImagePainter(model = "https:${hour.condition.icon}")

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

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    modifier = Modifier
                        .padding(top = 5.dp, bottom = 5.dp, start = 15.dp),
                    text = "Temperature: ${hour.tempC.toInt()}°C",
                    style = TextStyle(fontSize = 15.sp),
                    color = TextLight

                )
                Text(
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp, start = 15.dp),
                    text = "Feels like ${hour.feelslikeC.toInt()}°C",
                    style = TextStyle(fontSize = 15.sp),
                    color = TextLight

                )
                Text(
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp, start = 15.dp),
                    text = "Wind ${hour.windKph} Kph",
                    style = TextStyle(fontSize = 15.sp),
                    color = TextLight

                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp, start = 15.dp),
                    text = hour.condition.text,
                    style = TextStyle(fontSize = 15.sp),
                    color = TextLight

                )
                Text(
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp, start = 15.dp),
                    text = "Humidity ${hour.humidity}%",
                    style = TextStyle(fontSize = 15.sp),
                    color = TextLight

                )

                Text(

                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp, start = 15.dp),
                    text = "Time ${hour.time.takeLast(5)}",
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
