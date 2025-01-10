package com.saba.alarmmanager.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.saba.base_android.graph.AppGraphScreens
import com.saba.ui_home_screen.HomeScreen


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    isDark:Boolean,
    changeThemeClick:()->Unit
) {
        SharedTransitionLayout {
            NavHost(
                navController = navController,
                startDestination = AppGraphScreens.HomeScreen.route,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
                popEnterTransition = { EnterTransition.None },
                popExitTransition = { ExitTransition.None }

            ) {
                /*    composable(AppGraphScreens.SplashScreens.route) {
                      //  SplashScreen(navController = navController)
                    }*/
                composable(AppGraphScreens.HomeScreen.route) {
                    HomeScreen(navController = navController,isDark=isDark, changeThemeClick =changeThemeClick )
                }


            }
        }

}