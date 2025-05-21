package com.example.rickmasters.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.rickmasters.R


private val Gilroy = FontFamily(
    Font(R.font.gilroy_medium, FontWeight.Medium),
    Font(R.font.gilroy_semibold, FontWeight.SemiBold),
    Font(R.font.gilroy_bold, FontWeight.Bold)
)


data class AppTypography (
    val title: TextStyle = TextStyle(
        fontFamily = Gilroy,
        fontWeight = FontWeight.Bold,
        lineHeight = 40.sp,
        fontSize = 32.sp,
        letterSpacing = 0.14.sp
    ),
    val subTitle: TextStyle = TextStyle(
        fontFamily = Gilroy,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        letterSpacing = 0.11.sp
    ),
    val bodyRegular: TextStyle = TextStyle(
        fontFamily = Gilroy,
        fontWeight = FontWeight.SemiBold,
        fontSize = 15.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.09.sp
    ),

    val body1: TextStyle = TextStyle(
        fontFamily = Gilroy,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        letterSpacing = 0.sp
    ),
    val caption1: TextStyle = TextStyle(
        fontFamily = Gilroy,
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.04.sp
    ),
    val caption2: TextStyle = TextStyle(
        fontFamily = Gilroy,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        lineHeight = 11.sp,
        letterSpacing = 0.11.sp
    )
)

val LocalAppTypography = staticCompositionLocalOf {
    AppTypography()
}


val MaterialTypography = Typography()

