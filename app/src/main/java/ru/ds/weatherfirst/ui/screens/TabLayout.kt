package ru.ds.weatherfirst.ui.screens

import android.graphics.Color.alpha
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.ds.weatherfirst.ui.theme.BlueLight
import ru.ds.weatherfirst.ui.theme.WeatherFirstTheme

@Composable
fun TabLayout() {

    val tabList = listOf("HOURS", "DAYS")

    Column(
        modifier = Modifier
            .padding(start = 2.dp, end = 2.dp)
            .clip(RoundedCornerShape(5.dp)
            )
    ) {
        TabRow(
            selectedTabIndex = 0,
            indicator = {},
            backgroundColor = BlueLight,
            modifier = Modifier.alpha(0.9f)
        ) {
            //идет перебор списка, для каждого элемнета выводит "text" и Tab
            tabList.forEachIndexed { index, text ->
                Tab(
                    selected = false,
                    onClick = {},
                    text = {
                        Text(text = text, style = TextStyle(color = Color.White))
                    }
                )
            }
        }
    }

}

@Preview
@Composable
fun TabLayoutPreview() {
    WeatherFirstTheme {
        TabLayout()
    }

}