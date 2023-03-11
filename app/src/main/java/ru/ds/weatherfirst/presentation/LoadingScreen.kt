package ru.ds.weatherfirst.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.ds.weatherfirst.presentation.theme.R1
import ru.ds.weatherfirst.presentation.theme.R2

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Box(contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                color = R2,
                modifier = Modifier.then(Modifier.size(60.dp))
            )
            CircularProgressIndicator(
                color = R1,
                modifier = Modifier.then(Modifier.size(80.dp))
            )
        }
    }
}