package ru.ds.weatherfirst.widget

import android.Manifest
import android.app.Application
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.util.Log
import android.view.View
import android.widget.RemoteViews
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.ds.weatherfirst.R
import ru.ds.weatherfirst.data.api.WeatherApi
import ru.ds.weatherfirst.domain.model.Weather
import kotlin.coroutines.resume

const val BASE_URL = "https://api.weatherapi.com/"
private const val MY_ACTION = "MY_ACTION"
private const val API_KEY = "886e042c31bc49c3a3f131017220902"

const val MY_TAG = "VVV"

class RateWidget : AppWidgetProvider() {

    private val application: Application
        get() {
            TODO()
        }

    private lateinit var fusedLocClient: FusedLocationProviderClient
    private suspend fun getCurrentLocation(): Location? {
        val hasAccessFineLocationPermission = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val hasAccessCoarseLocationPermission = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val locationManager =
            application.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (!hasAccessCoarseLocationPermission || !hasAccessFineLocationPermission || !isGpsEnabled) {
            return null
        }

        return suspendCancellableCoroutine { cont ->
            fusedLocClient.lastLocation.apply {
                if (isComplete) {
                    if (isSuccessful) {
                        cont.resume(result)
                    } else {
                        cont.resume(null)
                    }
                    return@suspendCancellableCoroutine
                }
                addOnSuccessListener {
                    cont.resume(it)
                }
                addOnFailureListener {
                    cont.resume(null)
                }
                addOnCanceledListener {
                    cont.cancel()
                }
            }
        }
    }

    //get api by retrofit
    private val api: WeatherApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(WeatherApi::class.java)
    }

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

        if (intent.action != MY_ACTION) return

        val appWidgetManager = AppWidgetManager.getInstance(context)
        val appWidgetId = ComponentName(context, RateWidget::class.java)

        RemoteViews(
            context.packageName,
            R.layout.initial_layout_widget
        ).apply {
            scope.launch {

                withContext(Dispatchers.IO) {
                    Log.d("VVV", " thread sleep")
                    val lon = getCurrentLocation()?.longitude.toString()
                    val lat = getCurrentLocation()?.latitude.toString()

                    val location = weatherResponse("$lat,$lon")
                    Log.d(MY_TAG, "onReceive location: $location")
                    setTextViewText(R.id.textView, "Location: $location")
                }

                setViewVisibility(R.id.progress_bar, View.INVISIBLE)
                setViewVisibility(R.id.progress_image, View.VISIBLE)
                setViewVisibility(R.id.progress_bar, View.INVISIBLE)
                //update widget manager
                appWidgetManager.updateAppWidget(appWidgetId, this@apply)
            }
        }
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        //отправляюь интен чтобы активировать метод OnReceive, (для этого использую pending intent)
        val intent = Intent(context, RateWidget::class.java).apply {
            action = MY_ACTION
        }
        val pendingIntent =
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        appWidgetIds.forEach { appWidgetId ->
            RemoteViews(
                context.packageName,
                R.layout.initial_layout_widget
            ).apply {
                scope.launch {
                    //set pending intent
                    setOnClickPendingIntent(R.id.widget_root, pendingIntent)

                    withContext(Dispatchers.IO) {
                        Log.d("VVV", " thread sleep")
                        val lon = getCurrentLocation()?.longitude.toString()
                        val lat = getCurrentLocation()?.latitude.toString()

                        val location = weatherResponse("$lat,$lon")
                        Log.d(MY_TAG, "onUpd location: $location")
                        setTextViewText(R.id.textView, "Location: $location")
                    }
                    setViewVisibility(R.id.progress_bar, View.INVISIBLE)
                    setViewVisibility(R.id.progress_image, View.VISIBLE)
                    Log.d(MY_TAG, "onUpd  done")
                    appWidgetManager.updateAppWidget(appWidgetId, this@apply)
                }
            }
        }
    }

    suspend fun weatherResponse(city: String): Weather {
        return api.getWeather(API_KEY,city,"3")
    }

}