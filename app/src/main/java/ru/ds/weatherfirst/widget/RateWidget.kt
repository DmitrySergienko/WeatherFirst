package ru.ds.weatherfirst.widget

import android.Manifest
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
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.ds.weatherfirst.R
import ru.ds.weatherfirst.data.api.WeatherApi
import kotlin.coroutines.resume


const val BASE_URL = "https://api.weatherapi.com/"
private const val MY_ACTION = "MY_ACTION"
private const val API_KEY = "886e042c31bc49c3a3f131017220902"

const val MY_TAG = "VVV"

class RateWidget : AppWidgetProvider() {


    private suspend fun getCurrentLocation(
        context: Context,
        locationClient: FusedLocationProviderClient
    ): Location? {
        val hasAccessFineLocationPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val hasAccessCoarseLocationPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (!hasAccessCoarseLocationPermission || !hasAccessFineLocationPermission || !isGpsEnabled) {
            return null
        }

        return suspendCancellableCoroutine { cont ->
            locationClient.lastLocation.apply {
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
//=================
                val fusedLocationProviderClient =
                    LocationServices.getFusedLocationProviderClient(context)
                getCurrentLocation(context, fusedLocationProviderClient)
                val lat = getCurrentLocation(context, fusedLocationProviderClient)?.latitude.toString()
                val lon = getCurrentLocation(context, fusedLocationProviderClient)?.longitude.toString()

                Log.d(MY_TAG, "onReceive() called lat: $lat")
                val weather = api.getWeather(API_KEY,"$lat,$lon","3")
                Log.d(MY_TAG, "onReceive() weather: ${weather.current}")
//=================
                setTextViewText(R.id.textView, "Location: 123")

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
        val intent = Intent(context, RateWidget::class.java).apply { action = MY_ACTION }
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

//=================
                    val fusedLocationProviderClient =
                        LocationServices.getFusedLocationProviderClient(context)
                    getCurrentLocation(context, fusedLocationProviderClient)
                    val lat = getCurrentLocation(context, fusedLocationProviderClient)?.latitude.toString()
                    val lon = getCurrentLocation(context, fusedLocationProviderClient)?.longitude.toString()

                    Log.d(MY_TAG, "onUpd() called lat: $lat")
                    val weather = api.getWeather(API_KEY,"$lat,$lon","3")
                    Log.d(MY_TAG, "onUpd() weather: ${weather.current}")
//=================

                    setViewVisibility(R.id.progress_bar, View.INVISIBLE)
                    setViewVisibility(R.id.progress_image, View.VISIBLE)
                    appWidgetManager.updateAppWidget(appWidgetId, this@apply)
                }
            }
        }
    }

}