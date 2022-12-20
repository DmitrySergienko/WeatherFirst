package ru.ds.weatherfirst.presentation.screens

import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import ru.ds.weatherfirst.R
import ru.ds.weatherfirst.presentation.screens.days.RecyclerScreenDays
import ru.ds.weatherfirst.presentation.screens.hours.RecyclerScreen
import ru.ds.weatherfirst.presentation.screens.main.fontFamily
import ru.ds.weatherfirst.presentation.screens.uv_screen.UV_screenTab
import ru.ds.weatherfirst.presentation.theme.BlueLight
import ru.ds.weatherfirst.presentation.theme.TextLight

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabLayout(
    navController: NavController
) {
    val tabList = listOf(
        stringResource(id = R.string.uv),
        stringResource(id = R.string.hours),
        stringResource(id = R.string.days))
    //для пейджера
    val pageState = rememberPagerState()
    val tabIndex = pageState.currentPage
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .height(420.dp)
            .padding(start = 2.dp, end = 2.dp)
            .clip(RoundedCornerShape(1.dp))
    ) {
        TabRow(
            selectedTabIndex = tabIndex,
            indicator = { pos ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pageState, pos)
                )
            },
            backgroundColor = BlueLight,
            modifier = Modifier.alpha(0.94f),
            contentColor = TextLight,
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
                        Text(
                            text = text,
                            style = TextStyle(color = TextLight),
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.Normal,
                        )
                    }
                )
            }
        }
        HorizontalPager(
            count = tabList.size,
            state = pageState,
            modifier = Modifier
                .weight(1.0f)
        ) { tabIndex ->
            when (tabIndex) {
                0 -> UV_screenTab(navController)
                1 -> RecyclerScreen()
                2 -> RecyclerScreenDays()
            }
        }
    }
}
