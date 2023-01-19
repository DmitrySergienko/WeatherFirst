package ru.ds.weatherfirst.presentation.widget

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.glance.LocalSize
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.SizeMode
import androidx.glance.text.Text
import ru.ds.weatherfirst.R
import ru.ds.weatherfirst.presentation.utils.isSmallerThan
import ru.ds.weatherfirst.presentation.utils.readable

class ErrorUIWidget : GlanceAppWidget(errorUiLayout = R.layout.layout_widget_custom_error) {

    // Use SizeMode.Exact so that the widget's UI is refreshed whenever it's resized, this allows
    // hitting the error case.
    override val sizeMode = SizeMode.Exact

    @Composable
    override fun Content() {
        val size = LocalSize.current
        if (size.isSmallerThan(maxSize)) {
            Text(text = "Size: ${size.width.readable()} x ${size.height.readable()}")
        } else {
            androidx.compose.material.Text(text = "error") // Not a glance composable, should error out
        }
    }

    // When the widget is resized to a size larger than `maxSize`, an error is triggered.
    private val maxSize = DpSize(300.dp, 300.dp)
}

class ErrorUIWidgetReceiver : GlanceAppWidgetReceiver() {

    override val glanceAppWidget: GlanceAppWidget = ErrorUIWidget()
}