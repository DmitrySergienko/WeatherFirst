package ru.ds.weatherfirst.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import ru.ds.weatherfirst.ui.theme.BlueLight
import ru.ds.weatherfirst.ui.theme.TextLight
import ru.ds.weatherfirst.ui.theme.WeatherFirstTheme

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabLayout() {

    val tabList = listOf("HOURS", "DAYS")
    //для пейджера
    val pageState = rememberPagerState()
    val tabIndex = pageState.currentPage
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 2.dp, end = 2.dp)
            .clip(RoundedCornerShape(5.dp))
    ) {
        TabRow(
            selectedTabIndex = tabIndex,
            indicator = { pos ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pageState, pos)
                )
            },
            backgroundColor = BlueLight,
            modifier = Modifier.alpha(0.7f),
            contentColor = Color.White,

            ) {
            //идет перебор списка, для каждого элемнета выводит "text" и Tab
            tabList.forEachIndexed { index, text ->
                Tab(
                    selected = false,
                    onClick = {
                        coroutineScope.launch {
                            pageState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Text(text = text, style = TextStyle(color = TextLight))
                    }
                )
            }


        }
        HorizontalPager(
            count = tabList.size,
            state = pageState,
            modifier = Modifier
                //.fillMaxWidth()
                .weight(1.0f)
        ) { tabIndex ->
            RecyclerScreen()
        }
    }

}

@Preview
@Composable
fun TabLayoutPreview() {
    WeatherFirstTheme {
        TabLayout()
    }

}