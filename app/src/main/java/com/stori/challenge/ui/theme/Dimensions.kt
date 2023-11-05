package com.stori.challenge.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val LocalDim = compositionLocalOf { Dimensions() }

data class Dimensions(
    val textFieldHeight: Dp = 60.dp,
    val default: Dp = 0.dp,
    val spaceXXSmall: Dp = 2.dp,
    val spaceXSmall: Dp = 3.dp,
    val spaceExtraSmall: Dp = 4.dp,
    val spaceSmall: Dp = 8.dp,
    val smallElevation: Dp = 4.dp,
    val mediumElevation: Dp = 9.dp,
    val spaceMedium: Dp = 16.dp,
    val paddingMedium: Dp = 18.dp,
    val paddingSmall: Dp = 9.dp,
    val smallRounded: Dp = 10.dp,
    val mediumRounded: Dp = 20.dp,
    val largeRounded: Dp = 30.dp,
    val notRounded: Dp = 0.dp,
    val paddingLarge: Dp = 21.dp,
    val spaceLarge: Dp = 32.dp,
    val spaceExtraLarge: Dp = 77.dp,
    val spaceXXLarge: Dp = 128.dp,
    val spaceXXXLarge: Dp = 256.dp,
)
