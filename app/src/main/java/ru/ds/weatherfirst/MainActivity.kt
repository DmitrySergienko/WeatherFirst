package ru.ds.weatherfirst

import android.Manifest
import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import dagger.hilt.android.AndroidEntryPoint
import ru.ds.weatherfirst.domain.connectivity.ConnectivityObserver
import ru.ds.weatherfirst.domain.connectivity.NetworkConnectivityObserver
import ru.ds.weatherfirst.presentation.ui.screens.main.NoConnectionScreen
import ru.ds.weatherfirst.presentation.ui.theme.WeatherFirstTheme
import ru.ds.weatherfirst.ui.SetupNavGraph

const val ADV_TEST_START = "ca-app-pub-3940256099942544/3419835294"
const val ADV_TEST_BANNER = "ca-app-pub-3940256099942544/6300978111"
const val ADV_MY_BANNER = "ca-app-pub-4733065340996872/5195655548"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    //connectivity observer
    private lateinit var connectivityObserver: ConnectivityObserver

    //navigation between screens
    lateinit var navController: NavHostController

    //Admob
    var mInterstitialAd: InterstitialAd? = null

    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>


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

        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            // Тут можно запустить код если локация получена

        }
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            )
        )

        //connectivity observer
        connectivityObserver = NetworkConnectivityObserver(applicationContext)

        setContent {

            //connectivity observer
            val status by connectivityObserver.observe().collectAsState(
                initial = ConnectivityObserver.Status.Unavailable
            )

            //Admob
            val adRequest = AdRequest.Builder().build()
            InterstitialAd.load(this, ADV_MY_BANNER, adRequest,
                object : InterstitialAdLoadCallback() {

                    override fun onAdFailedToLoad(p0: LoadAdError) {
                        super.onAdFailedToLoad(p0)
                        mInterstitialAd = null
                    }
                    override fun onAdLoaded(p0: InterstitialAd) {
                        super.onAdLoaded(p0)
                        mInterstitialAd = p0
                        mInterstitialAd?.show(this@MainActivity)
                    }
                })

            WeatherFirstTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(
                        //Admob
                        bottomBar = {
                            AndroidView(factory = {
                                AdView(it).apply {
                                    var adSize = AdSize(300, 50)
                                    adSize = AdSize.BANNER
                                    setAdSize(adSize)
                                    adUnitId = ADV_MY_BANNER
                                    loadAd(AdRequest.Builder().build())
                                }
                            })
                        },

                        content = {
                            // if internet available
                            if (status == ConnectivityObserver.Status.Available) {

                                Navigation
                                navController = rememberNavController()
                                SetupNavGraph(navController = navController)
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









