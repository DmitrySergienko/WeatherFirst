package ru.ds.weatherfirst.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.ds.weatherfirst.ui.home.HomeViewModel


@Composable
fun RecyclerScreen(names: List<String> = List(100) { "Any date" }) {

    val homeViewModel = viewModel(modelClass = HomeViewModel::class.java)
    val state by homeViewModel.state.collectAsState()

    LazyColumn(
        contentPadding = PaddingValues(bottom = 30.dp)
    ) {

        items(state) { name ->

            RecyclerItemScreen(name)

        }
    }

}