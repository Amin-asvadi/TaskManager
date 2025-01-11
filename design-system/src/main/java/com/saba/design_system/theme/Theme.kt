package com.saba.design_system.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Primary,
    onPrimary = Primary_Active,
    primaryContainer = Primary_Light,
    background = Gray_900,
    onBackground = White_Active,
    inversePrimary = Success,
    inverseOnSurface = Success_Active,
    inverseSurface = Success_Light,
    error = Danger,
    onError = Danger_Active,
    errorContainer = Danger_Light,
    secondary = Info,
    onSecondary = Info_Active,
    secondaryContainer = Info_Light,
    outline = Warning,
    outlineVariant = Warning_Active,
    onTertiary = Warning_Light,
    surfaceContainerHighest =Gray_100 ,
    surfaceContainerHigh =Gray_200 ,
    surface =Gray_900 ,
    onSurface =Gray_400 ,
    surfaceContainer = Gray_500,
    surfaceBright =Gray_600 ,
    onSurfaceVariant =Gray_700 ,
    surfaceContainerLow = Gray_800,
    surfaceContainerLowest = Gray_900,
)
private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = Primary_Active,
    primaryContainer = Primary_Light,
    background = White,
    onBackground = White_Active,
    inversePrimary = Success,
    inverseOnSurface = Success_Active,
    inverseSurface = Success_Light,
    error = Danger,
    onError = Danger_Active,
    errorContainer = Danger_Light,
    secondary = Info,
    onSecondary = Info_Active,
    secondaryContainer = Info_Light,
    outline = Warning,
    outlineVariant = Warning_Active,
    onTertiary = Warning_Light,
    surfaceContainerHighest = Gray_900,
    surfaceContainerHigh = Gray_800,
    surfaceContainerLow = Gray_200,
    surface = White,
    onSurface = Gray_600,
    surfaceContainer = Gray_500,
    surfaceBright = Gray_400,
    onSurfaceVariant = Gray_300,
    surfaceContainerLowest = Gray_100,
)

@Composable
fun AlarmManagerTheme(
    darkTheme: Boolean = false,
    // Dynamic color is available on Android 12+
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}