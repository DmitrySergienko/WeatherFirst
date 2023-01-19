package ru.ds.weatherfirst.presentation.widget

import androidx.compose.runtime.Composable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.text.Text

class HelloWorldWidget: GlanceAppWidget()  {

    @Composable
    override fun Content() {
        Text(text = "Hello world!!!!")
    }
}