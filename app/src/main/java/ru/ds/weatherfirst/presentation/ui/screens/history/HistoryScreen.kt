package ru.ds.weatherfirst.presentation.ui.screens.history

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.room.Room
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.ds.weatherfirst.R
import ru.ds.weatherfirst.data.db.HistoryDatabase
import ru.ds.weatherfirst.presentation.ui.theme.TextLight
import ru.ds.weatherfirst.presentation.ui.theme.WeatherFirstTheme

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun HistoryScreen(navController: NavController) {

    //====Database

    val db =
        Room.databaseBuilder(LocalContext.current, HistoryDatabase::class.java, "new_db").build()
    val dao = db.historyDao()

    val result by dao.readAll().collectAsState(initial = emptyList())
    val listHistory = mutableListOf("")

    // add to list from db
    for (i in result) listHistory.add(i.name)

    //============

    WeatherFirstTheme {
        Image(
            painter = painterResource(id = R.drawable.ic_back_new),
            contentDescription = "imageBack",
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.5f),
            contentScale = ContentScale.FillBounds
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(0.9f)
                .padding(bottom = 5.dp, top = 5.dp, start = 5.dp, end = 5.dp),
            backgroundColor = Color.Transparent,
            elevation = 0.dp,
            shape = RoundedCornerShape(10.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp, bottom = 30.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.padding(top = 15.dp, bottom = 5.dp, start = 15.dp),
                        text = stringResource(id = R.string.search_history),
                        style = TextStyle(fontSize = 22.sp),
                        color = TextLight,
                        )
                    Text(
                        modifier = Modifier
                            .padding(top = 18.dp, bottom = 5.dp, end = 15.dp)
                            .clickable { GlobalScope.launch { dao.deleteAll() } },
                        text = stringResource(id = R.string.clean_all),
                        style = TextStyle(fontSize = 18.sp),
                        color = TextLight,
                    )
                }

                Spacer(modifier = Modifier.padding(10.dp))
                //MyDropMenu(navController)
                RecyclerHistoryItem(navController,hiltViewModel())


            }
        }

    }
}


