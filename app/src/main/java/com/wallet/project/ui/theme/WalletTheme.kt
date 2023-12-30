package com.wallet.project.ui.theme

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

val walletDarkColorScheme = darkColorScheme(
    primary = walletDarkPrimary,
    onPrimary = walletDarkOnPrimary,
    primaryContainer = walletDarkPrimaryContainer,
    onPrimaryContainer = walletDarkOnPrimaryContainer,
    inversePrimary = walletDarkPrimaryInverse,
    secondary = walletDarkSecondary,
    onSecondary = walletDarkOnSecondary,
    secondaryContainer = walletDarkSecondaryContainer,
    onSecondaryContainer = walletDarkOnSecondaryContainer,
    tertiary = walletDarkTertiary,
    onTertiary = walletDarkOnTertiary,
    tertiaryContainer = walletDarkTertiaryContainer,
    onTertiaryContainer = walletDarkOnTertiaryContainer,
    error = walletDarkError,
    onError = walletDarkOnError,
    errorContainer = walletDarkErrorContainer,
    onErrorContainer = walletDarkOnErrorContainer,
    background = walletDarkBackground,
    onBackground = walletDarkOnBackground,
    surface = walletDarkSurface,
    onSurface = walletDarkOnSurface,
    inverseSurface = walletDarkInverseSurface,
    inverseOnSurface = walletDarkInverseOnSurface,
    surfaceVariant = walletDarkSurfaceVariant,
    onSurfaceVariant = walletDarkOnSurfaceVariant,
    outline = walletDarkOutline,
)

private val walletLightColorScheme = lightColorScheme(
    primary = walletLightPrimary,
    onPrimary = walletLightOnPrimary,
    primaryContainer = walletLightPrimaryContainer,
    onPrimaryContainer = walletLightOnPrimaryContainer,
    inversePrimary = walletLightPrimaryInverse,
    secondary = walletLightSecondary,
    onSecondary = walletLightOnSecondary,
    secondaryContainer = walletLightSecondaryContainer,
    onSecondaryContainer = walletLightOnSecondaryContainer,
    tertiary = walletLightTertiary,
    onTertiary = walletLightOnTertiary,
    tertiaryContainer = walletLightTertiaryContainer,
    onTertiaryContainer = walletLightOnTertiaryContainer,
    error = walletLightError,
    onError = walletLightOnError,
    errorContainer = walletLightErrorContainer,
    onErrorContainer = walletLightOnErrorContainer,
    background = walletLightBackground,
    onBackground = walletLightOnBackground,
    surface = walletLightSurface,
    onSurface = walletLightOnSurface,
    inverseSurface = walletLightInverseSurface,
    inverseOnSurface = walletLightInverseOnSurface,
    surfaceVariant = walletLightSurfaceVariant,
    onSurfaceVariant = walletLightOnSurfaceVariant,
    outline = walletLightOutline,
)

@Composable
fun WalletTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val walletColorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> walletDarkColorScheme
        else -> walletLightColorScheme
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
        colorScheme = walletColorScheme,
        typography = walletTypography,
        shapes = shapes,
        content = content,
    )
}
