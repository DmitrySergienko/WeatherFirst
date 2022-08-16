package ru.ds.weatherfirst.presentation.ui.screens.history

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.room.Room
import ru.ds.weatherfirst.data.db.HistoryDatabase
import ru.ds.weatherfirst.presentation.ui.screens.navigation.Screen
import ru.ds.weatherfirst.presentation.ui.theme.BlueLight
import ru.ds.weatherfirst.presentation.ui.theme.TextLight

@Composable
fun HistoryItemScreen(history: String, navController: NavController) {


    //====Database

    val db =
        Room.databaseBuilder(LocalContext.current, HistoryDatabase::class.java, "new_db").build()
    val dao = db.historyDao()

    val result by dao.readAll().collectAsState(initial = emptyList())
    val listHistory = mutableListOf("")

    // add to list from db
    for (i in result) listHistory.add(i.name)

    //============

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
                Text(
                    modifier = Modifier
                        .padding(top = 5.dp, bottom = 5.dp, start = 15.dp)
                        .clickable {
                            navController
                                .navigate(route = Screen.Search.passHistoryArg(history))
                                   },
                    text = history,
                    style = TextStyle(fontSize = 15.sp),
                    color = TextLight,
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    fontWeight = FontWeight.Light
                )
//                Image(
//                    painter = painterResource(id = R.drawable.ic_baseline_history_24),
//                    contentDescription = "Clean_image",
//                    modifier = Modifier
//                        .padding(start = 1.dp, top = 26.dp, end = 10.dp)
//                        .size(30.dp)
//                        .alpha(0.7f)
//                        .clickable {
//                            navController.navigate(route = Screen.History.route)
//                            GlobalScope.launch {
//
//                                dao.deleteItem() // save to db
//                            }
//                        },
//                )
            }
        }
    }
}