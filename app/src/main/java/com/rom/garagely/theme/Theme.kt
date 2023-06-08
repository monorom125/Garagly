package com.rom.garagely.theme


import AppColor
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

private val DarkColorScheme = darkColors(
    primary = AppColor.Primary,
    secondary = AppColor.Secondary,
    error = AppColor.Red
)

private val LightColorScheme = lightColors(
    primary = AppColor.DarkPrimary,
    secondary = AppColor.DarkSecondary,
    error = AppColor.DarkRed
)

@Composable
fun GaragelyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
//    val view = LocalView.current
//    if (!view.isInEditMode) {
//        SideEffect {
//            (view.context as Activity).window.statusBarColor = colorScheme.primary.toArgb()
//            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
//        }
//    }

    MaterialTheme(
        colors = colorScheme,
        typography = Typography,
        content = content
    )
}

object GaragelyTheme {
    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = Typography
}