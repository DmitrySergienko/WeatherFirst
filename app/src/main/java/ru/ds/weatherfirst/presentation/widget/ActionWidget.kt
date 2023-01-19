package ru.ds.weatherfirst.presentation.widget


import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionParametersOf
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.layout.Column
import androidx.glance.layout.padding
import ru.ds.weatherfirst.presentation.utils.log


class ActionWidget : GlanceAppWidget() {

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

/**
 * Callback used with [actionRunCallback], and executed on user interaction. It must have a public
 * zero argument constructor since it's instanciated at runtime.
 */
class LogActionCallback : ActionCallback {
    override suspend fun onRun(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {

        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context, "Hi", Toast.LENGTH_SHORT).show()
        }
        log("Item with id $glanceId and params $parameters clicked.")
    }

}



