package ru.ds.weatherfirst.presentation.ui

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ds.weatherfirst.R
import ru.ds.weatherfirst.data.repository.WeatherRepo
import ru.ds.weatherfirst.domain.location.LocationTracker
import javax.inject.Inject

@AndroidEntryPoint
class Widget @Inject constructor(
    private val weatherRepo: WeatherRepo,
    private val locationTracker: LocationTracker
) : AppWidgetProvider() {
    private val scope = CoroutineScope(Dispatchers.IO)
    //private val viewModel: HomeViewModel by viewModels()

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        appWidgetIds.forEach { appWidgetId ->
            RemoteViews(
                context.packageName,
                R.layout.widget_layout
            ).apply {
                scope.launch {
                    val lat = locationTracker.getCurrentLocation()?.latitude.toString()
                    val lon = locationTracker.getCurrentLocation()?.longitude.toString()
                    val day = weatherRepo.weatherResponse("$lat,$lon")

                    var tempDay = day.current

                    setTextViewText(R.id.textView,"Test")

                    appWidgetManager.updateAppWidget(appWidgetId, this@apply)
                }
            }
        }
    }
}