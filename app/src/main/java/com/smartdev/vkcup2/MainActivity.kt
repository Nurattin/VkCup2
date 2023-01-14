package com.smartdev.vkcup2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.smartdev.vkcup2.base.VkCupNavHost
import com.smartdev.vkcup2.base.rememberAppState
import com.smartdev.vkcup2.ui.screens.choose.navigation.ChooseDestination
import com.smartdev.vkcup2.ui.theme.VkCup2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContent {
            VkCup2Theme {
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