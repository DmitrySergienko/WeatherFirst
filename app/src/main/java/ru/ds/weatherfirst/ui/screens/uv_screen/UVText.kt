package ru.ds.weatherfirst.ui.screens.uv_screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.ds.weatherfirst.ui.screens.HomeViewModel

@Composable
fun UVText() {
    //viewModel
    val uvLiveData = hiltViewModel<HomeViewModel>()
    val state by uvLiveData.stateMain.collectAsState()

// вызываем метод который подкачивает liveData
    uvLiveData.getWeather("Dubai")


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(0.7f)
            .padding(bottom = 5.dp),

    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,

        ) {
            Text(
                text = "UV ${uvComment(state.uv)}",
                color = MaterialTheme.colors.primary,
                style = TextStyle(fontSize = 45.sp),
                textAlign = TextAlign.Center
                //fontSize = MaterialTheme.typography.h2.fontSize,
                //fontWeight = FontWeight.Bold,

                )
        }
    }
}
fun uvComment(uv: Double):String{
    val text = when(uv.toInt()){
        in 0..3 -> "Low Hazard: No sun protection required"
        in 4..6 -> "Medium hazard class: use protective equipment (glasses, hat, shirts, trousers)"
        in 7..9 -> "High hazard class: try not to be in the sun, use sunscreen"
        in 10..12 -> "Extreme hazard class: look for shade, it is better to be indoors"
        else -> {"No internet. Please connect to clarify actual UV level."}
    }
return text
}
