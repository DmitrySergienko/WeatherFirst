package ru.ds.weatherfirst.presentation.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.room.Room
import ru.ds.weatherfirst.data.db.HistoryDatabase
import ru.ds.weatherfirst.presentation.ui.screens.navigation.Screen
import ru.ds.weatherfirst.presentation.ui.theme.TextLight

@Composable
fun MyDropMenu(
    navController: NavController
) {

    //====Database

    val db =
        Room.databaseBuilder(LocalContext.current, HistoryDatabase::class.java, "new_db").build()
    val dao = db.historyDao()

    val result by dao.readAll().collectAsState(initial = emptyList())
    val listHistory = mutableListOf("")

    // add to list from db
    for (i in result)  listHistory.add(i.name)

    //============


    var expanded by remember { mutableStateOf(false) }
    val items = listOf("Test A", "Test B", "Test C", "Test D", "Test E", "Test F")
    val disabledValue = "B"
    var selectedIndex by remember { mutableStateOf(0) }
    Box(
        Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Text(listHistory[selectedIndex],
            style = TextStyle(fontSize = 28.sp),
            color = TextLight,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { expanded = true })
                .background(Color.Transparent))


        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
        ) {
            listHistory.forEachIndexed { index, s ->
                DropdownMenuItem(onClick = {
                    selectedIndex = index
                    expanded = false

                    navController.navigate(
                        route = Screen.Search.passHistoryArg(
        //send selected arg to search screen
                        s
                        )
                    )

                }) {
                    val disabledText = if (s == disabledValue) {
                        " (Disabled)"
                    } else {
                        ""
                    }
                    Text(text = s + disabledText)
                }
            }
        }
    }
}

//@Preview
//@Composable
//fun DrobDownPreview(){
//    WeatherFirstTheme() {
//        MyDropMenu()
//    }
//
//}
