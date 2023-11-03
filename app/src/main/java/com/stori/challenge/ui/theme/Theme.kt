package com.stori.challenge.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

val storiDarkColorScheme = darkColorScheme(
    primary = storiDarkPrimary,
    onPrimary = storiDarkOnPrimary,
    primaryContainer = storiDarkPrimaryContainer,
    onPrimaryContainer = storiDarkOnPrimaryContainer,
    inversePrimary = storiDarkPrimaryInverse,
    secondary = storiDarkSecondary,
    onSecondary = storiDarkOnSecondary,
    secondaryContainer = storiDarkSecondaryContainer,
    onSecondaryContainer = storiDarkOnSecondaryContainer,
    tertiary = storiDarkTertiary,
    onTertiary = storiDarkOnTertiary,
    tertiaryContainer = storiDarkTertiaryContainer,
    onTertiaryContainer = storiDarkOnTertiaryContainer,
    error = storiDarkError,
    onError = storiDarkOnError,
    errorContainer = storiDarkErrorContainer,
    onErrorContainer = storiDarkOnErrorContainer,
    background = storiDarkBackground,
    onBackground = storiDarkOnBackground,
    surface = storiDarkSurface,
    onSurface = storiDarkOnSurface,
    inverseSurface = storiDarkInverseSurface,
    inverseOnSurface = storiDarkInverseOnSurface,
    surfaceVariant = storiDarkSurfaceVariant,
    onSurfaceVariant = storiDarkOnSurfaceVariant,
    outline = storiDarkOutline,
)

private val storiLightColorScheme = lightColorScheme(
    primary = storiLightPrimary,
    onPrimary = storiLightOnPrimary,
    primaryContainer = storiLightPrimaryContainer,
    onPrimaryContainer = storiLightOnPrimaryContainer,
    inversePrimary = storiLightPrimaryInverse,
    secondary = storiLightSecondary,
    onSecondary = storiLightOnSecondary,
    secondaryContainer = storiLightSecondaryContainer,
    onSecondaryContainer = storiLightOnSecondaryContainer,
    tertiary = storiLightTertiary,
    onTertiary = storiLightOnTertiary,
    tertiaryContainer = storiLightTertiaryContainer,
    onTertiaryContainer = storiLightOnTertiaryContainer,
    error = storiLightError,
    onError = storiLightOnError,
    errorContainer = storiLightErrorContainer,
    onErrorContainer = storiLightOnErrorContainer,
    background = storiLightBackground,
    onBackground = storiLightOnBackground,
    surface = storiLightSurface,
    onSurface = storiLightOnSurface,
    inverseSurface = storiLightInverseSurface,
    inverseOnSurface = storiLightInverseOnSurface,
    surfaceVariant = storiLightSurfaceVariant,
    onSurfaceVariant = storiLightOnSurfaceVariant,
    outline = storiLightOutline,
)

@Composable
fun StoriTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val storiColorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> storiDarkColorScheme
        else -> storiLightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Transparent.toArgb()

            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = storiColorScheme,
        typography = storiTypography,
        shapes = shapes,
        content = content,
    )
}
