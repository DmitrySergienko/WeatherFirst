package ru.ds.weatherfirst.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ru.ds.weatherfirst.ui.theme.BlueLight

@Composable
fun RecyclerItemScreen(cityName: String)  {

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

            Text(
                modifier = Modifier.padding(15.dp),
                text = "$cityName",
                style = TextStyle(fontSize = 25.sp),
                color = Color.White

            )


            AsyncImage(
                model = "https://cdn.weatherapi.com/weather/64x64/day/113.png",
                contentDescription = "imageIcon",
                modifier = Modifier
                    .size(45.dp)
                    .padding(top = 14.dp, end = 2.dp)
            )
        }
    }
}