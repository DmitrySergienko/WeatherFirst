package ru.ds.weatherfirst.presentation.ui.screens.history

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.room.Room
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.ds.weatherfirst.R
import ru.ds.weatherfirst.data.db.HistoryDatabase
import ru.ds.weatherfirst.presentation.ui.screens.navigation.Screen
import ru.ds.weatherfirst.presentation.ui.theme.BlueLight
import ru.ds.weatherfirst.presentation.ui.theme.TextLight

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun HistoryItemScreen(history: String, navController: NavController) {


    //====Database

    val db =
        Room.databaseBuilder(LocalContext.current, HistoryDatabase::class.java, "new_db").build()
    val dao = db.historyDao()

    //val result by dao.readAll().collectAsState(initial = emptyList())

//    // add to list from db
//    val listHistory = mutableListOf("")
//    for (i in result) listHistory.add(i.name)
//
//    // add "id" to list from db
//    val listHistoryForDelete = mutableListOf<Int>()
//    for (i in result) listHistoryForDelete.add(i.id)

    //============

    //==========shimmer effect option =========
    val shimmentColors = listOf(
        BlueLight.copy(alpha = 0.7f),
        BlueLight.copy(alpha = 0.1f),
        BlueLight.copy(alpha = 0.4f),
    )

    val transition = rememberInfiniteTransition()
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing,
            ),
            repeatMode = RepeatMode.Reverse
        ),

        )
    val brush = Brush.linearGradient(
        colors = shimmentColors,
        start = Offset.Zero,
        end = Offset(
            x = translateAnim.value,
            y = translateAnim.value,
        )
    )

    //================

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
                modifier = Modifier
                    .weight(1f)
                    .background(brush)
            ) {
                Text(
                    modifier = Modifier
                        .padding(top = 5.dp, bottom = 5.dp, start = 15.dp)
                        .clickable {
                            navController.navigate(route = Screen.Search.passHistoryArg(history))
                        },
                    text = history,
                    style = TextStyle(fontSize = 15.sp),
                    color = TextLight,
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    fontWeight = FontWeight.Light
                )

            }

            Image(
                painter = painterResource(id = R.drawable.ic_baseline_clear_24),
                contentDescription = "Clean_image",
                modifier = Modifier
                    .padding(all = 10.dp)
                    .size(30.dp)
                    .alpha(0.7f)
                    .clickable {
                        //remove item from database
                        GlobalScope.launch {
                            val item = dao.getByName(history)
                            dao.deleteItem(item)
                        }

                    },
            )
        }
    }
}