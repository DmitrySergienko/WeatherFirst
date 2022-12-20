package ru.ds.weatherfirst.presentation.screens.uv_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import ru.ds.weatherfirst.R
import ru.ds.weatherfirst.presentation.screens.main.fontFamily
import ru.ds.weatherfirst.presentation.screens.navigation.Screen
import ru.ds.weatherfirst.presentation.screens.utils.MarqueeText
import ru.ds.weatherfirst.presentation.theme.BlueLight
import ru.ds.weatherfirst.presentation.theme.TextLight
import ru.ds.weatherfirst.presentation.theme.WeatherFirstTheme
import ru.ds.weatherfirst.ui.screens.UVIndicator

@Composable
fun UV_screenTab(
    navController:NavController
) {

//viewModel
    val uvLiveData = hiltViewModel<ru.ds.weatherfirst.presentation.screens.HomeViewModel>()
    val state by uvLiveData.stateMain.collectAsState()

    WeatherFirstTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.94f)
                    .padding(bottom = 2.dp, top = 4.dp),
                backgroundColor = BlueLight,
                elevation = 0.dp,
                shape = RoundedCornerShape(4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(modifier = Modifier.padding(7.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        UVIndicator(
                            indicatorValue = state.uv.toInt(),
                            canvasSize = 200.dp,
                            backgroundIndicatorStrokeWidth = 40f,
                            foregroundIndicatorStrokeWidth = 40f,
                            bigTextFontSize = 28.sp
                        )
                            Column(modifier = Modifier
                                .padding(end = 16.dp),
                                horizontalAlignment = Alignment.End
                            ) {
                                AsyncImage(
                                    model = "https:${state.condition.icon}",
                                    contentDescription = "imageIcon",
                                    modifier = Modifier
                                        .size(76.dp)
                                        .padding(top = 1.dp, end = 2.dp)
                                )
                                Text(
                                    modifier = Modifier
                                        .padding(start = 10.dp),
                                    text = "${state.tempC.toInt()}°C",
                                    style = TextStyle(fontSize = 56.sp),
                                    color = TextLight,
                                    fontFamily = fontFamily,
                                    fontWeight = FontWeight.Normal,
                                )
                            }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                        MarqueeText(text = "UV ${uVComment(state.uv)}")

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 30.dp),
                            horizontalArrangement = Arrangement.Center
                        ){
                            CustomInfoButton(
                                text = stringResource(id = R.string.indication),
                                navController,painterResource(id = R.drawable.ic_uv_img2),
                                Screen.UVscreen.passUVARG(state.uv.toInt()))
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun CustomInfoButton(
    text: String,
    navController: NavController,
    icon: Painter,
    road:String
){
    TextButton(
        onClick = {
            navController.navigate(route = road)
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent,
            contentColor = Color.White
        )
    ) {
        Icon(
            painter = icon,
            contentDescription = "UV_button_details",
            modifier = Modifier.size(34.dp)
        )
        Spacer(modifier = Modifier.width(14.dp))
        Text(
            text = text,
            fontSize = 20.sp,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal
        )
    }
}