package ru.ds.weatherfirst

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import dagger.hilt.android.AndroidEntryPoint
import ru.ds.weatherfirst.domain.connectivity.ConnectivityObserver
import ru.ds.weatherfirst.domain.connectivity.NetworkConnectivityObserver
import ru.ds.weatherfirst.presentation.screens.HomeViewModel
import ru.ds.weatherfirst.presentation.theme.WeatherFirstTheme
import ru.ds.weatherfirst.presentation.ui.screens.main.NoConnectionScreen
import ru.ds.weatherfirst.ui.SetupNavGraph

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    //connectivity observer
    private lateinit var connectivityObserver: ConnectivityObserver

    //navigation between screens
    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        //connectivity observer
        connectivityObserver = NetworkConnectivityObserver(applicationContext)

        setContent {

            //connectivity observer
            val status by connectivityObserver.observe().collectAsState(
                initial = ConnectivityObserver.Status.Unavailable
            )

            WeatherFirstTheme {
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
                                // If no internet
                                NoConnectionScreen()
                            }
                        }
                    )
                }
            }
        }
    }
}









