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
import kotlin.coroutines.resume

const val BASE_URL = "https://api.weatherapi.com/"
private const val MY_ACTION = "MY_ACTION"

class RateWidget : AppWidgetProvider() {

       private val application: Application
        get() {
            TODO()
        }

    private lateinit var fusedLocClient: FusedLocationProviderClient
    suspend fun getCurrentLocation(): Location? {
        val hasAccessFineLocationPermission = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val hasAccessCoarseLocationPermission = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val locationManager = application.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if(!hasAccessCoarseLocationPermission || !hasAccessFineLocationPermission || !isGpsEnabled) {
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

                //get rate USD & EUR (https://app.freecurrencyapi.com/)
              // val rate = api.getRateApi(APIKEY).data
              // val usdRate = rate?.rub?.toDouble()
              // if (usdRate != null) {
              //     rateDecimal(usdRate)
              // }

              // val tempRate = rate?.eur?.toDouble()
              // val eurRate = usdRate?.let { ((1 - tempRate!!) + 1).times(it) }
              // if (usdRate != null) {
              //     if (eurRate != null) {
              //         rateDecimal(eurRate)
              //     }
              // }

                //progress bar visibility
                setProgressBar(R.id.progress_bar, 1, 1, false)
                setViewVisibility(R.id.progress_image, View.INVISIBLE)
                setViewVisibility(R.id.progress_bar, View.VISIBLE)
                //update widget manager
                appWidgetManager.updateAppWidget(appWidgetId, this@apply)

                //sleep the thread to show progress bar running
                withContext(Dispatchers.IO) {
                    Log.d("VVV", " thread sleep")
                    Thread.sleep(500)

                    setTextViewText(R.id.textView, "USD Test")

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

                    //get rate USD and EUR (https://app.freecurrencyapi.com/)
                   // val rate = api.getRateApi(APIKEY).data
                   // val usdRate = rate?.rub?.toDouble()
//
                   // if (usdRate != null) {
                   //     rateDecimal(usdRate)
                   // }
                   // val tempRate = rate?.eur?.toDouble()
                   // val eurRate = usdRate?.let { ((1 - tempRate!!) + 1).times(it) }
                   // if (usdRate != null) {
                   //     if (eurRate != null) {
                   //         rateDecimal(eurRate)
                   //     }
                   // }

                    setTextViewText(R.id.textView, "USD Test")

                    setViewVisibility(R.id.progress_bar, View.INVISIBLE)
                    setViewVisibility(R.id.progress_image, View.VISIBLE)
                    Log.d("VVV", "onUpdate done")
                    appWidgetManager.updateAppWidget(appWidgetId, this@apply)
                }
            }
        }
    }

    private fun rateDecimal(value: Double): String {
        val result = Math.round(value * 100.0) / 100.0
        return result.toString()
    }

}