package com.example.rickmasters.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class AppColors(
    val primary: Color,
    val secondary: Color,
    val tertiary: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val primaryContainer: Color,
    val onSuccess: Color
)

val appColors = AppColors(
    primary = Light_green1,
    secondary = Red1,
    tertiary = Orange1,
    textPrimary = Black1,
    textSecondary = Gray1,
    primaryContainer = White1,
    onSuccess = Green1
)

val LocalAppColors = staticCompositionLocalOf<AppColors> {
    appColors
}