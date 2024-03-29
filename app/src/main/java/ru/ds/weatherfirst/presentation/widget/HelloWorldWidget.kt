package ru.ds.weatherfirst.presentation.widget


import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.Button
import androidx.glance.GlanceModifier
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionParametersOf
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.layout.Column
import androidx.glance.layout.padding

val actionWidgetKey = ActionParameters.Key<String>("action-widget-key")

class HelloWorldWidget : GlanceAppWidget() {

    @Composable
    override fun Content() {
        Column(
            modifier = GlanceModifier.padding(8.dp)
        ) {
            Button(
                text = "Log on a click event",
                onClick = actionRunCallback<LogActionCallback>(
                    parameters = actionParametersOf(
                        actionWidgetKey to "log event"
                    )
                )
            )
        }
    }
}


//class LogActionCallback : ActionCallback {
//    override suspend fun onRun(
//        context: Context,
//        glanceId: GlanceId,
//        parameters: ActionParameters
//    ) {
//        log("Item with id $glanceId and params $parameters clicked.")
//    }
//}
