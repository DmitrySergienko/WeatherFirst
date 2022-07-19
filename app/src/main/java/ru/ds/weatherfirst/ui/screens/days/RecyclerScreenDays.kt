package ru.ds.weatherfirst.ui.screens.days

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.ds.weatherfirst.data.api.model.Forecastday

@Composable
fun RecyclerScreenDays() {

    val daysLiveData = viewModel(modelClass = DateViewModel::class.java)
    val stateDays by daysLiveData.stateDay.collectAsState()

    LazyColumn(contentPadding = PaddingValues(bottom = 20.dp)){
        items(stateDays){ item: Forecastday ->  
            RecyclerItemScreenDays(item)

        }
    }

}