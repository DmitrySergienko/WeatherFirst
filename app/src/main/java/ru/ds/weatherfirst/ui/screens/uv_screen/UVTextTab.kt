//package ru.ds.weatherfirst.ui.screens.uv_screen
//
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.alpha
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.hilt.navigation.compose.hiltViewModel
//import ru.ds.weatherfirst.ui.screens.HomeViewModel
//
//@Composable
//fun UVTextTab() {
//    //viewModel
//    val uvLiveData = hiltViewModel<HomeViewModel>()
//    val state by uvLiveData.stateMain.collectAsState()
//
//
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .alpha(0.7f)
//            .padding(bottom = 5.dp),
//
//        ) {
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.Center,
//
//            ) {
//            Text(
//                text = "UV ${uvComment(state.uv)}",
//                color = MaterialTheme.colors.primary,
//                style = TextStyle(fontSize = 18.sp),
//                textAlign = TextAlign.Center
//                //fontSize = MaterialTheme.typography.h2.fontSize,
//                //fontWeight = FontWeight.Bold,
//
//            )
//        }
//    }
//}
//
//
