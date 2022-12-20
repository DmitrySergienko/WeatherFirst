package ru.ds.weatherfirst.presentation.screens.days

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import ru.ds.weatherfirst.R
import ru.ds.weatherfirst.domain.model.Forecastday
import ru.ds.weatherfirst.presentation.screens.main.fontFamily
import ru.ds.weatherfirst.presentation.screens.utils.translateCondition
import ru.ds.weatherfirst.presentation.theme.BlueLight
import ru.ds.weatherfirst.presentation.theme.TextLight


@Composable
fun RecyclerItemScreenDays(forecastday: Forecastday) {

    val imagePainter = rememberAsyncImagePainter(model = "https:${forecastday.day.condition.icon}")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(0.9f)
            .padding(bottom = 4.dp, top = 4.dp),
        backgroundColor = BlueLight,
        elevation = 0.dp,
        shape = RoundedCornerShape(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                CustomTextItem(text = "${stringResource(id = R.string.date)} ${forecastday.date}")
                CustomTextItem(text = "${stringResource(id = R.string.average)} ${forecastday.day.avgtempC.toInt()}°C")
                CustomTextItem(text = "${stringResource(id = R.string.min)} ${forecastday.day.mintempC.toInt()}°C")
                CustomTextItem(text = "${stringResource(id = R.string.max)} ${forecastday.day.maxtempC.toInt()}°C")
                CustomTextItem(text = translateCondition(param = forecastday.astro.moonPhase))
            }
            Column(modifier = Modifier.weight(1f)) {
                CustomTextItem(text = "${stringResource(id = R.string.wind)} ${forecastday.day.maxwindKph} ${stringResource(id = R.string.kph)}")
                CustomTextItem(text = translateCondition(param = forecastday.day.condition.text))
                CustomTextItem(text = "${stringResource(id = R.string.sunrise)} ${forecastday.astro.sunrise}")
                CustomTextItem(text = "${stringResource(id = R.string.sunset)} ${forecastday.astro.sunset}")
            }
            Image(
                painter = imagePainter, contentDescription = null,
                modifier = Modifier
                    .weight(0.4f)
                    .size(65.dp)
                    .padding(top = 14.dp, end = 4.dp),
                contentScale = ContentScale.FillBounds
            )
        }
    }
}
@Composable
fun CustomTextItem(text: String){
    Text(
        modifier = Modifier.padding(top = 4.dp, bottom = 4.dp, start = 16.dp),
        text = text,
        style = TextStyle(fontSize = 14.sp),
        color = TextLight,
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
    )
}

