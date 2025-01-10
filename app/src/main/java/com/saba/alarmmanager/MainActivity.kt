package com.saba.alarmmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalUseFallbackRippleImplementation
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.saba.alarmmanager.navigation.AppNavHost
import com.saba.design_system.theme.AlarmManagerTheme
import com.saba.design_system.animations.CircularReveal
import com.saba.design_system.theme.Gray_900
import com.saba.design_system.theme.White
import com.saba.presentation.main.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModels()
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val navController = rememberNavController()
            var darkTheme by remember { mutableStateOf(false) }
            val onThemeToggle = {
                darkTheme = !darkTheme
            }

            CircularReveal(
                expanded = uiState.darkMode,
                animationSpec = tween(1500)
            ) { isDark ->
                AlarmManagerTheme(isDark) {
                    CompositionLocalProvider(
                        LocalUseFallbackRippleImplementation provides true,
                        LocalLayoutDirection provides  LayoutDirection.Rtl,
                    ) {
                        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                            AppNavHost(
                                navController = navController,
                                modifier = Modifier.padding(top = innerPadding.calculateTopPadding()),
                                changeThemeClick ={
                                    onThemeToggle()
                                    viewModel.changeTheme( !darkTheme)
                                } ,
                                isDark= uiState.darkMode,
                            )
                        }
                    }

                }
            }

        }
    }
}