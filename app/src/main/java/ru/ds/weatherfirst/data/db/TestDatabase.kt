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
import androidx.compose.ui.platform.LocalContext
import androidx.room.Room
import ru.ds.weatherfirst.data.db.databaseSan.TestDB
import ru.ds.weatherfirst.presentation.MainViewModel
import ru.ds.weatherfirst.presentation.ui.theme.WeatherFirstTheme
import java.util.concurrent.Executors

@Composable
fun TestDatabase(
    mainViewModel: MainViewModel

) {

    WeatherFirstTheme() {

        val db = Room.databaseBuilder(LocalContext.current,HistoryDatabase::class.java,"new_db").build()
        val dao =db.historyDao()

        val list = listOf(
            TestDB(1,"test1"),
            TestDB(2,"test2"),
            TestDB(3,"test3"),
            TestDB(4,"test4"),


        )

        Executors.newSingleThreadExecutor().execute{
            //dao.insert(TestDB(5,"test1"))
            dao.deleteAll()
        }


        val result by mainViewModel.readAll.collectAsState(initial = emptyList())

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if(result.isNotEmpty()){
                for (new in result){
                    Text(
                        text = new.name,

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




