package com.smartdev.vkcup2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.smartdev.vkcup2.base.VkCupNavHost
import com.smartdev.vkcup2.base.rememberAppState
import com.smartdev.vkcup2.ui.screens.choose.navigation.ChooseDestination
import com.smartdev.vkcup2.ui.theme.VkCup2Theme
import com.smartdev.vkcup2.util.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContent {
            VkCup2Theme {
                val systemUiController = rememberSystemUiController()
                LaunchedEffect(systemUiController) {
                    systemUiController.setSystemBarsColor(
                        color = Color.White.copy(alpha = 0.02f),
                        darkIcons = false
                    )
                    systemUiController.setNavigationBarColor(Color.Black)
                }

                val appState = rememberAppState()

                VkCupNavHost(
                    modifier = Modifier.navigationBarsPadding(),
                    navController = appState.navController,
                    onNavigateToDestination = appState::navigate,
                    onBackClick = appState::onBackClick,
                    startDestination = ChooseDestination.route
                )
            }
        }
    }
}