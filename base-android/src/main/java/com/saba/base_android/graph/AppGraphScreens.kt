package com.saba.base_android.graph

sealed class AppGraphScreens(val route: String) {
    data object SplashScreens : AppGraphScreens("splash_screen")
    data object HomeScreen : AppGraphScreens("home_screen")
}