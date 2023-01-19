package ru.ds.weatherfirst.presentation.widget

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver

class HelloWorldWidgetReceiver() : GlanceAppWidgetReceiver() {

    override val glanceAppWidget: GlanceAppWidget = ActionWidget()
    //override val glanceAppWidget: GlanceAppWidget = HelloWorldWidget()
    //override val glanceAppWidget: ListWidget = ListWidget()
    //override val glanceAppWidget: GlanceAppWidget = StatefulWidget()
}