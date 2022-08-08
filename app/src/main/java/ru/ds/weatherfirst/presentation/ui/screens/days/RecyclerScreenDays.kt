package ru.ds.weatherfirst.presentation.ui.screens.days

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.ds.weatherfirst.presentation.ui.screens.HomeViewModel

@Composable
fun RecyclerScreenDays() {

    val daysLiveData = hiltViewModel<HomeViewModel>()
    val stateDays by daysLiveData.stateDay.collectAsState()



    LazyColumn(contentPadding = PaddingValues(bottom = 20.dp)
    ){
        items(stateDays){ item->
            RecyclerItemScreenDays(item)

        }
    }

}