package ru.ds.weatherfirst.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp


@Composable
fun RecyclerScreen(names: List<String> = List(100) { "Any date" }) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 30.dp)
    ) {

        items(names) { name ->

            RecyclerItemScreen(name)

        }
    }

}