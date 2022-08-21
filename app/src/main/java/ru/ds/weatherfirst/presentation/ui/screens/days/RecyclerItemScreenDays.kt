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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import ru.ds.weatherfirst.R
import ru.ds.weatherfirst.domain.model.Forecastday
import ru.ds.weatherfirst.presentation.ui.screens.utils.translateCondition
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
                    text = "${stringResource(id = R.string.date)} ${forecastday.date}",
                    style = TextStyle(fontSize = 15.sp),
                    color = TextLight

                )
                Text(
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp, start = 15.dp),
                    text = "${stringResource(id = R.string.average)} ${forecastday.day.avgtempC.toInt()}°C",
                    style = TextStyle(fontSize = 15.sp),
                    color = TextLight

                )
                Text(
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp, start = 15.dp),
                    text = "${stringResource(id = R.string.min)} ${forecastday.day.mintempC.toInt()}°C",
                    style = TextStyle(fontSize = 15.sp),
                    color = TextLight

                )
                Text(
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp, start = 15.dp),
                    text = "${stringResource(id = R.string.max)} ${forecastday.day.maxtempC.toInt()}°C",
                    style = TextStyle(fontSize = 15.sp),
                    color = TextLight

                )
                Text(
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp, start = 15.dp),
                    text = translateCondition(param = forecastday.astro.moonPhase),
                    style = TextStyle(fontSize = 15.sp),
                    color = TextLight

                )

            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp, start = 15.dp),
                    text = "${stringResource(id = R.string.wind)} ${forecastday.day.maxwindKph} ${stringResource(id = R.string.kph)}",
                    style = TextStyle(fontSize = 15.sp),
                    color = TextLight

                )
                Text(
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp, start = 15.dp),
                    text = translateCondition(param = forecastday.day.condition.text),
                    style = TextStyle(fontSize = 15.sp),
                    color = TextLight

                )
                Text(
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp, start = 15.dp),
                    text = "${stringResource(id = R.string.sunrise)} ${forecastday.astro.sunrise}",
                    style = TextStyle(fontSize = 15.sp),
                    color = TextLight

                )
                Text(
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp, start = 15.dp),
                    text = "${stringResource(id = R.string.sunset)} ${forecastday.astro.sunset}",
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
