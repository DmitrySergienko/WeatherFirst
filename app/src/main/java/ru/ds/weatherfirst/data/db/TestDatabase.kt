package ru.ds.weatherfirst.data.db

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.ds.weatherfirst.presentation.MainViewModel
import ru.ds.weatherfirst.presentation.ui.theme.WeatherFirstTheme

@Composable
fun TestDatabase(mainViewModel: MainViewModel) {

    WeatherFirstTheme() {


        val result by mainViewModel.readAll.collectAsState(initial = emptyList())

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if(result.isNotEmpty()){
                for (city_history in result){
                    Text(
                        text = city_history.name,
                        fontSize = MaterialTheme.typography.h4.fontSize
                    )
                }
            }
            else{
                Text(
                    text = "empty database",
                    fontSize = MaterialTheme.typography.h4.fontSize
                )
            }
        }
    }
}
