package ru.ds.weatherfirst.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import ru.ds.weatherfirst.R
import ru.ds.weatherfirst.domain.model.Hour
import ru.ds.weatherfirst.presentation.screens.days.CustomTextItem
import ru.ds.weatherfirst.presentation.screens.utils.translateCondition
import ru.ds.weatherfirst.presentation.theme.BlueLight


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
                CustomTextItem(text = "${stringResource(id = R.string.time)} ${hour.time.takeLast(5)}")
                CustomTextItem(text = "${stringResource(id = R.string.feels_like)} ${hour.feelslikeC.toInt()}°C")
                CustomTextItem(text = "${stringResource(id = R.string.wind)} ${hour.windKph} ${stringResource(id = R.string.kph)}")
                CustomTextItem(text = "${stringResource(id = R.string.uv)} ${hour.uv}")
            }
            Column(modifier = Modifier.weight(1f)) {
                CustomTextItem(text = "${stringResource(id = R.string.temperature)} ${hour.tempC.toInt()}°C")
                CustomTextItem(text = translateCondition(param = hour.condition.text))
                CustomTextItem(text = "${stringResource(id = R.string.humidity)} ${hour.humidity}%")
                CustomTextItem(text = "${stringResource(id = R.string.wind_direction)} ${hour.wind_dir}")
            }
            Column(
                modifier = Modifier.weight(0.4f)
            ) {
                Image(
                    painter = imagePainter, contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                        .padding(top = 14.dp, end = 5.dp),
                    contentScale = ContentScale.FillBounds
                )
            }
        }
    }
}
