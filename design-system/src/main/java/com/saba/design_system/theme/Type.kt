package com.saba.design_system.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.saba.common_ui_resources.R
val font = R.font.iranian_sans
val customHamrahFont = FontFamily(
    Font(R.font.iran_sansx_medium)
)
val Typography = Typography(
    displayLarge = TextStyle(fontFamily = customHamrahFont),
    displayMedium = TextStyle(fontFamily = customHamrahFont),
    displaySmall = TextStyle(fontFamily = customHamrahFont),
    headlineLarge = TextStyle(fontFamily = customHamrahFont),
    headlineMedium = TextStyle(fontFamily = customHamrahFont),
    headlineSmall = TextStyle(fontFamily = customHamrahFont),
    titleMedium = TextStyle(fontFamily = customHamrahFont),
    titleSmall = TextStyle(fontFamily = customHamrahFont),
    bodyLarge = TextStyle(fontFamily = customHamrahFont),
    bodyMedium = TextStyle(fontFamily = customHamrahFont),
    bodySmall = TextStyle(fontFamily = customHamrahFont),
    labelLarge = TextStyle(fontFamily = customHamrahFont),
    labelMedium = TextStyle(fontFamily = customHamrahFont),
    titleLarge = TextStyle(
        fontFamily = customHamrahFont,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = customHamrahFont,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)