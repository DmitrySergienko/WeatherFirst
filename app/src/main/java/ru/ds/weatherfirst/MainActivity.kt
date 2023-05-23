package ru.ds.weatherfirst

//import androidx.activity.result.contract.ActivityResultContracts
import android.Manifest
import android.animation.ObjectAnimator
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import dagger.hilt.android.AndroidEntryPoint
import ru.ds.weatherfirst.domain.connectivity.ConnectivityObserver
import ru.ds.weatherfirst.domain.connectivity.NetworkConnectivityObserver
import ru.ds.weatherfirst.presentation.screens.HomeViewModel
import ru.ds.weatherfirst.presentation.theme.WeatherFirstTheme
import ru.ds.weatherfirst.presentation.ui.screens.main.NoConnectionScreen
import ru.ds.weatherfirst.ui.SetupNavGraph
import ru.ds.weatherfirst.widget.RateWidget

@AndroidEntryPoint
@ExperimentalPermissionsApi
class MainActivity : ComponentActivity() {

    //connectivity observer
    private lateinit var connectivityObserver: ConnectivityObserver

    //permission request
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    //navigation between screens
    lateinit var navController: NavHostController

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //first launch attach the widget
        val appContext = this.applicationContext
        val sharedPrefs = appContext.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val isFirstLaunch = sharedPrefs.getBoolean("firstLaunch", true)

        //Splash Screen
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val splashScreen = installSplashScreen()
            splashScreen.setOnExitAnimationListener { splashScreenProvider ->
                ObjectAnimator.ofFloat(
                    splashScreenProvider.view,
                    View.TRANSLATION_Y,
                    0f, -splashScreenProvider.view.height.toFloat()
                ).apply {
                    duration = 500
                    interpolator = AnticipateInterpolator() //occurs with acceleration
                    doOnEnd { splashScreenProvider.remove() }
                }.start()
            }
        }

        //permission request
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            // permission granted launch code

            //connectivity observer
            connectivityObserver = NetworkConnectivityObserver(applicationContext)

            setContent {

                //connectivity observer
                val status by connectivityObserver.observe().collectAsState(
                    initial = ConnectivityObserver.Status.Unavailable
                )

                WeatherFirstTheme {
                    //navigation
                    navController = rememberNavController()
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        Scaffold(
                            content = {

                                // if internet available
                                if (status == ConnectivityObserver.Status.Available) {
                                    Navigation
                                    navController = rememberNavController()
                                    val homeViewModel: HomeViewModel = viewModel()
                                    SetupNavGraph(
                                        navController = navController,
                                        homeViewModel.weatherState
                                    )
                                } else {
                                    NoConnectionScreen()
                                }
                            }
                        )
                    }
                }
                if (isFirstLaunch) {
                    //Add widget to the main screen Alert
                    val mAppWidgetManager = AppWidgetManager.getInstance(this)

                    val myProvider = ComponentName(this, RateWidget::class.java)
                    val b = Bundle()
                    b.putString("123", "ggg")


                    if (mAppWidgetManager.isRequestPinAppWidgetSupported) {
                        val pinnedWidgetCallbackIntent = Intent(this, RateWidget::class.java)
                        val successCallback = PendingIntent.getBroadcast(
                            this,
                            0, pinnedWidgetCallbackIntent, PendingIntent.FLAG_IMMUTABLE
                        )
                        mAppWidgetManager.requestPinAppWidget(myProvider, b, successCallback)
                    }
                    //first launch
                    val editor = sharedPrefs.edit()
                    editor.putBoolean("firstLaunch", false)
                    editor.apply()
                }
            }
        }

        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            )
        )
    }
}







