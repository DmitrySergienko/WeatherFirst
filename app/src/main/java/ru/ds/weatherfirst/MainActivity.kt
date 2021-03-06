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
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import dagger.hilt.android.AndroidEntryPoint
import ru.ds.weatherfirst.ui.theme.MyProject
import ru.ds.weatherfirst.ui.theme.WeatherFirstTheme

const val ADV_TEST_START = "ca-app-pub-3940256099942544/3419835294"
const val ADV_TEST_BANNER = "ca-app-pub-3940256099942544/6300978111"
const val ADV_MY_BANNER = "ca-app-pub-4733065340996872/5195655548"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    var mInterstitialAd: InterstitialAd? = null


//    @OptIn(ExperimentalPermissionsApi::class)
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

        setContent {

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
//Request permissions
//                            RequestMultiplePermissions(
//                                permissions = listOf(
//                                    Manifest.permission.ACCESS_FINE_LOCATION,
//                                    Manifest.permission.ACCESS_COARSE_LOCATION
//                                )
//                            )
                            MyProject()


                        })
                }

            }
        }
    }

}






