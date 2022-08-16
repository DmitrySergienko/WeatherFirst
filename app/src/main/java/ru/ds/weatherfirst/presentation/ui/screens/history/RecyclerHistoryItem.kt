package ru.ds.weatherfirst.presentation.ui.screens.history

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.room.Room
import ru.ds.weatherfirst.data.db.HistoryDatabase
import ru.ds.weatherfirst.presentation.ui.theme.WeatherFirstTheme

@Composable
fun RecyclerHistoryItem(navController: NavController) {


    //====Database

    val db =
        Room.databaseBuilder(LocalContext.current, HistoryDatabase::class.java, "new_db").build()
    val dao = db.historyDao()

    val result by dao.readAll().collectAsState(initial = emptyList())
    val listHistory = mutableListOf<String>()

    // add to list from db
    for (i in result) listHistory.add(i.name)

    //============

    WeatherFirstTheme() {

        LazyColumn(
            contentPadding = PaddingValues(bottom = 20.dp)
        ) {

            items(listHistory) { name ->
                //для каждого элемента мы Запускаем:
                HistoryItemScreen(history = name, navController)

            }
        }
    }
}